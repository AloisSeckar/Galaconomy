package galaconomy.gui.pane;

import galaconomy.gui.pane.action.*;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.Ship;
import galaconomy.utils.DisplayUtils;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class DisplayPane extends AnchorPane {
    
    private static final int WIDTH = DisplayUtils.SIDE_PANEL_X;
    
    private final Label nameText;
    private final TextArea dscrText;
    private final ImageView imgView;
    private ActionButtonsPane actionButtons;
    
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
        reloadActionButtons();
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

    private void reloadActionButtons() {
        if (actionButtons != null) {
            this.getChildren().remove(actionButtons);
        }
        
        if (elementToDisplay instanceof Star) {
            actionButtons = new StarActionButtonsPane();
        } else if (elementToDisplay instanceof Base) {
            actionButtons = new BaseActionButtonsPane();
        } else if (elementToDisplay instanceof Ship) {
            actionButtons = new ShipActionButtonsPane();
        } else {
            actionButtons = new PlainActionButtonsPane();
        }
        
        actionButtons.setDisplayedItem(elementToDisplay);
        
        this.getChildren().add(actionButtons);
        AnchorPane.setTopAnchor(actionButtons, WIDTH + 360d);
    }
    
}
