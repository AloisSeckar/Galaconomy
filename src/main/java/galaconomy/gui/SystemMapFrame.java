package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.Route;
import galaconomy.universe.traffic.Travel;
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
    
    public Star currentSystem = null;
    public boolean active = true;
    
    private static SystemMapFrame INSTANCE;
    
    public static SystemMapFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SystemMapFrame();
        }
        return INSTANCE;
    }
    
    private SystemMapFrame() {
        super.setMinWidth(DisplayUtils.getMAIN_PANEL_X());
        super.setMinHeight(DisplayUtils.getMAIN_PANEL_Y());
        
        // TODO system-specific backgrounds
        Image universe = new Image(getClass().getResourceAsStream(Constants.FOLDER_IMG + "universe.jpg"));
        BackgroundImage bgImage = new BackgroundImage(universe, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        super.setBackground(new Background(bgImage));  
    }
    
    public void paintSystemMap(Star starSystem) {
        this.getChildren().removeAll(systemObjects);
        systemObjects.clear();
        
        currentSystem = starSystem;
        
        if (starSystem != null) {
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
            
            paintShipTravels(UniverseManager.getInstance().getTravels());
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    public void paintShipTravels(List<Travel> travels) {
        this.getChildren().removeAll(activeRoutes);
        activeRoutes.clear();
        this.getChildren().removeAll(activeShips);
        activeShips.clear();
        
        if (travels != null) {
            travels.forEach((travel) -> {
                Route route = travel.getActiveRoute();
                if (route != null && !route.isRiftDrive()) {
                    Star system = route.getSystem();
                    if (system != null && system.equals(currentSystem)) {

                        Line routeLine = new Line();
                        routeLine.getStyleClass().add("ship-route");

                        AbstractMapElement departure = (AbstractMapElement) route.getDeparture();
                        AbstractMapElement arrival = (AbstractMapElement) route.getArrival();

                        double total = route.getDistanceTotal();
                        double elapsed = route.getDistanceElapsed();
                        double distance = elapsed / total;

                        int offset = DisplayUtils.DEFAULT_TILE_SIZE * 2;
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
                            setElementToDisplay(route);
                        });

                        this.getChildren().add(routeLine);
                        activeRoutes.add(routeLine);

                        Circle ship = new Circle(5);
                        ship.setCenterX(routeLine.getStartX());
                        ship.setCenterY(routeLine.getStartY());
                        ship.setFill(Color.DARKMAGENTA);

                        ship.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                            setElementToDisplay(travel);
                        });

                        this.getChildren().add(ship);
                        activeShips.add(ship);
                    }
                }
            });
        }
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        paintShipTravels(UniverseManager.getInstance().getTravels());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private void addObject(AbstractMapElement mapObject) {
        if (mapObject != null) {
            ImageView object;
            if (mapObject instanceof Star) {
                object = DisplayUtils.getImageView(mapObject.getImage(), DisplayUtils.DEFAULT_TILE_SIZE * 10);
                object.setX(DisplayUtils.fitCoordIntoDisplay(DisplayUtils.getMAX_X() / 2 + 1) - DisplayUtils.DEFAULT_TILE_SIZE * 5);
                object.setY(DisplayUtils.fitCoordIntoDisplay(DisplayUtils.getMAX_Y() / 2 + 1) - DisplayUtils.DEFAULT_TILE_SIZE * 5);
            } else {
                object = DisplayUtils.getImageView(mapObject.getImage(), DisplayUtils.DEFAULT_TILE_SIZE * 4);
                object.setX(DisplayUtils.fitCoordIntoDisplay(mapObject.getX()));
                object.setY(DisplayUtils.fitCoordIntoDisplay(mapObject.getY()));
            }

            object.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                setElementToDisplay(mapObject);
            });

            this.getChildren().add(object);
            systemObjects.add(object);
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////

    private void setElementToDisplay(IDisplayable object) {
        DisplayPane.getInstance().setElementToDisplay(object);
        SwitchDisplayPane.getInstance().setElementToDisplay(object);
    }
    
    private boolean isElementPresent(AbstractMapElement element) {
        boolean ret = false;
        
        if (currentSystem != null && element != null) {
            if (element instanceof RiftGate) {
                for (RiftGate gate : currentSystem.getRiftGates()) {
                    if (element.equals(gate)) {
                        ret = true;
                        break;
                    }
                }
            } else {
                for (StellarObject object : currentSystem.getStellarObjects()) {
                    if (element.equals(object)) {
                        ret = true;
                        break;
                    }
                }
            }
        }
        
        return ret;
    }
}
