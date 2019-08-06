package galaconomy.gui;

import galaconomy.gui.pane.BasicDisplayPane;
import galaconomy.gui.window.*;
import galaconomy.universe.*;
import galaconomy.universe.player.Player;
import galaconomy.universe.systems.Star;
import galaconomy.universe.traffic.*;
import galaconomy.utils.InfoUtils;
import galaconomy.utils.MathUtils;
import java.util.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.slf4j.*;

public class PlayerFrame extends AnchorPane implements IEngineSubscriber {
    
    private static final Logger LOG = LoggerFactory.getLogger(PlayerFrame.class);
    
    private final Label playerName;
    private final ImageView playerImage;
    private final Label playerCredits;
    
    private final BasicDisplayPane infoBox;
    private final TilePane shipBox;
    
    private final Button getShipButton = new Button("Get a ship");
    private final List<Button> shipDetailButtons = new ArrayList<>();
    
    public PlayerFrame(BasicDisplayPane info) {
        super.setMinHeight(155);
        super.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        super.getStyleClass().add("pane-info");
        
        this.infoBox = info;
        
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
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        // DO NOTHING... ?
    }

    public void displayPlayer() {
        Player player = UniverseManager.getInstance().getPlayer();
        
        playerName.setText(player.displayName());
        playerCredits.setText("Credits: " + String.valueOf(player.getCredits()));
        
        Image playerImg = new Image(getClass().getResourceAsStream(player.getImage()));
        playerImage.setImage(playerImg);
        
        getShipButton.setVisible(true);
    }
    
    public void buyShip(String shipName, ShipClass shipClass, Star location) {
        Player player = UniverseManager.getInstance().getPlayer();
        
        Ship newShip = new Ship(shipName, shipClass, location);
        newShip.addOwner(player);

        Button newShipButton = new Button(newShip.displayName());
        newShipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            infoBox.setElementToDisplay(newShip);
            if (newShip.getCurrentLocation() != null) {
                ShipDispatchWindow window = new ShipDispatchWindow(this, newShip);
                window.show();
            } else {
                InfoUtils.showMessage(newShip.displayName() + " is under way...");
            }
        });
        shipDetailButtons.add(newShipButton);

        shipBox.getChildren().add(newShipButton);

        player.spendCredits(shipClass.getPrice());
        playerCredits.setText("Credits: " + String.valueOf(player.getCredits()));  
        
        LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + player.displayName() + " bought new " + shipClass.displayName() + " caalled " + shipName);            
    }

    public void planRoute(Ship ship, Star arrival) {
        Star departure = ship.getCurrentLocation();

        double distance = MathUtils.getDistance(departure.getX(), departure.getY(), arrival.getX(), arrival.getY());

        Route newRoute = new Route(ship, departure, arrival, distance);
        UniverseManager.getInstance().addRoute(newRoute);

        LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + ship.displayName() + " set off from " + departure.displayName() + " system to " + arrival.displayName());             
    }
}
