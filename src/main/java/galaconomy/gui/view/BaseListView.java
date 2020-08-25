package galaconomy.gui.view;

import galaconomy.gui.BasicGameLayout;
import galaconomy.universe.*;
import galaconomy.universe.map.Base;
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
 
public class BaseListView extends Stage implements IEngineSubscriber {
 
    private final TableView<Base> table;  
    private ObservableList<Base> data;
 
    public BaseListView() {
        super.setTitle("Base list");
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) BasicGameLayout.getInstance().getScene().getWindow());
 
        data = FXCollections.observableList(UniverseManager.getInstance().getAvailableBases());
        
        table = new TableView();
        table.setItems(data);
 
        TableColumn<Base, String> nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return p.getValue().displayName();
            }
        });
        
        TableColumn<Base, String> landsCol = new TableColumn("Size");
        landsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(p.getValue().countLands());
            }
        });
        
        TableColumn<Base, String> buildingsCol = new TableColumn("Buildings");
        buildingsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(p.getValue().countBuildings());
            }
        });
        
        TableColumn<Base, String> freeCol = new TableColumn("Free");
        freeCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(p.getValue().countFreeLands());
            }
        });
        
        TableColumn<Base, String> shipsCol = new TableColumn("Ships");
        shipsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(UniverseUtils.countShipsInBase(p.getValue()));
            }
        });
 
        table.getColumns().setAll(nameCol, landsCol, buildingsCol, freeCol, shipsCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        table.getSelectionModel().select(0);
        
        nameCol.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(nameCol);
        table.sort();
        
        Button selectButton = new Button("Go to base");
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Base currentBase = table.getSelectionModel().getSelectedItem();
            BasicGameLayout.getInstance().switchToBase(currentBase);
            close();
        });
        
        HBox menuBox = WindowUtils.getWindowToolbar(BaseListView.this, BaseListView.this, selectButton);

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
        data = FXCollections.observableList(UniverseManager.getInstance().getAvailableBases());
        table.refresh();
    }
}