package galaconomy.gui;

import galaconomy.universe.IEngineSubscriber;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.player.Player;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class PlayerPane extends AnchorPane implements IEngineSubscriber {
    
    private final Label playerName;
    private final ImageView playerImage;
    
    public PlayerPane() {
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
    }

}
