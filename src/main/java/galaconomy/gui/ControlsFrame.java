package galaconomy.gui;

import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import galaconomy.utils.DisplayUtils;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ControlsFrame extends BorderPane implements IEngineSubscriber {
    
    private final PlayerPane playerPane = PlayerPane.getInstance();
    private final EngineSpeedPane engineSpeedPane = EngineSpeedPane.getInstance();
    private final StellarTimePane stellarTimePane = StellarTimePane.getInstance();
    private final SwitchDisplayPane switchDisplayPane = SwitchDisplayPane.getInstance();
    
    private static ControlsFrame INSTANCE;
    
    public static ControlsFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ControlsFrame();
        }
        return INSTANCE;
    }
    
    private ControlsFrame() {
        super.setMinHeight(DisplayUtils.BOTTOM_PANEL_Y);
        super.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        super.getStyleClass().add("pane-info");
        
        Tab playerTab = new Tab("Player", playerPane);
        
        TabPane controlsTabsheet = new TabPane(playerTab);
        
        this.setCenter(controlsTabsheet);
        
        AnchorPane bottomPane = new AnchorPane();
        
        bottomPane.getChildren().add(engineSpeedPane);
        AnchorPane.setLeftAnchor(engineSpeedPane, 5d);
        AnchorPane.setBottomAnchor(engineSpeedPane, 2d);
        
        stellarTimePane.setMaxWidth(250);
        stellarTimePane.getStyleClass().add("pane-info-name");
        bottomPane.getChildren().add(stellarTimePane);
        AnchorPane.setLeftAnchor(stellarTimePane, 250d);
        AnchorPane.setBottomAnchor(stellarTimePane, 2d);
        
        bottomPane.getChildren().add(switchDisplayPane);
        AnchorPane.setRightAnchor(switchDisplayPane, 5d);
        AnchorPane.setBottomAnchor(switchDisplayPane, 2d);
        
        this.setBottom(bottomPane);
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        stellarTimePane.update(stellarTime);
        playerPane.updatePlayerCredits();
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public PlayerPane getPlayerPane() {
        return playerPane;
    }
}
