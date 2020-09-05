package galaconomy.gui.pane.action;

import de.jensd.fx.glyphs.fontawesome.*;
import galaconomy.gui.BasicGameLayout;
import galaconomy.gui.pane.DisplayPane;
import galaconomy.gui.window.ShipWindow;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.Ship;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class ShipActionButtonsPane extends ActionButtonsPane {
    
    private final Button shipDetailButton;

    public ShipActionButtonsPane() {
        
        getGoToButton().setTooltip(new Tooltip("Focus to this ship"));
        
        shipDetailButton = new Button();
        shipDetailButton.setMinWidth(BUTTON_SIZE);
        shipDetailButton.setMinHeight(BUTTON_SIZE);
        shipDetailButton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.SPACE_SHUTTLE));
        shipDetailButton.setTooltip(new Tooltip("View ship details"));
        shipDetailButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            shipDetails();
        });
        super.getChildren().add(shipDetailButton);
        AnchorPane.setLeftAnchor(shipDetailButton, BUTTON_SIZE);
    }
    
    @Override
    public void goToDisplayedItem() {
        Ship ship = (Ship) getDisplayedItem();
        Base currentBase = ship.getCurrentBase();
        if (currentBase != null) {
            BasicGameLayout.getInstance().switchToBase(currentBase);
        } else {
            Star currentSystem = ship.getCurrentSystem();
            if (currentSystem != null) {
                BasicGameLayout.getInstance().switchToSystem(currentSystem);
            }
        }
        DisplayPane.getInstance().setElementToDisplay(ship);
    }
    
    ////////////////////////////////////////////////////////////////////////////

    private void shipDetails() {
        Ship ship = (Ship) getDisplayedItem();
        ShipWindow window = new ShipWindow(ship);
        window.show();
    }
    
}
