package galaconomy.gui.window;

import galaconomy.gui.PlayerPane;
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

public class DispatchShipWindow extends Stage {
    
    private final Label shipNameLabel;
    private final Label locationLabel;
    private final ComboBox<ObservableList<Star>> destinationCB;
    
    public DispatchShipWindow(PlayerPane parent, Ship ship) {
        super.setTitle("Dispatch ship");
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) parent.getScene().getWindow());
        
        GridPane inputsGrid = new GridPane();
        inputsGrid.setAlignment(Pos.CENTER);
        inputsGrid.setHgap(10);
        inputsGrid.setVgap(10);
        inputsGrid.setPadding(new Insets(5, 5, 5, 5));
        
        inputsGrid.add(new Label("Ship:"), 0, 0);
        
        shipNameLabel = new Label(ship.displayName());
        inputsGrid.add(shipNameLabel, 1, 0);
        
        inputsGrid.add(new Label("Ship:"), 0, 1);
        
        locationLabel = new Label(ship.getCurrentLocation().displayName());
        inputsGrid.add(locationLabel, 1, 1);
        
        inputsGrid.add(new Label("Select destination:"), 0, 2);
        
        ObservableList<Star> availableStars = 
            FXCollections.observableArrayList(
                    UniverseManager.getInstance().getStars().values()
            );
        destinationCB = new ComboBox(availableStars);
        inputsGrid.add(destinationCB, 1, 2);
        
        HBox menuBox = new HBox(20);
        menuBox.setAlignment(Pos.CENTER);

        Button buyButton = new Button("Launch");
        buyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Star destination = (Star) destinationCB.getValue();
            
            if (destination != null) {
                if (true /*TODO route plaing restrictions*/) {
                    parent.planRoute(ship, destination);
                    close();
                } else {
                    InfoUtils.showMessage("Cannot depart! TODO why");
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
