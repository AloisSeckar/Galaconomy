package galaconomy.gui.pane;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DisplayPane extends AnchorPane {
    
    private static final int WIDTH = Constants.SIDE_PANEL_X;
    
    private final Label nameText;
    private final TextArea dscrText;
    private final ImageView imgView;
    
    private IDisplayable elementToDisplay;
    
    private static DisplayPane INSTANCE;
    
    public static DisplayPane getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DisplayPane();
        }
        return INSTANCE;
    }
    
    private DisplayPane() {
        super.setMinWidth(WIDTH);
        super.setMaxWidth(WIDTH);
        super.setBackground(new Background(new BackgroundFill(Color.BURLYWOOD, CornerRadii.EMPTY, Insets.EMPTY)));
        
        nameText = new Label();
        nameText.setMaxWidth(WIDTH);
        nameText.getStyleClass().add("pane-info-name");
        super.getChildren().add(nameText);
        
        imgView = new ImageView();
        imgView.setFitWidth(WIDTH);
        imgView.setFitHeight(WIDTH);
        imgView.getStyleClass().add("pane-info-img");
        super.getChildren().add(imgView);
        AnchorPane.setTopAnchor(imgView, 35d);
        
        dscrText = new TextArea();
        dscrText.setMaxWidth(WIDTH);
        dscrText.setMinHeight(300d);
        dscrText.setWrapText(true);
        dscrText.setEditable(false);
        dscrText.getStyleClass().add("pane-info-dscr");
        super.getChildren().add(dscrText);
        AnchorPane.setTopAnchor(dscrText, WIDTH + 50d);
        
        setElementToDisplay(new VoidElement());
    }

    public IDisplayable getElementToDisplay() {
        return elementToDisplay;
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
