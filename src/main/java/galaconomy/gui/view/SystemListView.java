package galaconomy.gui.view;

import galaconomy.gui.BasicGameLayout;
import galaconomy.universe.*;
import galaconomy.universe.map.Star;
import galaconomy.utils.WindowUtils;
import javafx.beans.value.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Pos;
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
 
public class SystemListView extends Stage implements IEngineSubscriber {
 
    private final TableView<Star> table;
    private ObservableList<Star> data;
 
    public SystemListView() {
        super.setTitle("System list");
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) BasicGameLayout.getInstance().getScene().getWindow());
 
        data = FXCollections.observableList(UniverseManager.getInstance().getAvailableStars());
        
        table = new TableView();
        table.setItems(data);
 
        TableColumn<Star, String> nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return p.getValue().displayName();
            }
        });
        
        TableColumn<Star, String> systemsCol = new TableColumn("Bases");
        systemsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(p.getValue().getBases().size());
            }
        });
        
        TableColumn<Star, String> shipsCol = new TableColumn("Ships");
        shipsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(UniverseUtils.countShipsInSystem(p.getValue()));
            }
        });
 
        table.getColumns().setAll(nameCol, systemsCol, shipsCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        table.getSelectionModel().select(0);
        
        nameCol.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(nameCol);
        table.sort();
        
        Button selectButton = new Button("Go to system");
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Star currentStar = table.getSelectionModel().getSelectedItem();
            BasicGameLayout.getInstance().switchToSystem(currentStar);
            close();
        });
        
        HBox menuBox = WindowUtils.getWindowToolbar(SystemListView.this, SystemListView.this, selectButton);

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(table);
        mainLayout.getChildren().add(menuBox);
        
        Scene dialogScene = new Scene(mainLayout, 600, 450);
        
        super.setScene(dialogScene);
 
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        getData();
    }

    @Override
    public boolean isActive() {
        return true;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private void getData() {
        data = FXCollections.observableList(UniverseManager.getInstance().getAvailableStars());
        table.refresh();
    }
}