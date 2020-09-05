package galaconomy.gui.pane.action;

import galaconomy.gui.BasicGameLayout;
import galaconomy.universe.map.Star;
import javafx.scene.control.Tooltip;

public class StarActionButtonsPane extends ActionButtonsPane {

    public StarActionButtonsPane() {
        getGoToButton().setTooltip(new Tooltip("Focus to this system"));
    }
    
    @Override
    public void goToDisplayedItem() {
        BasicGameLayout.getInstance().switchToSystem((Star) getDisplayedItem());
    }
    
}
