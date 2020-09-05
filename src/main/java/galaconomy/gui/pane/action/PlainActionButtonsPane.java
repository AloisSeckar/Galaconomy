package galaconomy.gui.pane.action;

import javafx.scene.control.Tooltip;

public class PlainActionButtonsPane extends ActionButtonsPane {
    
    public PlainActionButtonsPane() {
        getGoToButton().setTooltip(new Tooltip("Cannot go there"));
        getGoToButton().setDisable(true);
    }
    
    @Override
    public void goToDisplayedItem() {
        // button does no action
    }
    
}
