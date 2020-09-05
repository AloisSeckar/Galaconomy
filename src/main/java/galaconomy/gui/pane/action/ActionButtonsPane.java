package galaconomy.gui.pane.action;

import de.jensd.fx.glyphs.fontawesome.*;
import galaconomy.universe.IDisplayable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public abstract class ActionButtonsPane extends AnchorPane {
    
    public static final double BUTTON_SIZE = 45;
    public static final double ZERO_ANCHOR = 0;
    
    private IDisplayable displayedItem;
    
    private final Button goToButton;
    
    public ActionButtonsPane() {
        goToButton = new Button();
        goToButton.setMinWidth(BUTTON_SIZE);
        goToButton.setMinHeight(BUTTON_SIZE);
        goToButton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.BULLSEYE));
        goToButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            goToDisplayedItem();
        });
        super.getChildren().add(goToButton);
        AnchorPane.setLeftAnchor(goToButton, ZERO_ANCHOR);
    }

    protected final Button getGoToButton() {
        return goToButton;
    }
    
    public IDisplayable getDisplayedItem() {
        return displayedItem;
    }

    public void setDisplayedItem(IDisplayable displayedItem) {
        this.displayedItem = displayedItem;
    }
    
    public abstract void goToDisplayedItem();
    
    ////////////////////////////////////////////////////////////////////////////

    /*
    private void gotoElementToDisplay() {
        IDisplayable elementToDisplay = DisplayPane.getInstance().getElementToDisplay();
        if (elementToDisplay instanceof Star) {
            BasicGameLayout.getInstance().switchToSystem((Star) elementToDisplay);
        } else if (elementToDisplay instanceof Base) { 
            BasicGameLayout.getInstance().switchToBase((Base) elementToDisplay);
        } else if (elementToDisplay instanceof Ship) { 
            Ship ship = (Ship) elementToDisplay;
            Base currentBase = ship.getCurrentBase();
            if (currentBase != null) {
                BasicGameLayout.getInstance().switchToBase(currentBase);
            } else {
                Star currentSystem = ship.getCurrentSystem();
                if (currentSystem != null) {
                    BasicGameLayout.getInstance().switchToSystem(currentSystem);
                }
            }
        } else if (elementToDisplay instanceof RiftGate) {
            RiftGate gate = (RiftGate) elementToDisplay;
            BasicGameLayout.getInstance().switchToSystem((Star) gate.getParent());  
        } else if (elementToDisplay instanceof RiftPortal) {
            RiftPortal portal = (RiftPortal) elementToDisplay;
            BasicGameLayout.getInstance().switchToSystem((Star) portal.getParent());  
        } else if (elementToDisplay instanceof SurfaceTile) {
            SurfaceTile tile = (SurfaceTile) elementToDisplay;
            BasicGameLayout.getInstance().switchToBase((Base) tile.getParent());  
        } else if (elementToDisplay instanceof Building) {
            Building building = (Building) elementToDisplay;
            BasicGameLayout.getInstance().switchToBase(building.getParentBase());  
        }
    }
    */
    
}
