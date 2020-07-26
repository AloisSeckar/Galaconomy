package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import org.slf4j.*;

public class ControlsFrame extends BorderPane implements IEngineSubscriber {
    
    private static final Logger LOG = LoggerFactory.getLogger(ControlsFrame.class);
    
    private final PlayerPane playerPane;
    private final EngineSpeedPane engineSpeedPane;
    private final StellarTimePane stellarTimePane;
    private final SwitchDisplayPane switchDisplayPane;
    
    private static ControlsFrame INSTANCE;
    
    public static ControlsFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ControlsFrame();
        }
        return INSTANCE;
    }
    
    private ControlsFrame() {
        super.setMinHeight(Constants.BOTTOM_PANEL_Y);
        super.setBackground(new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        super.getStyleClass().add("pane-info");
        
        this.playerPane = new PlayerPane();
        
        Tab playerTab = new Tab("Player", playerPane);
        
        TabPane controlsTabsheet = new TabPane(playerTab);
        
        this.setCenter(controlsTabsheet);
        
        AnchorPane bottomPane = new AnchorPane();
        
        engineSpeedPane = new EngineSpeedPane();
        bottomPane.getChildren().add(engineSpeedPane);
        AnchorPane.setLeftAnchor(engineSpeedPane, 5d);
        AnchorPane.setBottomAnchor(engineSpeedPane, 2d);
        
        stellarTimePane = new StellarTimePane();
        stellarTimePane.setMaxWidth(250);
        stellarTimePane.getStyleClass().add("pane-info-name");
        bottomPane.getChildren().add(stellarTimePane);
        AnchorPane.setLeftAnchor(stellarTimePane, 250d);
        AnchorPane.setBottomAnchor(stellarTimePane, 2d);
        
        switchDisplayPane = new SwitchDisplayPane();
        bottomPane.getChildren().add(switchDisplayPane);
        AnchorPane.setRightAnchor(switchDisplayPane, 5d);
        AnchorPane.setBottomAnchor(switchDisplayPane, 2d);
        
        this.setBottom(bottomPane);
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        stellarTimePane.update(stellarTime);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    public PlayerPane getPlayerPane() {
        return playerPane;
    }
    
    public final void setElementToDisplay(IDisplayable elementToDisplay) {
        switchDisplayPane.setElementToDisplay(elementToDisplay);
    }
}
