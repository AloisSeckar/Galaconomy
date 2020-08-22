package galaconomy.gui.pane;

import galaconomy.gui.BaseMapFrame;
import galaconomy.universe.*;
import galaconomy.universe.building.*;
import static galaconomy.universe.building.Building.*;
import galaconomy.universe.economy.TradeHelper;
import galaconomy.universe.map.*;
import galaconomy.universe.player.Player;
import galaconomy.utils.InfoUtils;
import galaconomy.utils.result.ResultBean;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BuildingPane extends AnchorPane {
    
    private static final int SIZE = 28;
    
    private final Button buildFactory;
    private final Button buildGenerator;
    private final Button buildWarehouse;
    private final Button buildMine;
    private final Button buildFarm;
    
    private static BuildingPane INSTANCE;

    public static BuildingPane getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BuildingPane();
        }
        return INSTANCE;
    }
    
    private BuildingPane() {

        buildFactory = new Button();
        buildFactory.setGraphic(getButtonImage(Building.IMG_FACTORY));
        buildFactory.setTooltip(new Tooltip("Build a new factory"));
        buildFactory.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            build(FACTORY);
        });
        
        super.getChildren().add(buildFactory);
        AnchorPane.setLeftAnchor(buildFactory, 5d);
        AnchorPane.setTopAnchor(buildFactory, 5d);
        
        buildGenerator = new Button();
        buildGenerator.setGraphic(getButtonImage(Building.IMG_GENERATOR));
        buildGenerator.setTooltip(new Tooltip("Build a new generator"));
        buildGenerator.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            build(GENERATOR);
        });
        
        super.getChildren().add(buildGenerator);
        AnchorPane.setLeftAnchor(buildGenerator, 5d);
        AnchorPane.setTopAnchor(buildGenerator, 5d + SIZE + 15);

        buildWarehouse = new Button();
        buildWarehouse.setGraphic(getButtonImage(Building.IMG_WAREHOUSE));
        buildWarehouse.setTooltip(new Tooltip("Build a new warehouse"));
        buildWarehouse.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            build(WAREHOUSE);
        });
        
        super.getChildren().add(buildWarehouse);
        AnchorPane.setLeftAnchor(buildWarehouse, 5d);
        AnchorPane.setTopAnchor(buildWarehouse, 10d + 2 * SIZE + 30);
        
        buildMine = new Button();
        buildMine.setGraphic(getButtonImage(Building.IMG_MINE));
        buildMine.setTooltip(new Tooltip("Build a new mine"));
        buildMine.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            build(MINE);
        });
        
        super.getChildren().add(buildMine);
        AnchorPane.setLeftAnchor(buildMine, 10d + SIZE + 20);
        AnchorPane.setTopAnchor(buildMine, 5d);
        
        buildFarm = new Button();
        buildFarm.setGraphic(getButtonImage(Building.IMG_FARM));
        buildFarm.setTooltip(new Tooltip("Build a new farm"));
        buildFarm.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            build(FARM);
        });
        
        super.getChildren().add(buildFarm);
        AnchorPane.setLeftAnchor(buildFarm, 10d + SIZE + 20);
        AnchorPane.setTopAnchor(buildFarm, 5d + SIZE + 15);
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private ImageView getButtonImage(String imgSrc) {
        Image generatorImage = new Image(getClass().getResourceAsStream(imgSrc));
        ImageView imageView = new ImageView(generatorImage);
        
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);
        imageView.setCache(true);
        
        imageView.setFitWidth(SIZE);
        imageView.setFitHeight(SIZE);
        
        return imageView;
    }
    
    private void build(String buildingType) {
        IDisplayable selectedItem = DisplayPane.getInstance().getElementToDisplay();
        if (selectedItem instanceof SurfaceTile) {
            SurfaceTile tile = (SurfaceTile) selectedItem;
            if (tile.getBuilding() == null) {
                Building newBuilding = GLSFactory.deliverBuilding(buildingType);
                Player player = UniverseManager.getInstance().getPlayer();
                ResultBean tradeResult = TradeHelper.tradeAsset(newBuilding, player);
                if (tradeResult.isSuccess()) {
                    newBuilding.setParent((Base) tile.getParent());
                    tile.setBuilding(newBuilding);
                    BaseMapFrame.getInstance().paintBaseMap();
                    DisplayPane.getInstance().setElementToDisplay(newBuilding);
                    PlayerPane.getInstance().updatePlayerCredits();
                    InfoUtils.showMessage("Building purchased!");
                } else {
                    InfoUtils.showMessage(tradeResult.getMessage());
                }
            } else {
                InfoUtils.showMessage("Tile is occupied by another building!");
            }
        }
    }
}
