package galaconomy.gui.window;

import galaconomy.gui.window.pane.ShipDispatchPane;
import galaconomy.gui.pane.*;
import galaconomy.universe.economy.Cargo;
import galaconomy.universe.map.Base;
import galaconomy.universe.map.Star;
import galaconomy.universe.traffic.Ship;
import galaconomy.utils.InfoUtils;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

public class ShipWindow extends Stage {
    
    private final ImageView imgView;
    
    private final ShipDispatchPane shipDispatchPane;
    
    public ShipWindow(PlayerPane parent, Ship ship) {
        super.setTitle(ship.displayName());
        super.initModality(Modality.APPLICATION_MODAL);
        super.initOwner((Stage) parent.getScene().getWindow());
        
        AnchorPane windowLayout = new AnchorPane();
        
        imgView = new ImageView();
        imgView.setFitWidth(100);
        imgView.setFitHeight(100);
        imgView.getStyleClass().add("pane-info-img");
        windowLayout.getChildren().add(imgView);
        AnchorPane.setLeftAnchor(imgView, 5d);
        AnchorPane.setTopAnchor(imgView, 5d);
        
        Image shipImg = new Image(getClass().getResourceAsStream(ship.getImage()));
        imgView.setImage(shipImg);
        
        GridPane cargoPane = new GridPane();
        cargoPane.add(new Label("Cargo list:"), 0, 0);
        int col = 0;
        for (Cargo cargo : ship.getCurrentCargo()) {
            Image cargoImage = new Image(getClass().getResourceAsStream(cargo.getImage()));
            ImageView cargoImageView = new ImageView(cargoImage);
            cargoImageView.setFitWidth(64);
            cargoImageView.setFitHeight(64);
            
            Button cargoButton = new Button();
            cargoButton.setGraphic(cargoImageView);
            cargoButton.setTooltip(new Tooltip(cargo.displayName()));
            cargoPane.add(cargoButton, col++, 1);
        }
        windowLayout.getChildren().add(cargoPane);
        AnchorPane.setLeftAnchor(cargoPane,115d);
        AnchorPane.setTopAnchor(cargoPane, 5d);
        
        String location = ship.getCurrentSystem() != null ? ship.getCurrentSystem().displayName() : "Deep space";
        shipDispatchPane = new ShipDispatchPane(location);
        windowLayout.getChildren().add(shipDispatchPane);
        AnchorPane.setLeftAnchor(shipDispatchPane, 115d);
        AnchorPane.setTopAnchor(shipDispatchPane, 120d);
        
        HBox menuBox = new HBox(20);
        menuBox.setAlignment(Pos.CENTER);

        Button buyButton = new Button("Launch");
        buyButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Base destination = shipDispatchPane.getDestination();
            
            if (destination != null) {
                if (true /*TODO route plaing restrictions*/) {
                    parent.planTravel(ship, destination);
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
        
        windowLayout.getChildren().add(menuBox);
        AnchorPane.setLeftAnchor(menuBox, 115d);
        AnchorPane.setTopAnchor(menuBox, 200d);
        
        Scene dialogScene = new Scene(windowLayout, 450, 300);
        super.setScene(dialogScene);
    }

}
