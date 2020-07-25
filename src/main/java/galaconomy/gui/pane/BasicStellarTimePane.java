package galaconomy.gui.pane;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BasicStellarTimePane extends AnchorPane {
    
    private final Label stellarTimeText;
    
    public BasicStellarTimePane() {
        stellarTimeText = new Label();
        stellarTimeText.getStyleClass().add("pane-info-name");
        super.getChildren().add(stellarTimeText);
    }

    public void update(long stellarTime) {
        stellarTimeText.setText(String.valueOf(stellarTime));
    }
}
