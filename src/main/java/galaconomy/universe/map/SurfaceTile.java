package galaconomy.universe.map;

import galaconomy.universe.building.Building;
import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import java.awt.Color;
import java.util.*;

public class SurfaceTile extends AbstractMapElement {
    
    private final Map<String, Supplies> rawMaterials = new HashMap<>();
    
    private Building building = null; 
    
    private Player owner;

    public SurfaceTile(String name, String dscr, String img, Color color, int xCoord, int yCoord, Base parent, Player owner) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord, parent);
        this.owner = owner;
    }
    
    @Override
    public String displayDscr() {
        StringBuilder tileDscr = new StringBuilder(super.displayDscr());
        
        tileDscr.append("\nOwner:");
        if (owner != null) {
            tileDscr.append(owner.displayName());
        }
        tileDscr.append("\n");
                
        tileDscr.append("SUPPLIES").append("\n");
        tileDscr.append("----------").append("\n");
        for (Supplies goods : rawMaterials.values()) {
            tileDscr.append(goods.displayName());
            tileDscr.append("\tB: ").append(goods.getPriceBuy());
            tileDscr.append("\tS: ").append(goods.getPriceSell());
            tileDscr.append("\n");
        }
        tileDscr.append("\n");
        
        tileDscr.append("INFO").append("\n");
        tileDscr.append("----------").append("\n");
        tileDscr.append(super.displayDscr());
        
        return tileDscr.toString();
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

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
}
