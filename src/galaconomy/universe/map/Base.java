package galaconomy.universe.map;

import galaconomy.universe.economy.*;
import java.awt.Color;
import java.util.*;

public class Base extends AbstractMapElement {
    
    private static final int COLS = 50;
    private static final int ROWS = 20;
    
    private final SurfaceTile[][] surface = new SurfaceTile[COLS][ROWS];
    
    private final Map<String, Supplies> supplies = new HashMap<>();

    public Base(String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord);
        
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                surface[col][row] = new SurfaceTile("X", "Y", "", Color.GREEN, col, row);
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
