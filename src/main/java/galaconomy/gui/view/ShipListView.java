package galaconomy.gui.view;

import galaconomy.gui.BasicGameLayout;
import galaconomy.gui.pane.DisplayPane;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.player.Player;
import galaconomy.universe.traffic.Ship;
import java.util.*;
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
import javafx.util.Callback;
 
public class ShipListView extends Stage {
 
    private final TableView<Ship> table;
    private final ObservableList<Ship> data;
 
    public ShipListView() {
        super.setTitle("Ship list");
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) BasicGameLayout.getInstance().getScene().getWindow());
 
        List<Ship> allShips = new ArrayList<>();
        List<Player> allPlayers = UniverseManager.getInstance().getAllPlayers();
        allPlayers.forEach((player) -> {
            allShips.addAll(player.getShips());
        });
        data = FXCollections.observableList(allShips);
        
        table = new TableView();
        table.setItems(data);
 
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Ship, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Ship, String> p) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return p.getValue().displayName();
                    }
                };
            }
        });
 
        table.getColumns().setAll(nameCol);
        table.setPrefWidth(250);
        table.setPrefHeight(400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        table.getSelectionModel().select(0);
        
        nameCol.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(nameCol);
        table.sort();
        
        HBox menuBox = new HBox(20);
        menuBox.setAlignment(Pos.CENTER);
        
        Button selectButton = new Button("View ship");
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Ship currentShip = table.getSelectionModel().getSelectedItem();
            DisplayPane.getInstance().setElementToDisplay(currentShip);
            close();
        });
        menuBox.getChildren().add(selectButton);
        
        Button cancelButton = new Button("Close");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            close();
        });
        menuBox.getChildren().add(cancelButton);

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(table);
        mainLayout.getChildren().add(menuBox);
        
        Scene dialogScene = new Scene(mainLayout, 600, 450);
        
        super.setScene(dialogScene);
 
    }
}