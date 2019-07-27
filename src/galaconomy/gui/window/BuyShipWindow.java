package galaconomy.gui.window;

import galaconomy.gui.PlayerPane;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.systems.Star;
import galaconomy.universe.traffic.ShipClass;
import galaconomy.universe.traffic.ShipGenerator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

public class BuyShipWindow extends Stage {
    
    private final TextField shipNameTF;
    private final ComboBox<ObservableList<ShipClass>> shipClassCB;
    private final ComboBox<ObservableList<Star>> locationCB;
    
    public BuyShipWindow(PlayerPane parent) {
        super.setTitle("Select ship to buy");
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) parent.getScene().getWindow());
        
        GridPane inputsGrid = new GridPane();
        inputsGrid.setAlignment(Pos.CENTER);
        inputsGrid.setHgap(10);
        inputsGrid.setVgap(10);
        inputsGrid.setPadding(new Insets(5, 5, 5, 5));
        
        Label shipNameLabel = new Label("Name your ship:");
        inputsGrid.add(shipNameLabel, 0, 0);
        
        shipNameTF = new TextField();
        inputsGrid.add(shipNameTF, 1, 0);
        
        Label shipClassLabel = new Label("Select class:");
        inputsGrid.add(shipClassLabel, 0, 1);
        
        ObservableList<ShipClass> availableShipClasses = 
            FXCollections.observableArrayList(
                        ShipGenerator.getShipClassById(0),
                        ShipGenerator.getShipClassById(1),
                        ShipGenerator.getShipClassById(2),
                        ShipGenerator.getShipClassById(3),
                        ShipGenerator.getShipClassById(4)
            );
        shipClassCB = new ComboBox(availableShipClasses);
        inputsGrid.add(shipClassCB, 1, 1);
        
        Label locationLabel = new Label("Select init. location:");
        inputsGrid.add(locationLabel, 0, 2);
        
        ObservableList<Star> availableStars = 
            FXCollections.observableArrayList(
                    UniverseManager.getInstance().getStars().values()
            );
        locationCB = new ComboBox(availableStars);
        inputsGrid.add(locationCB, 1, 2);
        
        HBox menuBox = new HBox(20);
        menuBox.setAlignment(Pos.CENTER);

        Button buyButton = new Button("Buy ship");
        buyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            parent.buyShip(shipNameTF.getText(), (ShipClass) shipClassCB.getValue(),(Star) locationCB.getValue());
            close();
        });
        menuBox.getChildren().add(buyButton);
        
        Button cancelButton = new Button("Cancel");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            close();
        });
        menuBox.getChildren().add(cancelButton);

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().add(inputsGrid);
        mainLayout.getChildren().add(menuBox);
        
        Scene dialogScene = new Scene(mainLayout, 350, 200);
        
        super.setScene(dialogScene);
    }

}
