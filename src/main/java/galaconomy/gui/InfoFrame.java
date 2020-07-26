package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class InfoFrame extends AnchorPane implements IEngineSubscriber {
    
    private final DisplayPane infoPane;
    
    private static InfoFrame INSTANCE;
    
    public static InfoFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InfoFrame();
        }
        return INSTANCE;
    }
    
    private InfoFrame() {
        super.setMinWidth(Constants.SIDE_PANEL_X + 20);
        super.setMaxWidth(Constants.SIDE_PANEL_X + 20);
        super.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
        super.getStyleClass().add("pane-info");
        
        infoPane = new DisplayPane(Constants.SIDE_PANEL_X);
        super.getChildren().add(infoPane);
        AnchorPane.setLeftAnchor(infoPane, 5d);
        AnchorPane.setTopAnchor(infoPane, 5d);
    }
    
    public DisplayPane getInfoPane() {
        return infoPane;
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        infoPane.reloadInfoPanel();
    }

    @Override
    public boolean isActive() {
        return true;
    }
    
    public final void setElementToDisplay(IDisplayable elementToDisplay) {
        infoPane.setElementToDisplay(elementToDisplay);
    }
}
