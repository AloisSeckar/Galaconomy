package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.Route;
import galaconomy.utils.DisplayUtils;
import java.util.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class SystemMapFrame extends AnchorPane implements IEngineSubscriber {
    
    public List<ImageView> systemObjects = new ArrayList<>();
    public List<Line> activeRoutes = new ArrayList<>();
    public List<Circle> activeShips = new ArrayList<>();
    
    public String currentSystem = null;
    public boolean active = true;
    
    private static SystemMapFrame INSTANCE;
    
    public static SystemMapFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SystemMapFrame();
        }
        return INSTANCE;
    }
    
    private SystemMapFrame() {
        super.setMinWidth(Constants.SCREEN_X);
        super.setMinHeight(Constants.SCREEN_Y);
        
        // TODO system-specific backgrounds
        Image universe = new Image(getClass().getResourceAsStream(Constants.FOLDER_IMG + "universe.png"));
        BackgroundImage bgImage = new BackgroundImage(universe, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        super.setBackground(new Background(bgImage));  
    }
    
    public void paintSystemMap(Star starSystem) {
        this.getChildren().removeAll(systemObjects);
        systemObjects.clear();
        
        currentSystem = starSystem.getName();
        
        addObject(starSystem);
                
        starSystem.getStellarObjects().forEach((stellarObject) -> {
            addObject(stellarObject);
        });  
        
        RiftPortal riftPortal = starSystem.getRiftPortal();
        if (riftPortal != null) {
            addObject(riftPortal);
        }
        
        starSystem.getRiftGates().forEach((riftGate) -> {
            addObject(riftGate);
        });
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void paintShipRoutes(List<Route> routes) {
        this.getChildren().removeAll(activeRoutes);
        activeRoutes.clear();
        this.getChildren().removeAll(activeShips);
        activeShips.clear();
        
        routes.forEach((route) -> {
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
                setElementToDisplay(route);
            });
            
            this.getChildren().add(routeLine);
            activeRoutes.add(routeLine);
            
            Circle ship = new Circle(5);
            ship.setCenterX(routeLine.getStartX());
            ship.setCenterY(routeLine.getStartY());
            ship.setFill(Color.DARKMAGENTA);
            
            ship.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                setElementToDisplay(route);
            });
            
            this.getChildren().add(ship);
            activeShips.add(ship);
            
            routeLine.toBack();
        });
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        // TODO inner-system travels
        // paintShipRoutes(UniverseManager.getInstance().getRoutes());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private void addObject(AbstractMapElement mapObject) {
        ImageView object = new ImageView();
        object.setPreserveRatio(false);
        object.setSmooth(true);
        object.setCache(true);

        Image objectImg = new Image(getClass().getResourceAsStream(mapObject.getImage()));
        object.setImage(objectImg);

        if (mapObject instanceof Star) {
            object.setFitWidth(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 10);
            object.setFitHeight(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 10);
            object.setX(DisplayUtils.fitCoordIntoDisplay(Constants.MAX_X / 2 + 1) - DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 5);
            object.setY(DisplayUtils.fitCoordIntoDisplay(Constants.MAX_Y / 2 + 1) - DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 5);
        } else { 
            object.setFitWidth(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 4);
            object.setFitHeight(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 4);
            object.setX(DisplayUtils.fitCoordIntoDisplay(mapObject.getX()));
            object.setY(DisplayUtils.fitCoordIntoDisplay(mapObject.getY()));
        }

        object.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
            setElementToDisplay(mapObject);
        });

        this.getChildren().add(object);
        systemObjects.add(object);
    }
    
    ////////////////////////////////////////////////////////////////////////////

    private void setElementToDisplay(IDisplayable object) {
        DisplayPane.getInstance().setElementToDisplay(object);
        SwitchDisplayPane.getInstance().setElementToDisplay(object);
    }
}
