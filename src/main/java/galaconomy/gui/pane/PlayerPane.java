package galaconomy.gui.pane;

import galaconomy.gui.window.*;
import galaconomy.universe.*;
import galaconomy.universe.economy.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import galaconomy.universe.traffic.*;
import galaconomy.utils.DisplayUtils;
import galaconomy.utils.StorageUtils;
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
    private final BuildingPane buildingPane;
    
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
            buyShip("USS Test", ShipGenerator.getShipClassById(0), UniverseUtils.getRandomBase());
        });
        quickShipButton.setVisible(false);
        super.getChildren().add(quickShipButton);
        AnchorPane.setLeftAnchor(quickShipButton, 150d);
        AnchorPane.setTopAnchor(quickShipButton, 35d);
        
        buildingPane = BuildingPane.getInstance();
        buildingPane.setVisible(false);
        super.getChildren().add(buildingPane);
        AnchorPane.setRightAnchor(buildingPane, 5d);
        AnchorPane.setTopAnchor(buildingPane, 5d);
    }

    public void displayPlayer() {
        Player player = UniverseManager.getInstance().getHumanPlayer();
        
        playerName.setText(player.displayName());
        playerCredits.setText("Credits: " + String.valueOf(player.getCredits()));
        
        Image playerImg = new Image(getClass().getResourceAsStream(player.getImage()));
        playerImage.setImage(playerImg);
        
        getShipButton.setVisible(true);
        quickShipButton.setVisible(true);
        
        buildingPane.setVisible(true);
    }
    
    public void buyShip(String shipName, ShipClass shipClass, Base location) {
        Player player = UniverseManager.getInstance().getHumanPlayer();
        
        Ship newShip = new Ship(shipName, shipClass, location);
        newShip.changeOwner(player);
        player.addShip(newShip);
        
        // TODO delete
        StorageUtils.storeCargo(new Cargo(Goods.getGoodsByName("Metal"), 10, player, newShip), newShip);
        StorageUtils.storeCargo(new Cargo(Goods.getGoodsByName("Chips"), 10, player, newShip), newShip);
        StorageUtils.storeCargo(new Cargo(Goods.getGoodsByName("Food"), 10, player, newShip), newShip);
        //
        
        Button newShipButton = getShipButton(newShip);
        shipDetailButtons.add(newShipButton);
        shipBox.getChildren().add(newShipButton);

        player.spendCredits(shipClass.getPrice());
        playerCredits.setText("Credits: " + String.valueOf(player.getCredits()));  
        
        LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + player.displayName() + " bought new " + shipClass.displayName() + " called " + shipName);            
    }

    public void planTravel(Ship ship, Base arrival) {
        Base departure = ship.getCurrentBase();

        Travel newTravel = new Travel(ship, departure, arrival);
        UniverseManager.getInstance().addTravel(newTravel);

        LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + ship.displayName() + " set off from " + departure.displayName() + " to " + arrival.displayName());             
    }
    
    public void loadPlayerShips() {
        shipBox.getChildren().removeAll(shipDetailButtons);
        shipDetailButtons.clear();
            
        Player player = UniverseManager.getInstance().getHumanPlayer();
        
        player.getShips().forEach(ship -> {
            Button newShipButton = getShipButton(ship);
            shipDetailButtons.add(newShipButton);
            shipBox.getChildren().add(newShipButton);
        });
    }
    
    public void updatePlayerCredits() {
        Player player = UniverseManager.getInstance().getHumanPlayer();
        playerCredits.setText("Credits: " + String.valueOf(player.getCredits()));  
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private Button getShipButton(Ship ship) {
        Button shipButton = new Button();
        shipButton.setGraphic(DisplayUtils.getImageView(ship.getImage(), 64));
        shipButton.setTooltip(new Tooltip(ship.displayName()));
        shipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            DisplayPane.getInstance().setElementToDisplay(ship);
            
            ShipWindow window = new ShipWindow(this, ship);
            window.show();
        });
        return shipButton;
    }
}
