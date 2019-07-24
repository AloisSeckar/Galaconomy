package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.universe.IEngineSubscriber;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.systems.Star;
import galaconomy.universe.traffic.*;
import galaconomy.utils.DisplayUtils;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class InfoPane extends AnchorPane implements IEngineSubscriber {
    
    private final Label nameText;
    private final TextArea dscrText;
    private final ImageView imgView;
    private final Label stellarTimeText;
    
    private final EngineSpeedPane engineSpeed;
    
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
        
        resetMapInfo();
    }
    
    public void loadSystemInfo(Star system) {
        if (system != null) {
            nameText.setText(system.getName());
            dscrText.setText(system.getDscr());
            
            Image systemImg = new Image(getClass().getResourceAsStream(system.getImg()));
            imgView.setImage(systemImg);     
        } else {
            resetMapInfo();
        }
    }

    public void loadRouteInfo(Route route) {
        if (route != null) {
            Ship ship = route.getShip();
            Star deparature = route.getDeparture();
            Star arrival = route.getArrival();
            
            StringBuilder routeDscr = new StringBuilder();
            routeDscr.append("Owner: ").append(ship.getOwners().get(ship.getOwners().size() - 1).getName()).append("\n");
            routeDscr.append("Speed: ").append(ship.getSpeed()).append("\n");
            routeDscr.append("Dep: ").append(deparature.getName()).append(DisplayUtils.getCoordinates(deparature.getX(), deparature.getY())).append("\n");
            routeDscr.append("Arr: ").append(arrival.getName()).append(DisplayUtils.getCoordinates(arrival.getX(), arrival.getY())).append("\n");
            routeDscr.append("Distance: ").append(String.format("%.2f", route.getDistanceTotal())).append("\n");
            routeDscr.append("Elapsed: ").append(String.format("%.2f", route.getDistanceElapsed())).append("\n");
            
            nameText.setText(ship.getName());
            dscrText.setText(routeDscr.toString());
            
            Image shipImg = new Image(getClass().getResourceAsStream(ship.getImg()));
            imgView.setImage(shipImg);     
        } else {
            resetMapInfo();
        }
    }

    private void resetMapInfo() {
        nameText.setText("Galaconomy");
        dscrText.setText("Click onto map element to see details");
        
        Image defaultImg = new Image(getClass().getResourceAsStream(Constants.FOLDER_IMG + "galaconomy.png"));
        imgView.setImage(defaultImg);
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        stellarTimeText.setText(String.valueOf(stellarTime));
    }
}
