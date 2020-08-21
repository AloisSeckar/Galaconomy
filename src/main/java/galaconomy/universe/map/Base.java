package galaconomy.universe.map;

import galaconomy.constants.Constants;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.building.*;
import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import galaconomy.utils.DisplayUtils;
import java.awt.Color;
import java.util.*;

public class Base extends StellarObject {
    
    private static final int COLS = Constants.MAX_X / DisplayUtils.BASE_TILE_SIZE;
    private static final int ROWS = Constants.MAX_Y / DisplayUtils.BASE_TILE_SIZE;
    
    private final SurfaceTile[][] surface = new SurfaceTile[COLS][ROWS];
    
    private final Map<String, Supplies> supplies = new HashMap<>();
    
    private boolean shipyard = false;

    public Base(Star system, String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord, system);
        
        Player glsPlayer = UniverseManager.getInstance().getGLSPlayer();
        
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Random rand = new Random();
                switch (rand.nextInt() % 4) {
                    case 3:
                        surface[col][row] = new SurfaceTile("Mountains", "Brown", Constants.TILES_FOLDER + "04.png", Color.GREEN, col, row, this, glsPlayer);
                        break;
                    case 2:
                        surface[col][row] = new SurfaceTile("Water", "Blue", Constants.TILES_FOLDER + "03.png", Color.GREEN, col, row, this, glsPlayer);
                        break;
                    case 1:
                        surface[col][row] = new SurfaceTile("Desert", "Yellow", Constants.TILES_FOLDER + "02.png", Color.GREEN, col, row, this, glsPlayer);
                        break;
                    default:
                        surface[col][row] = new SurfaceTile("Grass", "Green", Constants.TILES_FOLDER + "01.png", Color.GREEN, col, row, this, glsPlayer);
                }
                
                if (row == ROWS / 2 && col == COLS / 2) {
                    Building building = new City(this);
                    surface[col][row].setBuilding(building);
                }
            }
        }
    }
    
    @Override
    public String displayDscr() {
        StringBuilder starDscr = new StringBuilder();
        
        starDscr.append("\tShipyard: ").append(shipyard);
        starDscr.append("\n\n");
        
        starDscr.append("SUPPLIES").append("\n");
        starDscr.append("----------").append("\n");
        supplies.values().forEach(goods -> {
            starDscr.append(goods.displayName());
            starDscr.append("\tB: ").append(goods.getPriceBuy());
            starDscr.append("\tS: ").append(goods.getPriceSell());
            starDscr.append("\n");
        });
        starDscr.append("\n");
        
        starDscr.append("INFO").append("\n");
        starDscr.append("----------").append("\n");
        starDscr.append(super.displayDscr());
        
        return starDscr.toString();
    }

    public SurfaceTile[][] getSurface() {
        return surface;
    }

    public boolean isShipyard() {
        return shipyard;
    }

    public void setShipyard(boolean shipyard) {
        this.shipyard = shipyard;
        
        if (shipyard) {
            surface[COLS / 2][ROWS / 2 + 1].setBuilding(new Shipyard(this));
        } else {
            surface[COLS / 2][ROWS / 2 + 1].setBuilding(null);
        }
    }

    public Map<String, Supplies> getSupplies() {
        return supplies;
    }
    
    public Supplies findSupplies(String key) {
        Supplies ret = supplies.get(key);
        if (ret == null) {
            ret = new Supplies(Goods.getGoodsByName(key), 0);
        }
        return ret;
    }
    
    public void updateSupplies(Supplies newSupply) {
        supplies.put(newSupply.getId(), newSupply);
    }
    
    public void performPurchase(Cargo cargo) {
        String goodsName = cargo.getGoods().displayName();
        Supplies currentSupply = findSupplies(goodsName);
        if (currentSupply == null) {
            currentSupply = new Supplies(cargo.getGoods(), 0);
            supplies.put(goodsName, currentSupply);
        }
        currentSupply.increaseAmount(cargo.getAmount());
    }
    
    public void performSale(Goods goods, int amount) {
        String goodsName = goods.displayName();
        Supplies currentSupply = findSupplies(goodsName);
        if (currentSupply == null) {
            currentSupply = new Supplies(goods, 0);
        }
        currentSupply.decreaseAmount(amount);
        if (currentSupply.getAmount() < 1) {
            supplies.remove(goodsName);
        }
    }
}
