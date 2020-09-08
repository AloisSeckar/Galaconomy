package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.pane.DisplayPane;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.*;
import galaconomy.utils.*;
import java.util.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
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
        super.setMinWidth(DisplayUtils.getMAIN_PANEL_X());
        super.setMinHeight(DisplayUtils.getMAIN_PANEL_Y());
        
        Image universe = new Image(getClass().getResourceAsStream(Constants.FOLDER_IMG + "universe.jpg"));
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
        
        gates.forEach((gate) -> {
            Line riftLine = new Line();
            riftLine.getStyleClass().add("rift-route");

            Star point1 = gate.getPoint1();
            riftLine.setStartX(DisplayUtils.fitCoordIntoDisplay(point1.getX()));
            riftLine.setStartY(DisplayUtils.fitCoordIntoDisplay(point1.getY()));

            Star point2 = gate.getPoint2();
            riftLine.setEndX(DisplayUtils.fitCoordIntoDisplay(point2.getX()));
            riftLine.setEndY(DisplayUtils.fitCoordIntoDisplay(point2.getY()));

            riftLine.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                DisplayPane.getInstance().setElementToDisplay(gate);
            });

            this.getChildren().add(riftLine);
            activeConnections.add(riftLine);
        });
        
        stars.forEach((system) -> {
            Circle star = new Circle(DisplayUtils.DEFAULT_TILE_SIZE);
            star.setCenterX(DisplayUtils.fitCoordIntoDisplay(system.getX()));
            star.setCenterY(DisplayUtils.fitCoordIntoDisplay(system.getY()));
            star.setFill(system.getFXColor());
            
            star.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                DisplayPane.getInstance().setElementToDisplay(system);
                
            });
            
            this.getChildren().add(star);
            activeStars.add(star);
        });
    }
    
    public void paintShipTravels(List<Travel> travels) {
        this.getChildren().removeAll(activeTravels);
        activeTravels.clear();
        this.getChildren().removeAll(activeShips);
        activeShips.clear();
        
        travels.forEach((travel) -> {
            travel.getRoutes().stream().filter(route -> isActiveRiftRoute(route)).forEach((route) -> {
                
                Line routeLine = GraphicUtils.getRouteLine(travel, route, false);
                this.getChildren().add(routeLine);
                activeTravels.add(routeLine);

                Circle ship = GraphicUtils.getShipCircle(travel, routeLine, false);
                this.getChildren().add(ship);
                activeShips.add(ship);
                
                routeLine.toBack();
            });
        });
    }

    @Override
    public void engineTaskFinished(long stellarTime) {
        paintShipTravels(UniverseManager.getInstance().getTravels());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    private boolean isActiveRiftRoute(Route route) {
        return route.isRiftDrive() && route.getStatus() == TravelStatus.ONGOING;
    }
}
