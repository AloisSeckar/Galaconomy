package galaconomy.gui;

import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import galaconomy.utils.DisplayUtils;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class InfoFrame extends AnchorPane implements IEngineSubscriber {
    
    private static final DisplayPane displayPane = DisplayPane.getInstance();
    
    private static InfoFrame INSTANCE;
    
    public static InfoFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InfoFrame();
        }
        return INSTANCE;
    }
    
    private InfoFrame() {
        super.setMinWidth(DisplayUtils.SIDE_PANEL_X_WRAP);
        super.setMaxWidth(DisplayUtils.SIDE_PANEL_X_WRAP);
        super.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
        super.getStyleClass().add("pane-info");
        
        super.getChildren().add(displayPane);
        AnchorPane.setLeftAnchor(displayPane, 5d);
        AnchorPane.setTopAnchor(displayPane, 5d);
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        displayPane.reloadInfoPanel();
    }

    @Override
    public boolean isActive() {
        return true;
    }
}
