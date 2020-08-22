package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import java.io.Serializable;
import java.util.*;

public abstract class Building implements IDisplayable, ITradable, Serializable {
    
    public static final String CITY = "city";
    public static final String FACTORY = "factory";
    public static final String FARM = "farm";
    public static final String GENERATOR = "generator";
    public static final String MINE = "mine";
    public static final String SHIPYARD = "shipyard";
    public static final String WAREHOUSE = "warehouse";
    
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

    public Building(String name, String dscr, String img, int price, Base parent, Player owner) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        this.price = price;
        this.parent = parent;
        this.currentOwner = owner;
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
    
    public Base getParent() {
        return parent;
    }

    public void setParent(Base parent) {
        this.parent = parent;
    }
    
}
