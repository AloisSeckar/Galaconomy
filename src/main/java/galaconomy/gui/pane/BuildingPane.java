package galaconomy.gui.pane;

import galaconomy.gui.BaseMapFrame;
import galaconomy.universe.*;
import galaconomy.universe.building.*;
import galaconomy.universe.map.*;
import galaconomy.universe.player.Player;
import galaconomy.utils.InfoUtils;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class BuildingPane extends AnchorPane {
    
    private static final int SIZE = 28;
    
    private final Button buildFactory;
    private final Button buildGenerator;
    private final Button buildWarehouse;
    
    private static BuildingPane INSTANCE;

    public static BuildingPane getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BuildingPane();
        }
        return INSTANCE;
    }
    
    private BuildingPane() {
        
        Image factoryImage = new Image(getClass().getResourceAsStream(Building.IMG_FACTORY));
        ImageView factoryImageView = new ImageView(factoryImage);
        factoryImageView.setFitWidth(SIZE);
        factoryImageView.setFitHeight(SIZE);

        buildFactory = new Button();
        buildFactory.setGraphic(factoryImageView);
        buildFactory.setTooltip(new Tooltip("Build a new factory"));
        buildFactory.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            build(new Factory(null));
        });
        
        super.getChildren().add(buildFactory);
        AnchorPane.setLeftAnchor(buildFactory, 5d);
        AnchorPane.setTopAnchor(buildFactory, 5d);
        
        Image generatorImage = new Image(getClass().getResourceAsStream(Building.IMG_GENERATOR));
        ImageView generatorImageView = new ImageView(generatorImage);
        generatorImageView.setFitWidth(SIZE);
        generatorImageView.setFitHeight(SIZE);

        buildGenerator = new Button();
        buildGenerator.setGraphic(generatorImageView);
        buildGenerator.setTooltip(new Tooltip("Build a new generator"));
        buildGenerator.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            build(new Generator(null));
        });
        
        super.getChildren().add(buildGenerator);
        AnchorPane.setLeftAnchor(buildGenerator, 5d);
        AnchorPane.setTopAnchor(buildGenerator, 45d);
        
        Image warehouseImage = new Image(getClass().getResourceAsStream(Building.IMG_WAREHOUSE));
        ImageView warehouseImageView = new ImageView(warehouseImage);
        warehouseImageView.setFitWidth(SIZE);
        warehouseImageView.setFitHeight(SIZE);

        buildWarehouse = new Button();
        buildWarehouse.setGraphic(warehouseImageView);
        buildWarehouse.setTooltip(new Tooltip("Build a new warehouse"));
        buildWarehouse.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            build(new Warehouse(null));
        });
        
        super.getChildren().add(buildWarehouse);
        AnchorPane.setLeftAnchor(buildWarehouse, 5d);
        AnchorPane.setTopAnchor(buildWarehouse, 85d);
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private static void build(Building newBuilding) {
        IDisplayable selectedItem = DisplayPane.getInstance().getElementToDisplay();
        if (selectedItem instanceof SurfaceTile) {
            SurfaceTile tile = (SurfaceTile) selectedItem;
            if (tile.getBuilding() == null) {
                Player player = UniverseManager.getInstance().getPlayer();
                int price = newBuilding.getPrice();
                if (price <= player.getCredits()) {
                    newBuilding.setParent((Base) tile.getParent());
                    tile.setBuilding(newBuilding);
                    BaseMapFrame.getInstance().paintBaseMap();
                    DisplayPane.getInstance().setElementToDisplay(newBuilding);
                    player.spendCredits(price);
                    PlayerPane.getInstance().updatePlayerCredits();
                    InfoUtils.showMessage("Building purchased!");
                } else {
                    InfoUtils.showMessage("Insufficent credits!");
                }
            } else {
                InfoUtils.showMessage("Tile is occupied by another building!");
            }
        }
    }
}
