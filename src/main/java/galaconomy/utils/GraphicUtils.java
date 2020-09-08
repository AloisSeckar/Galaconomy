package galaconomy.utils;

import galaconomy.gui.pane.DisplayPane;
import galaconomy.universe.map.AbstractMapElement;
import galaconomy.universe.traffic.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import org.slf4j.*;

public class GraphicUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(GraphicUtils.class);

    private GraphicUtils() {
    }
    
    public static ImageView getImageView(String source, int dimension) {
        return getImageView(source, dimension, dimension);
    }
    
    public static ImageView getImageView(String source, int width, int height) {
        ImageView object = new ImageView();
        object.setPreserveRatio(false);
        object.setSmooth(true);
        object.setCache(true);
        
        try {
            Image objectImg = new Image(DisplayUtils.class.getClass().getResourceAsStream(source));
            object.setImage(objectImg);
        } catch(Exception ex) {
            LOG.error("DisplayUtils.getImageView", ex);
        }
        
        object.setFitWidth(width);
        object.setFitHeight(height);
        
        return object;
    }
    
    public static Line getRouteLine(Travel travel, Route route, boolean useOffset) {
        Line routeLine = new Line();
        routeLine.getStyleClass().add("ship-route");

        AbstractMapElement departure = (AbstractMapElement) route.getDeparture();
        AbstractMapElement arrival = (AbstractMapElement) route.getArrival();

        double total = route.getDistanceTotal();
        double elapsed = route.getDistanceElapsed();
        double distance = elapsed / total;

        int offset = useOffset ? DisplayUtils.DEFAULT_TILE_SIZE * 2 : 0;
        if (elapsed > 0) {
            int vectorX = arrival.getX() - departure.getX();
            int vectorY = arrival.getY() - departure.getY();
            double newX = departure.getX() +  distance * vectorX;
            routeLine.setStartX(DisplayUtils.fitCoordIntoDisplay(newX) + offset);
            double newY = departure.getY() +  distance * vectorY;
            routeLine.setStartY(DisplayUtils.fitCoordIntoDisplay(newY) + offset);
        } else {
            routeLine.setStartX(DisplayUtils.fitCoordIntoDisplay(departure.getX()) + offset);
            routeLine.setStartY(DisplayUtils.fitCoordIntoDisplay(departure.getY()) +offset);
        }

        routeLine.setEndX(DisplayUtils.fitCoordIntoDisplay(arrival.getX()) + offset);
        routeLine.setEndY(DisplayUtils.fitCoordIntoDisplay(arrival.getY()) + offset);

        routeLine.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            DisplayPane.getInstance().setElementToDisplay(travel);
        });
        
        return routeLine;
    }
    
    public static Circle getShipCircle(Travel travel, Line routeLine, boolean active) {
        Circle ship = new Circle();
        
        ship.setRadius(active ? 10 : 5);
        ship.setCenterX(routeLine.getStartX());
        ship.setCenterY(routeLine.getStartY());
        ship.setFill(active ? Color.MAGENTA : Color.DARKMAGENTA);

        ship.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            DisplayPane.getInstance().setElementToDisplay(travel.getShip());
        });
        
        return ship;
    }
    
}
