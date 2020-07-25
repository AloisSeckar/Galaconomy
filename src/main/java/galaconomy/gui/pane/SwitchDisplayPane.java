package galaconomy.gui.pane;

import galaconomy.gui.BasicGameLayout;
import galaconomy.universe.IDisplayable;
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
    
    private IDisplayable elementToDisplay;

    public SwitchDisplayPane() {
        switchToGalaxyButton = new Button("Universe");
        switchToGalaxyButton.setMinWidth(BUTTON_WIDTH);
        switchToGalaxyButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            BasicGameLayout.getInstance().switchToGalaxy();
        });
        super.getChildren().add(switchToGalaxyButton);
        AnchorPane.setLeftAnchor(switchToGalaxyButton, ZERO_ANCHOR);
        AnchorPane.setTopAnchor(switchToGalaxyButton, ZERO_ANCHOR);
        
        switchToSystemButton = new Button("System");
        switchToSystemButton.setMinWidth(BUTTON_WIDTH);
        switchToSystemButton.setDisable(true);
        switchToSystemButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            BasicGameLayout.getInstance().switchToSystem((Star) elementToDisplay);
        });
        super.getChildren().add(switchToSystemButton);
        AnchorPane.setLeftAnchor(switchToSystemButton, BUTTON_WIDTH);
        AnchorPane.setBottomAnchor(switchToSystemButton, ZERO_ANCHOR);
        
        switchToBaseButton = new Button("Base");
        switchToBaseButton.setMinWidth(BUTTON_WIDTH);
        switchToBaseButton.setDisable(true);
        switchToBaseButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            BasicGameLayout.getInstance().switchToSystem((Star) elementToDisplay);
        });
        super.getChildren().add(switchToBaseButton);
        AnchorPane.setLeftAnchor(switchToBaseButton, BUTTON_WIDTH * 2);
        AnchorPane.setBottomAnchor(switchToBaseButton, ZERO_ANCHOR);
    }

    public void setElementToDisplay(IDisplayable elementToDisplay) {
        this.elementToDisplay = elementToDisplay;
        
        switchToSystemButton.setDisable(!(elementToDisplay instanceof Star));
        switchToBaseButton.setDisable(!(elementToDisplay instanceof Base));
    }
    
}
