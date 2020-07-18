package galaconomy.universe.map;

import galaconomy.universe.building.Building;
import galaconomy.universe.economy.*;
import java.awt.Color;
import java.util.*;

public class SurfaceTile extends AbstractMapElement {
    
    private final Map<String, Supplies> rawMaterials = new HashMap<>();
    
    private Building building = null; 

    public SurfaceTile(String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord);
    }
    
    @Override
    public String displayDscr() {
        StringBuilder starDscr = new StringBuilder();
        
        starDscr.append("SUPPLIES").append("\n");
        starDscr.append("----------").append("\n");
        for (Supplies goods : rawMaterials.values()) {
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

    public Map<String, Supplies> getRawMaterialsSupply() {
        return rawMaterials;
    }
    
    public Supplies findRawMaterial(String key) {
        Supplies ret = rawMaterials.get(key);
        if (ret == null) {
            ret = new Supplies(Goods.getGoodsByName(key), 0);
        }
        return ret;
    }
    
    public void updateRawMaterials(Supplies newRawMaterial) {
        rawMaterials.put(newRawMaterial.displayName(), newRawMaterial);
    }
    
    public void performMining(Goods rawMaterial, int amount) {
        String rawMaterialName = rawMaterial.displayName();
        Supplies currentSupply = findRawMaterial(rawMaterialName);
        if (currentSupply == null) {
            currentSupply = new Supplies(rawMaterial, 0);
        }
        currentSupply.decreaseAmount(amount);
        if (currentSupply.getAmount() < 1) {
            rawMaterials.remove(rawMaterialName);
        }
    }
    
    public boolean isEmpty() {
        return building == null;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
    
}
