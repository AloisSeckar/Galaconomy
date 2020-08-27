package galaconomy.universe.map;

import galaconomy.universe.*;
import galaconomy.universe.building.Building;
import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import galaconomy.utils.DisplayUtils;
import java.awt.Color;
import java.util.*;

public class SurfaceTile extends AbstractMapElement implements ITradable, IStorage {
    
    private final Map<String, Supplies> rawMaterials = new HashMap<>();
    
    private Building building = null; 
    
    private int price;
    private Player currentOwner;
    private final List<Player> owners = new ArrayList<>();
    
    public SurfaceTile(String name, String dscr, String img, Color color, int xCoord, int yCoord, Base parent, Player owner) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord, parent);
        this.currentOwner = owner;
        this.price = 100;
    }
    
    @Override
    public String displayName() {
        return getParent().getName() + DisplayUtils.getCoordinates(getX(), getY());
    }
    
    @Override
    public String displayDscr() {
        StringBuilder tileDscr = new StringBuilder();
        
        tileDscr.append("Owner: ");
        if (currentOwner != null) {
            tileDscr.append(currentOwner.displayName());
        }
        tileDscr.append("\n\n");
                
        tileDscr.append("SUPPLIES").append("\n");
        tileDscr.append("----------").append("\n");
        rawMaterials.values().forEach((goods) -> {
            tileDscr.append(goods.displayName());
            tileDscr.append("\tB: ").append(goods.getPriceBuy());
            tileDscr.append("\tS: ").append(goods.getPriceSell());
            tileDscr.append("\n");
        });
        tileDscr.append("\n");
        
        tileDscr.append("INFO").append("\n");
        tileDscr.append("----------").append("\n");
        tileDscr.append(super.displayDscr());
        
        return tileDscr.toString();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }
    
    @Override
    public Player getCurrentOwner() {
        return currentOwner;
    }

    @Override
    public List<Player> getOwners() {
        return owners;
    }
    
    @Override
    public void changeOwner(Player newOwner) {
        owners.add(0, newOwner);
        currentOwner = newOwner;
    }

    @Override
    public String getStorageIdentity() {
        return displayName();
    }

    public Map<String, Supplies> getRawMaterialsSupply() {
        return rawMaterials;
    }
    
    public Supplies findRawMaterial(String key) {
        Supplies ret = rawMaterials.get(key);
        if (ret == null) {
            Cargo newCargo = new Cargo(Goods.getGoodsByName(key), 0, UniverseManager.getInstance().getGLSPlayer(), this);
            ret = new Supplies(newCargo, 0, 0);
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
            Cargo newCargo = new Cargo(rawMaterial, 0, UniverseManager.getInstance().getGLSPlayer(), this);
            currentSupply = new Supplies(newCargo, 0, 0);
        }
        currentSupply.decreaseSupply(amount);
        if (currentSupply.getCargo().getAmount() < 1) {
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
