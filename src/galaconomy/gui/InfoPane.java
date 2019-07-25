package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class InfoPane extends AnchorPane implements IEngineSubscriber {
    
    private final Label nameText;
    private final TextArea dscrText;
    private final ImageView imgView;
    private final Label stellarTimeText;
    
    private final EngineSpeedPane engineSpeed;
    
    private IDisplayable elementToDisplay;
    
    public InfoPane(int width) {
        super.setMinWidth(width + 20);
        super.setMaxWidth(width + 20);
        super.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
        super.getStyleClass().add("pane-info");
        
        nameText = new Label();
        nameText.setMaxWidth(width);
        nameText.getStyleClass().add("pane-info-name");
        super.getChildren().add(nameText);
        AnchorPane.setLeftAnchor(nameText, 5d);
        AnchorPane.setTopAnchor(nameText, 5d);
        
        dscrText = new TextArea();
        dscrText.setMaxWidth(width);
        dscrText.setPrefRowCount(7);
        dscrText.setWrapText(true);
        dscrText.setEditable(false);
        dscrText.getStyleClass().add("pane-info-dscr");
        super.getChildren().add(dscrText);
        AnchorPane.setLeftAnchor(dscrText, 5d);
        AnchorPane.setTopAnchor(dscrText, 35d);
        
        imgView = new ImageView();
        imgView.setFitWidth(width);
        imgView.setFitHeight(width);
        imgView.getStyleClass().add("pane-info-img");
        super.getChildren().add(imgView);
        AnchorPane.setLeftAnchor(imgView, 5d);
        AnchorPane.setTopAnchor(imgView, 205d);
        
        stellarTimeText = new Label();
        stellarTimeText.setMaxWidth(width);
        stellarTimeText.getStyleClass().add("pane-info-name");
        super.getChildren().add(stellarTimeText);
        AnchorPane.setLeftAnchor(stellarTimeText, 5d);
        AnchorPane.setBottomAnchor(stellarTimeText, 10d);
        
        engineSpeed = new EngineSpeedPane();
        super.getChildren().add(engineSpeed);
        AnchorPane.setLeftAnchor(engineSpeed, 5d);
        AnchorPane.setBottomAnchor(engineSpeed, 45d);
        
        setElementToDisplay(new VoidElement());
    }

    public final void setElementToDisplay(IDisplayable elementToDisplay) {
        this.elementToDisplay = elementToDisplay;
        reloadInfoPanel();
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        stellarTimeText.setText(String.valueOf(stellarTime));
        reloadInfoPanel();
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private void reloadInfoPanel() {
        if (elementToDisplay != null) {
            nameText.setText(elementToDisplay.displayName());
            dscrText.setText(elementToDisplay.displayDscr());

            Image defaultImg = new Image(getClass().getResourceAsStream(elementToDisplay.getImage()));
            imgView.setImage(defaultImg);
        } else {
            setElementToDisplay(new VoidElement());
        }
    }
    
    private class VoidElement implements IDisplayable {
        @Override
        public String displayName() {
            return "Galaconomy";
        }

        @Override
        public String displayDscr() {
            return "Click onto map element to see details";
        }

        @Override
        public String getImage() {
            return Constants.FOLDER_IMG + "galaconomy.png";
        }
    }
    
}
