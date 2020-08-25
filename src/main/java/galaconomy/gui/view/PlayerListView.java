package galaconomy.gui.view;

import galaconomy.gui.BasicGameLayout;
import galaconomy.gui.pane.DisplayPane;
import galaconomy.universe.*;
import galaconomy.universe.player.Player;
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
 
        TableColumn<Player, String> nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return p.getValue().displayName();
            }
        });
        
        TableColumn<Player, String> landsCol = new TableColumn("Lands");
        landsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(p.getValue().getLands().size());
            }
        });
        
        TableColumn<Player, String> buildingsCol = new TableColumn("Buildings");
        buildingsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(p.getValue().getBuildings().size());
            }
        });
        
        TableColumn<Player, String> shipsCol = new TableColumn("Ships");
        shipsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(p.getValue().getShips().size());
            }
        });
        
        TableColumn<Player, String> creditsCol = new TableColumn("Credits");
        creditsCol.setCellValueFactory((p) -> new ObservableValueBase<String>() {
            @Override
            public String getValue() {
                return String.valueOf(p.getValue().getCredits());
            }
        });
 
        table.getColumns().setAll(nameCol, landsCol, buildingsCol, shipsCol, creditsCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
 
        table.getSelectionModel().select(0);
        
        nameCol.setSortType(TableColumn.SortType.ASCENDING);
        table.getSortOrder().add(nameCol);
        table.sort();
        
        Button selectButton = new Button("View player");
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Player currentPlayer = table.getSelectionModel().getSelectedItem();
            DisplayPane.getInstance().setElementToDisplay(currentPlayer);
            UniverseManager.getInstance().unRegisterSubscriber(PlayerListView.this);
            close();
        });
        
        HBox menuBox = WindowUtils.getWindowToolbar(PlayerListView.this, PlayerListView.this, selectButton);
        
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