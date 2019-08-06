package galaconomy.gui;

import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class InfoFrame extends AnchorPane implements IEngineSubscriber {
    
    private final InfoPane infoPane;
    private final EngineSpeedPane engineSpeedPane;
    private final StellarTimePane stellarTimePane;
    
    public InfoFrame(int width) {
        super.setMinWidth(width + 20);
        super.setMaxWidth(width + 20);
        super.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
        super.getStyleClass().add("pane-info");
        
        infoPane = new InfoPane(width);
        super.getChildren().add(infoPane);
        AnchorPane.setLeftAnchor(infoPane, 5d);
        AnchorPane.setTopAnchor(infoPane, 5d);
        
        engineSpeedPane = new EngineSpeedPane();
        super.getChildren().add(engineSpeedPane);
        AnchorPane.setLeftAnchor(engineSpeedPane, 5d);
        AnchorPane.setBottomAnchor(engineSpeedPane, 40d);
        
        stellarTimePane = new StellarTimePane();
        stellarTimePane.setMaxWidth(width);
        stellarTimePane.getStyleClass().add("pane-info-name");
        super.getChildren().add(stellarTimePane);
        AnchorPane.setLeftAnchor(stellarTimePane, 5d);
        AnchorPane.setBottomAnchor(stellarTimePane, 5d);
        
        infoPane.setElementToDisplay(null);
    }

    public InfoPane getInfoPane() {
        return infoPane;
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        stellarTimePane.update(stellarTime);
        infoPane.reloadInfoPanel();
    }
}
