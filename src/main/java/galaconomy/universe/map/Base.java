package galaconomy.universe.map;

import galaconomy.constants.Constants;
import galaconomy.universe.building.Building;
import galaconomy.universe.economy.*;
import java.awt.Color;
import java.util.*;

public class Base extends StellarObject {
    
    private static final int COLS = Constants.MAX_X / 3;
    private static final int ROWS = Constants.MAX_Y / 3;
    
    private final SurfaceTile[][] surface = new SurfaceTile[COLS][ROWS];
    
    private final Map<String, Supplies> supplies = new HashMap<>();

    public Base(Star system, String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord, system);
        
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Random rand = new Random();
                switch (rand.nextInt() % 4) {
                    case 3:
                        surface[col][row] = new SurfaceTile("Mountains", "Brown", Constants.TILES_FOLDER + "04.png", Color.GREEN, col, row, this);
                        break;
                    case 2:
                        surface[col][row] = new SurfaceTile("Water", "Blue", Constants.TILES_FOLDER + "03.png", Color.GREEN, col, row, this);
                        break;
                    case 1:
                        surface[col][row] = new SurfaceTile("Desert", "Yellow", Constants.TILES_FOLDER + "02.png", Color.GREEN, col, row, this);
                        break;
                    default:
                        surface[col][row] = new SurfaceTile("Grass", "Green", Constants.TILES_FOLDER + "01.png", Color.GREEN, col, row, this);
                }
                
                if (rand.nextInt() % 4 == 3) {
                    Building building = new Building("Base", "Just to have something", Constants.BUILDINGS_FOLDER + "base.png", this);
                    surface[col][row].setBuilding(building);
                }
            }
        }
    }
    
    @Override
    public String displayDscr() {
        StringBuilder starDscr = new StringBuilder();
        
        starDscr.append("SUPPLIES").append("\n");
        starDscr.append("----------").append("\n");
        for (Supplies goods : supplies.values()) {
            starDscr.append(goods.displayName());
            starDscr.append("\tB: ").append(goods.getPriceBuy());
            starDscr.append("\tS: ").append(goods.getPriceSell());
            starDscr.append("\n");
        }
        starDscr.append("\n");
        
        starDscr.append("INFO").append("\n");
        starDscr.append("----------").append("\n");
        starDscr.append(super.displayDscr());
        
        return starDscr.toString();
    }

    public SurfaceTile[][] getSurface() {
        return surface;
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
