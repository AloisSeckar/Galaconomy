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
    public boolean active = false;
    
    private static BaseMapFrame INSTANCE;
    
    public static BaseMapFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BaseMapFrame();
        }
        return INSTANCE;
    }
    
    private BaseMapFrame() {
        super.setMinWidth(DisplayUtils.getMAIN_PANEL_X());
        super.setMinHeight(DisplayUtils.getMAIN_PANEL_Y());
        
        // TODO base-specific backgrounds
        Image universe = new Image(getClass().getResourceAsStream(Constants.FOLDER_IMG + "base.jpg"));
        BackgroundImage bgImage = new BackgroundImage(universe, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
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
        
        if (base != null) {
            for (SurfaceTile[] tileRow : base.getSurface()) {
                for (SurfaceTile tile : tileRow) {
                    ImageView object = DisplayUtils.getImageView(tile.getImage(), DisplayUtils.DEFAULT_TILE_SIZE * DisplayUtils.BASE_TILE_SIZE);
                    object.setX(DisplayUtils.fitCoordIntoDisplay(tile.getX() * DisplayUtils.BASE_TILE_SIZE));
                    object.setY(DisplayUtils.fitCoordIntoDisplay(tile.getY() * DisplayUtils.BASE_TILE_SIZE));

                    object.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent me) -> {
                        setElementToDisplay(tile);
                    });

                    baseTiles.add(object);
                    this.getChildren().add(object);

                    Building building = tile.getBuilding();
                    if (building != null) {
                        ImageView buildingObj = DisplayUtils.getImageView(building.getImage(), DisplayUtils.DEFAULT_TILE_SIZE * DisplayUtils.BASE_TILE_SIZE);                   
                        buildingObj.setX(DisplayUtils.fitCoordIntoDisplay(tile.getX() * DisplayUtils.BASE_TILE_SIZE));
                        buildingObj.setY(DisplayUtils.fitCoordIntoDisplay(tile.getY() * DisplayUtils.BASE_TILE_SIZE));

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
