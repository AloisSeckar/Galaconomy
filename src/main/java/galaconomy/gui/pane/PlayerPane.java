package galaconomy.gui.pane;

import galaconomy.gui.*;
import galaconomy.gui.window.*;
import galaconomy.universe.*;
import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import galaconomy.universe.map.Star;
import galaconomy.universe.traffic.*;
import java.util.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.slf4j.*;

public class PlayerPane extends AnchorPane {
    
    private static final Logger LOG = LoggerFactory.getLogger(PlayerPane.class);
    
    private final Label playerName;
    private final ImageView playerImage;
    private final Label playerCredits;
    
    private final TilePane shipBox;
    
    private final Button getShipButton = new Button("Get a ship");
    private final Button quickShipButton = new Button("Quick ship");  // TODO delete
    
    private final List<Button> shipDetailButtons = new ArrayList<>();
    
    private static PlayerPane INSTANCE;
    
    public static PlayerPane getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerPane();
        }
        return INSTANCE;
    }
    
    private PlayerPane() {
        super.setMinHeight(155);
        super.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        super.getStyleClass().add("pane-info");
        
        playerName = new Label();
        playerName.getStyleClass().add("pane-info-name");
        super.getChildren().add(playerName);
        AnchorPane.setLeftAnchor(playerName, 5d);
        AnchorPane.setTopAnchor(playerName, 5d);
        
        playerImage = new ImageView();
        playerImage.setFitWidth(100);
        playerImage.setFitHeight(100);
        playerImage.getStyleClass().add("pane-info-img");
        super.getChildren().add(playerImage);
        AnchorPane.setLeftAnchor(playerImage, 5d);
        AnchorPane.setTopAnchor(playerImage, 25d);
        
        playerCredits = new Label();
        super.getChildren().add(playerCredits);
        AnchorPane.setLeftAnchor(playerCredits, 5d);
        AnchorPane.setTopAnchor(playerCredits, 130d);
        
        shipBox = new TilePane();
        super.getChildren().add(shipBox);
        AnchorPane.setLeftAnchor(shipBox, 250d);
        AnchorPane.setTopAnchor(shipBox, 5d);
        
        getShipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            ShipBuyWindow window = new ShipBuyWindow(this);
            window.show();
        });
        getShipButton.setVisible(false);
        super.getChildren().add(getShipButton);
        AnchorPane.setLeftAnchor(getShipButton, 150d);
        AnchorPane.setTopAnchor(getShipButton, 5d);
        
        // TODO delete
        quickShipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            buyShip("USS Test", ShipGenerator.getShipClassById(0), UniverseUtils.getRandomSystem());
        });
        quickShipButton.setVisible(false);
        super.getChildren().add(quickShipButton);
        AnchorPane.setLeftAnchor(quickShipButton, 150d);
        AnchorPane.setTopAnchor(quickShipButton, 35d);
    }

    public void displayPlayer() {
        Player player = UniverseManager.getInstance().getPlayer();
        
        playerName.setText(player.displayName());
        playerCredits.setText("Credits: " + String.valueOf(player.getCredits()));
        
        Image playerImg = new Image(getClass().getResourceAsStream(player.getImage()));
        playerImage.setImage(playerImg);
        
        getShipButton.setVisible(true);
        quickShipButton.setVisible(true);
    }
    
    public void buyShip(String shipName, ShipClass shipClass, Star location) {
        Player player = UniverseManager.getInstance().getPlayer();
        
        Ship newShip = new Ship(shipName, shipClass, location);
        newShip.addOwner(player);
        player.addShip(newShip);
        
        // TODO delete
        Star origin = UniverseUtils.getRandomSystem();
        newShip.getCargoList().add(new Cargo(Goods.getGoodsByName("Metal"), 10, 150, origin, 10000));
        newShip.getCargoList().add(new Cargo(Goods.getGoodsByName("Chips"), 10, 200, origin, 10000));
        newShip.getCargoList().add(new Cargo(Goods.getGoodsByName("Food"), 10, 101, origin, 10000));
        //
        
        Image newShipImage = new Image(getClass().getResourceAsStream(newShip.getImage()));
        ImageView newShipImageView = new ImageView(newShipImage);
        newShipImageView.setFitWidth(64);
        newShipImageView.setFitHeight(64);

        Button newShipButton = new Button();
        newShipButton.setGraphic(newShipImageView);
        newShipButton.setTooltip(new Tooltip(newShip.displayName()));
        newShipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            DisplayPane.getInstance().setElementToDisplay(newShip);
            
            ShipWindow window = new ShipWindow(this, newShip);
            window.show();
        });
        shipDetailButtons.add(newShipButton);

        shipBox.getChildren().add(newShipButton);

        player.spendCredits(shipClass.getPrice());
        playerCredits.setText("Credits: " + String.valueOf(player.getCredits()));  
        
        LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + player.displayName() + " bought new " + shipClass.displayName() + " caalled " + shipName);            
    }

    public void planTravel(Ship ship, Star arrival) {
        Star departure = ship.getCurrentLocation();

        Travel newTravel = new Travel(ship, departure, arrival);
        UniverseManager.getInstance().addTravel(newTravel);

        LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + ship.displayName() + " set off from " + departure.displayName() + " system to " + arrival.displayName());             
    }
    
    public void loadPlayerShips() {
        shipBox.getChildren().removeAll(shipDetailButtons);
        shipDetailButtons.clear();
            
        Player player = UniverseManager.getInstance().getPlayer();
        
        for (Ship ship : player.getShips()) {
            Image newShipImage = new Image(getClass().getResourceAsStream(ship.getImage()));
            ImageView newShipImageView = new ImageView(newShipImage);
            newShipImageView.setFitWidth(64);
            newShipImageView.setFitHeight(64);
            
            Button newShipButton = new Button();
            newShipButton.setGraphic(newShipImageView);
            newShipButton.setTooltip(new Tooltip(ship.displayName()));
            newShipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
                DisplayPane.getInstance().setElementToDisplay(ship);

                ShipWindow window = new ShipWindow(this, ship);
                window.show();
            });
            shipDetailButtons.add(newShipButton);

            shipBox.getChildren().add(newShipButton);
        }
    }
}
