package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.pane.DisplayPane;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.*;
import galaconomy.utils.*;
import java.util.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

public class SystemMapFrame extends AnchorPane implements IEngineSubscriber {
    
    public List<ImageView> systemObjects = new ArrayList<>();
    public List<Line> activeRoutes = new ArrayList<>();
    public List<Circle> activeShips = new ArrayList<>();
    
    public Star currentSystem = null;
    public boolean active = false;
    
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
        
        IDisplayable selectedItem = DisplayPane.getInstance().getElementToDisplay();
        
        if (travels != null) {
            travels.forEach((travel) -> {
                Route route = travel.getActiveRoute();
                if (route != null && !route.isRiftDrive()) {
                    Star system = route.getSystem();
                    if (system != null && system.equals(currentSystem)) {

                        Line routeLine = GraphicUtils.getRouteLine(travel, route, true);
                        this.getChildren().add(routeLine);
                        activeRoutes.add(routeLine);

                        Circle ship = GraphicUtils.getShipCircle(travel, routeLine, selectedItem);
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
                object = GraphicUtils.getImageView(mapObject.getImage(), DisplayUtils.DEFAULT_TILE_SIZE * 10);
                object.setX(DisplayUtils.fitCoordIntoDisplay(DisplayUtils.getMAX_X() / 2 + 1) - DisplayUtils.DEFAULT_TILE_SIZE * 5);
                object.setY(DisplayUtils.fitCoordIntoDisplay(DisplayUtils.getMAX_Y() / 2 + 1) - DisplayUtils.DEFAULT_TILE_SIZE * 5);
            } else {
                object = GraphicUtils.getImageView(mapObject.getImage(), DisplayUtils.DEFAULT_TILE_SIZE * 4);
                object.setX(DisplayUtils.fitCoordIntoDisplay(mapObject.getX()));
                object.setY(DisplayUtils.fitCoordIntoDisplay(mapObject.getY()));
            }

            object.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                DisplayPane.getInstance().setElementToDisplay(mapObject);
            });

            this.getChildren().add(object);
            systemObjects.add(object);
        }
    }
}
