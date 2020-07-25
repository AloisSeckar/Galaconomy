package galaconomy.gui.pane;

import galaconomy.universe.UniverseManager;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BasicEngineSpeedPane extends AnchorPane {
    
    private static final double BUTTON_WIDTH = 45;
    private static final double ZERO_ANCHOR = 0;
    
    private final Label engineSpeedText;
    
    private final Button pauseButton;
    private final Button halfSpeedButton;
    private final Button normalSpeedButton;
    private final Button doubleSpeedButton;
    
    public BasicEngineSpeedPane() {
        
        engineSpeedText = new Label();
        engineSpeedText.getStyleClass().add("pane-info-name");
        super.getChildren().add(engineSpeedText);
        AnchorPane.setLeftAnchor(engineSpeedText, BUTTON_WIDTH * 4 + 10);
        AnchorPane.setBottomAnchor(engineSpeedText, ZERO_ANCHOR);
        
        pauseButton = new Button("||");
        pauseButton.setMinWidth(BUTTON_WIDTH);
        pauseButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            UniverseManager.getInstance().pauseEngine();
            updateText();
        });
        super.getChildren().add(pauseButton);
        AnchorPane.setLeftAnchor(pauseButton, ZERO_ANCHOR);
        AnchorPane.setTopAnchor(pauseButton, ZERO_ANCHOR);
        
        halfSpeedButton = new Button("|>");
        halfSpeedButton.setMinWidth(BUTTON_WIDTH);
        halfSpeedButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            UniverseManager.getInstance().setEnginePeriod(2);
            updateText();
        });
        super.getChildren().add(halfSpeedButton);
        AnchorPane.setLeftAnchor(halfSpeedButton, BUTTON_WIDTH);
        AnchorPane.setBottomAnchor(halfSpeedButton, ZERO_ANCHOR);
        
        normalSpeedButton = new Button(">");
        normalSpeedButton.setMinWidth(BUTTON_WIDTH);
        normalSpeedButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            UniverseManager.getInstance().setEnginePeriod(1);
            updateText();
        });
        super.getChildren().add(normalSpeedButton);
        AnchorPane.setLeftAnchor(normalSpeedButton, BUTTON_WIDTH * 2);
        AnchorPane.setBottomAnchor(normalSpeedButton, ZERO_ANCHOR);
        
        doubleSpeedButton = new Button(">>");
        doubleSpeedButton.setMinWidth(50);
        doubleSpeedButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            UniverseManager.getInstance().setEnginePeriod(0.5d);
            updateText();
        });
        super.getChildren().add(doubleSpeedButton);
        AnchorPane.setLeftAnchor(doubleSpeedButton, BUTTON_WIDTH * 3);
        AnchorPane.setBottomAnchor(doubleSpeedButton, ZERO_ANCHOR);
        
        updateText();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private void updateText() {
        double engineDuration = UniverseManager.getInstance().getEnginePeriod();
        boolean isPaused = UniverseManager.getInstance().isEnginePaused();
                
        if (isPaused) {
            engineSpeedText.setText("P");
        } else {
            if (engineDuration == 0.5d) {
                engineSpeedText.setText("x2");
            } else if (engineDuration == 2d) {
                engineSpeedText.setText("x0.5");
            } else {
                engineSpeedText.setText("x1");
            }
        }
    }

}
