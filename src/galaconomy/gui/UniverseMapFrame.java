package galaconomy.gui;

import galaconomy.gui.pane.BasicDisplayPane;
import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.map.Star;
import galaconomy.universe.traffic.Route;
import galaconomy.utils.DisplayUtils;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class UniverseMapFrame extends AnchorPane implements IEngineSubscriber {
    
    public List<Circle> activeStars = new ArrayList<>();
    public List<Line> activeRoutes = new ArrayList<>();
    public List<Circle> activeShips = new ArrayList<>();
    
    public final BasicDisplayPane infoPaneReference;
    
    public boolean active = true;
    
    public UniverseMapFrame(int width, int height, BasicDisplayPane infoPane) {
        super.setMinWidth(width);
        super.setMinHeight(height);
        
        this.infoPaneReference = infoPane;
        
        Image universe = new Image(getClass().getResourceAsStream(Constants.FOLDER_IMG + "universe.png"));
        BackgroundImage bgImage = new BackgroundImage(universe, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        super.setBackground(new Background(bgImage));  
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void paintUniverseMap(List<Star> stars) {
        this.getChildren().removeAll(activeStars);
        activeStars.clear();
                
        for (Star system : stars) {
            Circle star = new Circle(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER);
            star.setCenterX(DisplayUtils.fitCoordIntoDisplay(system.getX()));
            star.setCenterY(DisplayUtils.fitCoordIntoDisplay(system.getY()));
            star.setFill(system.getFXColor());
            
            star.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                infoPaneReference.setElementToDisplay(system);
            });
            
            this.getChildren().add(star);
            activeStars.add(star);
        }
    }
    
    public void paintShipRoutes(List<Route> routes) {
        this.getChildren().removeAll(activeRoutes);
        activeRoutes.clear();
        this.getChildren().removeAll(activeShips);
        activeShips.clear();
        
        for (Route route : routes) {
            Line routeLine = new Line();
            routeLine.getStyleClass().add("ship-route");
            
            Star departure = route.getDeparture();
            Star arrival = route.getArrival();
            double total = route.getDistanceTotal();
            double elapsed = route.getDistanceElapsed();
            double distance = elapsed / total;
            
            if (elapsed > 0) {
                int vectorX = arrival.getX() - departure.getX();
                int vectorY = arrival.getY() - departure.getY();
                double newX = departure.getX() +  distance * vectorX;
                routeLine.setStartX(DisplayUtils.fitCoordIntoDisplay(newX));
                double newY = departure.getY() +  distance * vectorY;
                routeLine.setStartY(DisplayUtils.fitCoordIntoDisplay(newY));
            } else {
                routeLine.setStartX(DisplayUtils.fitCoordIntoDisplay(departure.getX()));
                routeLine.setStartY(DisplayUtils.fitCoordIntoDisplay(departure.getY()));
            }
            
            routeLine.setEndX(DisplayUtils.fitCoordIntoDisplay(arrival.getX()));
            routeLine.setEndY(DisplayUtils.fitCoordIntoDisplay(arrival.getY()));
            
            routeLine.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                infoPaneReference.setElementToDisplay(route);
            });
            
            this.getChildren().add(routeLine);
            activeRoutes.add(routeLine);
            
            Circle ship = new Circle(5);
            ship.setCenterX(routeLine.getStartX());
            ship.setCenterY(routeLine.getStartY());
            ship.setFill(Color.DARKMAGENTA);
            
            ship.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                infoPaneReference.setElementToDisplay(route);
            });
            
            this.getChildren().add(ship);
            activeShips.add(ship);
            
            routeLine.toBack();
        }
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        paintShipRoutes(UniverseManager.getInstance().getRoutes());
    }
}