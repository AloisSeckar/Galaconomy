package galaconomy.gui.view;

import galaconomy.gui.BasicGameLayout;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.map.Star;
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
 
public class SystemListView extends Stage {
 
    private final TableView<Star> table;
    private final ObservableList<Star> data;
 
    public SystemListView() {
        super.setTitle("System list");
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) BasicGameLayout.getInstance().getScene().getWindow());
 
        data = FXCollections.observableList(UniverseManager.getInstance().getAvailableStars());
        
        table = new TableView();
        table.setItems(data);
 
        TableColumn nameCol = new TableColumn("Name");
        nameCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Star, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Star, String> p) {
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
        
        Button selectButton = new Button("Go to system");
        selectButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Star currentStar = table.getSelectionModel().getSelectedItem();
            BasicGameLayout.getInstance().switchToSystem(currentStar);
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