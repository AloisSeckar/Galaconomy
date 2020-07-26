package galaconomy.gui.pane;

import javafx.scene.control.*;
import javafx.scene.layout.*;

public class StellarTimePane extends AnchorPane {
    
    private final Label stellarTimeText;
    
    private static StellarTimePane INSTANCE;
    
    public static StellarTimePane getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StellarTimePane();
        }
        return INSTANCE;
    }
    
    private StellarTimePane() {
        stellarTimeText = new Label();
        stellarTimeText.getStyleClass().add("pane-info-name");
        super.getChildren().add(stellarTimeText);
    }

    public void update(long stellarTime) {
        stellarTimeText.setText(String.valueOf(stellarTime));
    }
}
