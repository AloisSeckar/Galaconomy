package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.economy.Cargo;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
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
    
    private Base parent;
    
    private byte level;

    public Building(String name, String dscr, String img, int price, Base parent, Player owner) {
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
    
    public Base getParent() {
        return parent;
    }

    public void setParent(Base parent) {
        this.parent = parent;
    }

    public byte getLevel() {
        return level;
    }

    public void levelUp() {
        this.level++;
    }
    
    public abstract Cargo produce();
    
}
