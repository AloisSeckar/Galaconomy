package galaconomy.gui.pane;

import galaconomy.gui.BasicGameLayout;
import galaconomy.universe.IDisplayable;
import galaconomy.universe.building.Building;
import galaconomy.universe.map.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SwitchDisplayPane extends AnchorPane {
    
    private static final double BUTTON_WIDTH = 75;
    private static final double ZERO_ANCHOR = 0;
    
    private final Button switchToGalaxyButton;
    private final Button switchToSystemButton;
    private final Button switchToBaseButton;
    
    private Star currentStar = null;
    private Base currentBase = null;

    private static SwitchDisplayPane INSTANCE;
    
    public static SwitchDisplayPane getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SwitchDisplayPane();
        }
        return INSTANCE;
    }
    
    private SwitchDisplayPane() {
        switchToGalaxyButton = new Button("Universe");
        switchToGalaxyButton.setMinWidth(BUTTON_WIDTH);
        switchToGalaxyButton.getStyleClass().setAll("btn","btn-danger");
        switchToGalaxyButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            currentBase = null;
            currentStar = null;
            BasicGameLayout.getInstance().switchToGalaxy();
        });
        super.getChildren().add(switchToGalaxyButton);
        AnchorPane.setLeftAnchor(switchToGalaxyButton, ZERO_ANCHOR);
        AnchorPane.setTopAnchor(switchToGalaxyButton, ZERO_ANCHOR);
        
        switchToSystemButton = new Button("System");
        switchToSystemButton.setMinWidth(BUTTON_WIDTH);
        switchToSystemButton.getStyleClass().setAll("btn","btn-warning");
        switchToSystemButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            currentBase = null;
            BasicGameLayout.getInstance().switchToSystem(currentStar);
        });
        super.getChildren().add(switchToSystemButton);
        AnchorPane.setLeftAnchor(switchToSystemButton, BUTTON_WIDTH);
        AnchorPane.setBottomAnchor(switchToSystemButton, ZERO_ANCHOR);
        
        switchToBaseButton = new Button("Base");
        switchToBaseButton.setMinWidth(BUTTON_WIDTH);
        switchToBaseButton.getStyleClass().setAll("btn","btn-success");
        switchToBaseButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            BasicGameLayout.getInstance().switchToBase(currentBase);
        });
        super.getChildren().add(switchToBaseButton);
        AnchorPane.setLeftAnchor(switchToBaseButton, BUTTON_WIDTH * 2);
        AnchorPane.setBottomAnchor(switchToBaseButton, ZERO_ANCHOR);
    }

    public void setElementToDisplay(IDisplayable elementToDisplay) {
        if (elementToDisplay instanceof Star) {
            currentStar = (Star) elementToDisplay;
        } else if (elementToDisplay instanceof Base) {
            currentBase = (Base) elementToDisplay;
            currentStar = (Star) currentBase.getParent();
        } 
    }
    
}
