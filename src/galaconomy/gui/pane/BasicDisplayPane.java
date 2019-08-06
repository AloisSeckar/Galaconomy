package galaconomy.gui.pane;

import galaconomy.gui.pane.BasicEngineSpeedPane;
import galaconomy.constants.Constants;
import galaconomy.universe.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BasicDisplayPane extends AnchorPane {
    
    private final Label nameText;
    private final TextArea dscrText;
    private final ImageView imgView;
    
    private IDisplayable elementToDisplay;
    
    public BasicDisplayPane(int width) {
        super.setMinWidth(width);
        super.setMaxWidth(width);
        super.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
        
        nameText = new Label();
        nameText.setMaxWidth(width);
        nameText.getStyleClass().add("pane-info-name");
        super.getChildren().add(nameText);
        
        imgView = new ImageView();
        imgView.setFitWidth(width);
        imgView.setFitHeight(width);
        imgView.getStyleClass().add("pane-info-img");
        super.getChildren().add(imgView);
        AnchorPane.setTopAnchor(imgView, 35d);
        
        dscrText = new TextArea();
        dscrText.setMaxWidth(width);
        dscrText.setMinHeight(250d);
        dscrText.setWrapText(true);
        dscrText.setEditable(false);
        dscrText.getStyleClass().add("pane-info-dscr");
        super.getChildren().add(dscrText);
        AnchorPane.setTopAnchor(dscrText, width + 50d);
        
        setElementToDisplay(new VoidElement());
    }

    public final void setElementToDisplay(IDisplayable elementToDisplay) {
        this.elementToDisplay = elementToDisplay;
        reloadInfoPanel();
    }
    
    public final void reloadInfoPanel() {
        if (elementToDisplay != null) {
            nameText.setText(elementToDisplay.displayName());
            dscrText.setText(elementToDisplay.displayDscr());

            Image defaultImg = new Image(getClass().getResourceAsStream(elementToDisplay.getImage()));
            imgView.setImage(defaultImg);
        } else {
            setElementToDisplay(new VoidElement());
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
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
