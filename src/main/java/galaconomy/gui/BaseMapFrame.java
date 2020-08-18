package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.pane.*;
import galaconomy.universe.*;
import galaconomy.universe.building.Building;
import galaconomy.universe.map.*;
import galaconomy.utils.DisplayUtils;
import java.util.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class BaseMapFrame extends AnchorPane implements IEngineSubscriber {
    
    public List<ImageView> baseTiles = new ArrayList<>();
    public List<ImageView> baseBuildings = new ArrayList<>();
    
    public Base currentBase = null;
    public boolean active = true;
    
    private static BaseMapFrame INSTANCE;
    
    public static BaseMapFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BaseMapFrame();
        }
        return INSTANCE;
    }
    
    private BaseMapFrame() {
        super.setMinWidth(Constants.SCREEN_X);
        super.setMinHeight(Constants.SCREEN_Y);
        
        // TODO base-specific backgrounds
        Image universe = new Image(getClass().getResourceAsStream(Constants.FOLDER_IMG + "base.jpg"));
        BackgroundImage bgImage = new BackgroundImage(universe, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        super.setBackground(new Background(bgImage));  
    }
    
    public void paintBaseMap() {
        paintBaseMap(currentBase);
    }
    
    public void paintBaseMap(Base base) {
        this.getChildren().removeAll(baseTiles);
        baseTiles.clear();
        this.getChildren().removeAll(baseBuildings);
        baseBuildings.clear();
        
        currentBase = base;
        
        for (SurfaceTile[] tileRow : base.getSurface()) {
            for (SurfaceTile tile : tileRow) {
                // TODO universal method for creating imageviews
                ImageView object = new ImageView();
                object.setPreserveRatio(false);
                object.setSmooth(true);
                object.setCache(true);

                Image objectImg = new Image(getClass().getResourceAsStream(tile.getImage()));
                object.setImage(objectImg);
                
                object.setFitWidth(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 3);
                object.setFitHeight(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 3);
                object.setX(DisplayUtils.fitCoordIntoDisplay(tile.getX() * 3));
                object.setY(DisplayUtils.fitCoordIntoDisplay(tile.getY() * 3));
                
                object.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                    setElementToDisplay(tile);
                });

                baseTiles.add(object);
                this.getChildren().add(object);
                
                Building building = tile.getBuilding();
                if (building != null) {
                    ImageView buildingObj = new ImageView();
                    buildingObj.setPreserveRatio(false);
                    buildingObj.setSmooth(true);
                    buildingObj.setCache(true);

                    Image buildingImg = new Image(getClass().getResourceAsStream(building.getImage()));
                    buildingObj.setImage(buildingImg);

                    buildingObj.setFitWidth(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 3);
                    buildingObj.setFitHeight(DisplayUtils.DEFAULT_ZOOM_MULTIPLIER * 3);
                    buildingObj.setX(DisplayUtils.fitCoordIntoDisplay(tile.getX() * 3));
                    buildingObj.setY(DisplayUtils.fitCoordIntoDisplay(tile.getY() * 3));

                    buildingObj.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                        setElementToDisplay(building);
                    });
                    
                    buildingObj.toFront();

                    baseBuildings.add(buildingObj);
                    this.getChildren().add(buildingObj);
                }
            }
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
    
    @Override
    public void engineTaskFinished(long stellarTime) {
        // TODO update base map
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    // TODO make universal for all map frames
    private void setElementToDisplay(IDisplayable object) {
        DisplayPane.getInstance().setElementToDisplay(object);
        SwitchDisplayPane.getInstance().setElementToDisplay(object);
    }
}
