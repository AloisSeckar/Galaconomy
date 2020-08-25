package galaconomy.gui.view;

import galaconomy.gui.BasicGameLayout;
import galaconomy.gui.pane.DisplayPane;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.universe.player.Player;
import galaconomy.universe.traffic.Ship;
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
 
public class ShipListView extends Stage implements IEngineSubscriber {
 
    private final TableView<Ship> table;
    private ObservableList<Ship> data;
 
    public ShipListView() {
        super.setTitle("Ship list");
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) BasicGameLayout.getInstance().getScene().getWindow());
 
        data = FXCollections.observableList(UniverseManager.getInstance().getAllShips());
        
        table = new TableView();
        table.setItems(data);
 
        TableColumn<Ship, String> nameCol= new TableColumn("Name");
        nameCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return p.getValue().displayName();
            }
        });
        
        TableColumn<Ship, String> classCol = new TableColumn("Class");
        classCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return p.getValue().getShipClass();
            }
        });
        
        TableColumn<Ship, String> ownerCol = new TableColumn("Owner");
        ownerCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                Player owner = p.getValue().getCurrentOwner();
                return owner != null ? owner.displayName() : "";
            }
        });
        
        TableColumn<Ship, String> statusCol = new TableColumn("Status");
        statusCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return p.getValue().isIdle() ? "Idle" : "En route";
            }
        });
        
        TableColumn<Ship, String> systemCol = new TableColumn("System");
        systemCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                Star system = p.getValue().getCurrentSystem();
                return system != null ? system.displayName() : "";
            }
        });
        
        TableColumn<Ship, String> baseCol = new TableColumn("Base");
        baseCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                Base base = p.getValue().getCurrentBase();
                return base != null ? base.displayName() : "";
            }
        });
 
        table.getColumns().setAll(nameCol, classCol, ownerCol, statusCol, systemCol, baseCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        table.getSelectionModel().select(0);
        
        nameCol.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(nameCol);
        table.sort();
        
        Button selectButton = new Button("View ship");
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Ship currentShip = table.getSelectionModel().getSelectedItem();
            DisplayPane.getInstance().setElementToDisplay(currentShip);
            close();
        });
        
        HBox menuBox = WindowUtils.getWindowToolbar(ShipListView.this, ShipListView.this, selectButton);

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(table);
        mainLayout.getChildren().add(menuBox);
        
        Scene dialogScene = new Scene(mainLayout, 800, 450);
        
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
        data = FXCollections.observableList(UniverseManager.getInstance().getAllShips());
        table.refresh();
    }
}