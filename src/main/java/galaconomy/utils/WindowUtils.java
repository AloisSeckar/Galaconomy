package galaconomy.utils;

import galaconomy.universe.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class WindowUtils {
    
    private WindowUtils() {
    }
    
    public static HBox getWindowToolbar(Stage parent, IEngineSubscriber subscriber, Button... actionButtons) {
        HBox menuBox = new HBox(10);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.setPadding(new Insets(0, 0, 10, 0));
        
        menuBox.getChildren().addAll(actionButtons);
        
        Button cancelButton = new Button("Close");
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            UniverseManager.getInstance().unRegisterSubscriber(subscriber);
            parent.close();
        });
        menuBox.getChildren().add(cancelButton);
        
        return menuBox;
    }
}
