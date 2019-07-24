package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.systems.StarSystem;
import galaconomy.universe.traffic.ShipRoute;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class MapPane extends AnchorPane implements IEngineSubscriber {
    
    public static int zoomMultiplier = 8;
    public static int mapBorderOffset = 8;
    
    public List<Circle> activeStars = new ArrayList<>();
    public List<Line> activeRoutes = new ArrayList<>();
    public List<Circle> activeShips = new ArrayList<>();
    
    public final InfoPane infoPaneReference;
    
    public MapPane(int width, int height, InfoPane infoPane) {
        super.setMinWidth(width);
        super.setMinHeight(height);
        
        this.infoPaneReference = infoPane;
        
        Image universe = new Image(getClass().getResourceAsStream(Constants.FOLDER_IMG + "universe.png"));
        BackgroundImage bgImage = new BackgroundImage(universe, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        super.setBackground(new Background(bgImage));  
    }
    
    public void paintUniverseMap(List<StarSystem> stars) {
        this.getChildren().removeAll(activeStars);
        activeStars.clear();
                
        for (StarSystem system : stars) {
            Circle star = new Circle(zoomMultiplier);
            star.setCenterX(fitCoordIntoDisplay(system.getX()));
            star.setCenterY(fitCoordIntoDisplay(system.getY()));
            star.setFill(system.getFXColor());
            
            star.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                infoPaneReference.loadSystemInfo(system);
            });
            
            this.getChildren().add(star);
            activeStars.add(star);
        }
    }
    
    public void paintShipRoutes(List<ShipRoute> routes) {
        this.getChildren().removeAll(activeRoutes);
        activeRoutes.clear();
        this.getChildren().removeAll(activeShips);
        activeShips.clear();
        
        for (ShipRoute route : routes) {
            Line routeLine = new Line();
            routeLine.getStyleClass().add("ship-route");
            
            StarSystem departure = route.getDeparture();
            StarSystem arrival = route.getArrival();
            double total = route.getDistanceTotal();
            double elapsed = route.getDistanceElapsed();
            double distance = elapsed / total;
            
            if (elapsed > 0) {
                int vectorX = arrival.getX() - departure.getX();
                int vectorY = arrival.getY() - departure.getY();
                double newX = departure.getX() +  distance * vectorX;
                routeLine.setStartX(fitCoordIntoDisplay(newX));
                double newY = departure.getY() +  distance * vectorY;
                routeLine.setStartY(fitCoordIntoDisplay(newY));
            } else {
                routeLine.setStartX(fitCoordIntoDisplay(departure.getX()));
                routeLine.setStartY(fitCoordIntoDisplay(departure.getY()));
            }
            
            routeLine.setEndX(fitCoordIntoDisplay(arrival.getX()));
            routeLine.setEndY(fitCoordIntoDisplay(arrival.getY()));
            
            routeLine.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                infoPaneReference.loadRouteInfo(route);
            });
            
            this.getChildren().add(routeLine);
            activeRoutes.add(routeLine);
            
            Circle ship = new Circle(5);
            ship.setCenterX(routeLine.getStartX());
            ship.setCenterY(routeLine.getStartY());
            ship.setFill(Color.DARKMAGENTA);
            
            ship.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                infoPaneReference.loadRouteInfo(route);
            });
            
            this.getChildren().add(ship);
            activeShips.add(ship);
            
            routeLine.toBack();
        }
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        paintShipRoutes(UniverseManager.getInstance().getShipRoutes());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private double fitCoordIntoDisplay(double coord) {
        return coord * zoomMultiplier + mapBorderOffset;
    }
}