package galaconomy.universe.map;

import galaconomy.universe.economy.*;
import java.awt.Color;
import java.util.*;

public class Star extends AbstractMapElement {
    
    private final List<StellarObject> stellarObjects = new ArrayList<>();
    
    private final Map<String, Supplies> supplies = new HashMap<>();

    public Star(String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord);
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
    
    public List<StellarObject> getStellarObjects() {
        return stellarObjects;
    }
    
    public boolean addStellarObject(StellarObject newObject) {
        return stellarObjects.add(newObject);
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
