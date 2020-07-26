package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.*;
import galaconomy.utils.DisplayUtils;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class UniverseMapFrame extends AnchorPane implements IEngineSubscriber {
    
    public List<Circle> activeStars = new ArrayList<>();
    public List<Line> activeConnections = new ArrayList<>();
    
    public List<Circle> activeShips = new ArrayList<>();
    public List<Line> activeTravels = new ArrayList<>();
    
    public boolean active = true;
    
    private static UniverseMapFrame INSTANCE;
    
    public static UniverseMapFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniverseMapFrame();
        }
        return INSTANCE;
    }
    
    private UniverseMapFrame() {
        super.setMinWidth(Constants.SCREEN_X);
        super.setMinHeight(Constants.SCREEN_Y);
        
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
    
    public void paintUniverseMap(List<Star> stars, List<Connector> gates) {
        this.getChildren().removeAll(activeStars);
        activeStars.clear();
        this.getChildren().removeAll(activeConnections);
        activeConnections.clear();
        
        // first draw rift gates connectors
        for (Connector gate : gates) {
            Line riftLine = new Line();
            riftLine.getStyleClass().add("rift-route");

            Star point1 = gate.getPoint1();
            riftLine.setStartX(DisplayUtils.fitCoordIntoDisplay(point1.getX()));
            riftLine.setStartY(DisplayUtils.fitCoordIntoDisplay(point1.getY()));

            Star point2 = gate.getPoint2();
            riftLine.setEndX(DisplayUtils.fitCoordIntoDisplay(point2.getX()));
            riftLine.setEndY(DisplayUtils.fitCoordIntoDisplay(point2.getY()));

            riftLine.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                setElementToDisplay(gate);
            });

            this.getChildren().add(riftLine);
            activeConnections.add(riftLine);
        }
        
        // then overlay lines with star images
        for (Star system : stars) {
            Circle star = new Circle(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER);
            star.setCenterX(DisplayUtils.fitCoordIntoDisplay(system.getX()));
            star.setCenterY(DisplayUtils.fitCoordIntoDisplay(system.getY()));
            star.setFill(system.getFXColor());
            
            star.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                setElementToDisplay(system);
                
            });
            
            this.getChildren().add(star);
            activeStars.add(star);
        }
    }
    
    public void paintShipTravels(List<Travel> travels) {
        this.getChildren().removeAll(activeTravels);
        activeTravels.clear();
        this.getChildren().removeAll(activeShips);
        activeShips.clear();
        
        for (Travel travel : travels) {
            for (Route route : travel.getRoutes()) {
                Line routeLine = new Line();

                Star departure = route.getDeparture();
                Star arrival = route.getArrival();
                double total = route.getDistanceTotal();
                double elapsed = route.getDistanceElapsed();
                double distance = elapsed / total;
                
                TravelStatus status = route.getStatus();
                switch (status) {
                    case ONGOING:
                        routeLine.getStyleClass().add("ship-route-ongoing");
                        break;
                    case FINISHED:
                        routeLine.getStyleClass().add("ship-route-finished");
                        break;
                    default:
                        routeLine.getStyleClass().add("ship-route");
                }
                
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
                    setElementToDisplay(travel);
                });

                this.getChildren().add(routeLine);
                activeTravels.add(routeLine);

                if (status == TravelStatus.ONGOING) {
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

                // TODO this causes routes being hidden behind rift connectors
                routeLine.toBack();
            }
        }
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        paintShipTravels(UniverseManager.getInstance().getTravels());
    }
    
    ////////////////////////////////////////////////////////////////////////////

    private void setElementToDisplay(IDisplayable object) {
        DisplayPane.getInstance().setElementToDisplay(object);
        SwitchDisplayPane.getInstance().setElementToDisplay(object);
    }
}
