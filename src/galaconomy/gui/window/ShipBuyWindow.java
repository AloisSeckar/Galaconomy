package galaconomy.gui.window;

import galaconomy.gui.PlayerFrame;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.systems.Star;
import galaconomy.universe.traffic.*;
import galaconomy.utils.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

public class ShipBuyWindow extends Stage {
    
    private final TextField shipNameTF;
    private final ComboBox<ObservableList<ShipClass>> shipClassCB;
    private final ComboBox<ObservableList<Star>> locationCB;
    
    public ShipBuyWindow(PlayerFrame parent) {
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
            String shipName = InputUtils.trimToNull(shipNameTF.getText());
            ShipClass shipClass = (ShipClass) shipClassCB.getValue();
            Star location = (Star) locationCB.getValue();
            
            if (shipName != null && shipClass != null && location != null) {
                if (UniverseManager.getInstance().getPlayer().getCredits() >= shipClass.getPrice()) {
                    parent.buyShip(shipName, shipClass, location);
                    close();
                } else {
                    InfoUtils.showMessage("Insufficent credits!");
                }
            } else {
                InfoUtils.showMessage("Please fill in all inputs first");
            }
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
