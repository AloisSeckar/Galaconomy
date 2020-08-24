package galaconomy.gui.view;

import galaconomy.gui.BasicGameLayout;
import galaconomy.gui.pane.DisplayPane;
import galaconomy.universe.IEngineSubscriber;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.player.Player;
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
 
public class PlayerListView extends Stage implements IEngineSubscriber {
 
    private final TableView<Player> table;
    private ObservableList<Player> data;
 
    public PlayerListView() {
        super.setTitle("Player list");
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) BasicGameLayout.getInstance().getScene().getWindow());
 
        data = FXCollections.observableList(UniverseManager.getInstance().getAllPlayers());
        
        table = new TableView();
        table.setItems(data);
 
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return p.getValue().displayName();
                    }
                };
            }
        });
        
        TableColumn landsCol = new TableColumn("Lands");
        landsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return String.valueOf(p.getValue().getLands().size());
                    }
                };
            }
        });
        
        TableColumn buildingsCol = new TableColumn("Buildings");
        buildingsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return String.valueOf(p.getValue().getBuildings().size());
                    }
                };
            }
        });
        
        TableColumn shipsCol = new TableColumn("Ships");
        shipsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return String.valueOf(p.getValue().getShips().size());
                    }
                };
            }
        });
        
        TableColumn creditsCol = new TableColumn("Credits");
        creditsCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Player, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Player, String> p) {
                return new ObservableValueBase<String>() {
                    @Override
                    public String getValue() {
                        return String.valueOf(p.getValue().getCredits());
                    }
                };
            }
        });
 
        table.getColumns().setAll(nameCol, landsCol, buildingsCol, shipsCol, creditsCol);
        table.setPrefWidth(600);
        table.setPrefHeight(400);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        table.getSelectionModel().select(0);
        
        nameCol.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(nameCol);
        table.sort();
        
        HBox menuBox = new HBox(20);
        menuBox.setAlignment(Pos.CENTER);
        
        Button selectButton = new Button("View player");
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Player currentPlayer = table.getSelectionModel().getSelectedItem();
            DisplayPane.getInstance().setElementToDisplay(currentPlayer);
            UniverseManager.getInstance().unRegisterSubscriber(PlayerListView.this);
            close();
        });
        menuBox.getChildren().add(selectButton);
        
        Button cancelButton = new Button("Close");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            UniverseManager.getInstance().unRegisterSubscriber(PlayerListView.this);
            close();
        });
        menuBox.getChildren().add(cancelButton);

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(table);
        mainLayout.getChildren().add(menuBox);
        
        Scene dialogScene = new Scene(mainLayout, 600, 450);
        
        super.setScene(dialogScene);
        
        UniverseManager.getInstance().registerSubscriber(PlayerListView.this);
 
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
        data = FXCollections.observableList(UniverseManager.getInstance().getAllPlayers());
        table.refresh();
    }
}