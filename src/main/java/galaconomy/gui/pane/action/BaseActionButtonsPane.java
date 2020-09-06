package galaconomy.gui.pane.action;

import de.jensd.fx.glyphs.fontawesome.*;
import galaconomy.gui.BasicGameLayout;
import galaconomy.gui.window.ShipBuyWindow;
import galaconomy.universe.IDisplayable;
import galaconomy.universe.map.Base;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BaseActionButtonsPane extends ActionButtonsPane {
    
    private final Button buyShipButton;

    public BaseActionButtonsPane() {
        
        getGoToButton().setTooltip(new Tooltip("Focus to this base"));
        
        buyShipButton = new Button();
        buyShipButton.setMinWidth(BUTTON_SIZE);
        buyShipButton.setMinHeight(BUTTON_SIZE);
        buyShipButton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.ROCKET));
        buyShipButton.setTooltip(new Tooltip("Buy ship on this base"));
        buyShipButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            buyShip();
        });
        super.getChildren().add(buyShipButton);
        AnchorPane.setLeftAnchor(buyShipButton, BUTTON_SIZE);
    }
    
    @Override
    public void setDisplayedItem(IDisplayable displayedItem) {
        super.setDisplayedItem(displayedItem);
        Base base = (Base) getDisplayedItem();
        buyShipButton.setDisable(!base.isShipyard());
    }
    
    @Override
    public void goToDisplayedItem() {
        BasicGameLayout.getInstance().switchToBase((Base) getDisplayedItem());
    }
    
    ////////////////////////////////////////////////////////////////////////////

    private void buyShip() {
        Base base = (Base) getDisplayedItem();
        ShipBuyWindow window = new ShipBuyWindow(base);
        window.show();
    }
    
}
