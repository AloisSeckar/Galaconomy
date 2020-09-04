package galaconomy.gui.pane;

import galaconomy.constants.Constants;
import galaconomy.gui.BaseMapFrame;
import galaconomy.universe.*;
import galaconomy.universe.building.*;
import static galaconomy.universe.building.Building.*;
import galaconomy.universe.economy.TradeHelper;
import galaconomy.universe.map.*;
import galaconomy.universe.player.Player;
import galaconomy.utils.DisplayUtils;
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
    private final Button buyLand;
    
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
        
        buyLand = new Button();
        buyLand.setGraphic(getButtonImage(Constants.ICONS_FOLDER + "money.png"));
        buyLand.setTooltip(new Tooltip("Buy selected land"));
        buyLand.addEventHandler(MouseEvent.MOUSE_PRESSED, (MouseEvent ime) -> {
            buyLand();
        });
        
        super.getChildren().add(buyLand);
        AnchorPane.setLeftAnchor(buyLand, 10d + SIZE + 20);
        AnchorPane.setTopAnchor(buyLand, 10d + 2 * SIZE + 30);
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private ImageView getButtonImage(String imgSrc) {
        return DisplayUtils.getImageView(imgSrc, SIZE);
    }
    
    private void build(String buildingType) {
        IDisplayable selectedItem = DisplayPane.getInstance().getElementToDisplay();
        if (selectedItem instanceof SurfaceTile) {
            SurfaceTile land = (SurfaceTile) selectedItem;
            Player player = UniverseManager.getInstance().getHumanPlayer();
            Player owner = land.getCurrentOwner();
            if (player.equals(owner)) {
                if (land.isEmpty()) {
                    Building newBuilding = GLSFactory.deliverBuilding(buildingType);
                    ResultBean tradeResult = TradeHelper.tradeAsset(newBuilding, player);
                    if (tradeResult.isSuccess()) {
                        newBuilding.setParent(land);
                        land.setBuilding(newBuilding);
                        player.addBuilding(newBuilding);
                        BaseMapFrame.getInstance().paintBaseMap();
                        DisplayPane.getInstance().setElementToDisplay(newBuilding);
                        PlayerPane.getInstance().updatePlayerCredits();
                        InfoUtils.showMessage("Building purchased!");
                    } else {
                        InfoUtils.showMessage(tradeResult.getMessage());
                    }
                } else {
                    InfoUtils.showMessage("Selected land is occupied by another building!");
                }
            } else {
                InfoUtils.showMessage("You must own the land to build on it!");
            }
        } else {
            InfoUtils.showMessage("Please select surface tile to build on");
        }
    }
    
    private void buyLand() {
        IDisplayable selectedItem = DisplayPane.getInstance().getElementToDisplay();
        if (selectedItem instanceof SurfaceTile) {
            SurfaceTile land = (SurfaceTile) selectedItem;
            Player player = UniverseManager.getInstance().getHumanPlayer();
            Player owner = land.getCurrentOwner();
            if (!player.equals(owner)) {
                if (owner.equals(UniverseManager.getInstance().getGLSPlayer())) {
                    if (land.isEmpty()) {
                        ResultBean tradeResult = TradeHelper.tradeAsset(land, player);
                        if (tradeResult.isSuccess()) {
                            player.addLand(land);
                            DisplayPane.getInstance().setElementToDisplay(land);
                            PlayerPane.getInstance().updatePlayerCredits();
                            InfoUtils.showMessage("Land purchased!");
                        } else {
                            InfoUtils.showMessage(tradeResult.getMessage());
                        }
                    } else {
                        InfoUtils.showMessage("Only empty lands can be purchased");
                    }
                } else {
                    // TODO allow it if player put selected land on the market
                    InfoUtils.showMessage("Purchasing land from other players is not supported yet");
                }
            } else {
                InfoUtils.showMessage("Selected land is already yours");
            }
        } else {
            InfoUtils.showMessage("Please select surface tile to purchase");
        }
    }
}
