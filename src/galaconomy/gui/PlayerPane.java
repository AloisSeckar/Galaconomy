package galaconomy.gui;

import galaconomy.universe.IEngineSubscriber;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.player.Player;
import galaconomy.universe.traffic.Ship;
import galaconomy.universe.traffic.ShipGenerator;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

public class PlayerPane extends AnchorPane implements IEngineSubscriber {
    
    private final Label playerName;
    private final ImageView playerImage;
    
    private final TilePane shipBox;
    
    private final Button getShipButton = new Button("Get a ship");
    private final List<Button> shipDetailButtons = new ArrayList<>();
    
    public PlayerPane(final InfoPane info) {
        super.setMinHeight(135);
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
        
        shipBox = new TilePane();
        super.getChildren().add(shipBox);
        AnchorPane.setLeftAnchor(shipBox, 250d);
        AnchorPane.setTopAnchor(shipBox, 5d);
        
        getShipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            Player player = UniverseManager.getInstance().getPlayer();
            Ship newShip = ShipGenerator.createRandomShip();
            newShip.addOwner(player);
            
            Button newShipButton = new Button(newShip.displayName());
            newShipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
                info.setElementToDisplay(newShip);
            });
            shipDetailButtons.add(newShipButton);
            
            shipBox.getChildren().add(newShipButton);
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
        
        Image playerImg = new Image(getClass().getResourceAsStream(player.getImage()));
        playerImage.setImage(playerImg);
        
        getShipButton.setVisible(true);
    }

}
