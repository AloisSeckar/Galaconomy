package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.economy.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.map.SurfaceTile;
import galaconomy.universe.player.Player;
import galaconomy.utils.StorageUtils;
import galaconomy.utils.result.ResultBean;
import java.io.Serializable;
import java.util.*;

public abstract class Building implements IDisplayable, ITradable, IStorage, Serializable {
    
    public static final String CITY = "City";
    public static final String FACTORY = "Factory";
    public static final String FARM = "Farm";
    public static final String GENERATOR = "Generator";
    public static final String MINE = "Mine";
    public static final String SHIPYARD = "Shipyard";
    public static final String WAREHOUSE = "Warehouse";
    
    private static final String BUILDING = Constants.BUILDINGS_FOLDER;
    private static final String PNG = ".png";
    
    public static final String IMG_CITY = BUILDING + CITY + PNG;
    public static final String IMG_FACTORY = BUILDING + FACTORY + PNG;
    public static final String IMG_FARM = BUILDING + FARM + PNG;
    public static final String IMG_GENERATOR = BUILDING + GENERATOR + PNG;
    public static final String IMG_MINE = BUILDING + MINE + PNG;
    public static final String IMG_SHIPYARD = BUILDING + SHIPYARD + PNG;
    public static final String IMG_WAREHOUSE = BUILDING + WAREHOUSE + PNG;
    
    private final String name;
    private final String dscr; 
    private final String img;
    
    private int price;
    private Player currentOwner;
    private final List<Player> owners = new ArrayList<>();
    
    private SurfaceTile parent;
    
    private byte level;
    
    private int storageCapacity;
    private final HashMap<String, Cargo> storage = new HashMap<>();
            
    public Building(String name, String dscr, String img, int price, SurfaceTile parent, Player owner) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        this.price = price;
        this.parent = parent;
        this.currentOwner = owner;
        this.level = 1;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        StringBuilder buildingDscr = new StringBuilder(dscr);
        buildingDscr.append("\n\nOwner: ");
        if (currentOwner != null) {
            buildingDscr.append(currentOwner.displayName());
        }
        return buildingDscr.toString();
    }

    @Override
    public String getImage() {
        return img;
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

    @Override
    public int getStorageCapacity() {
        return storageCapacity;
    }
    
    @Override
    public Player getStorageOwner() {
        return getCurrentOwner();
    }

    @Override
    public List<Cargo> getCurrentCargo() {
        List<Cargo> ret = new ArrayList<>();
        ret.addAll(storage.values());
        return ret;
    }
    
    @Override
    public ResultBean storeCargo(Cargo cargo) {
        return StorageUtils.storeCargo(cargo, this);
    }

    @Override
    public ResultBean withdrawCargo(Goods goods, int amount) {
        return StorageUtils.withdrawCargo(goods, amount, this);
    }

    @Override
    public Cargo get(String key) {
         return storage.get(key);
    }

    @Override
    public Cargo put(String key, Cargo cargo) {
         return storage.put(key, cargo);
    }

    @Override
    public Cargo remove(String key) {
         return storage.remove(key);
    }
    
    public Base getParentBase() {
        return parent != null ? (Base) parent.getParent() : null;
    }

    public SurfaceTile getParent() {
        return parent;
    }

    public void setParent(SurfaceTile parent) {
        this.parent = parent;
    }

    public byte getLevel() {
        return level;
    }

    public void levelUp() {
        if (level < 255) {
            level++;
        }
    }
    
    public int getFreeStorageCapacity() {
        int usedSpace = 0;
        usedSpace = storage.values().stream().map((cargo) -> cargo.getAmount()).reduce(usedSpace, Integer::sum);   
        return Math.min(storageCapacity - usedSpace, 0);
    }

    public void increaseStorageCapacity(int increase) {
        this.storageCapacity += increase;
    }
    
    public abstract Cargo produce();
    
}
