package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.IDisplayable;
import galaconomy.universe.map.Base;
import java.io.Serializable;

public abstract class Building implements IDisplayable, Serializable {
    
    private static final String BUILDING = Constants.BUILDINGS_FOLDER;
    public static final String IMG_CITY = BUILDING + "city.png";
    public static final String IMG_FACTORY = BUILDING + "factory.png";
    public static final String IMG_FARM = BUILDING + "farm.png";
    public static final String IMG_GENERATOR = BUILDING + "generator.png";
    public static final String IMG_MINE = BUILDING + "mine.png";
    public static final String IMG_SHIPYARD = BUILDING + "shipyard.png";
    public static final String IMG_WAREHOUSE = BUILDING + "warehouse.png";
    
    private final String name;
    private final String dscr; 
    private final String img;
    
    private final int price;
    
    private Base parent;

    public Building(String name, String dscr, String img, int price, Base parent) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        this.price = price;
        this.parent = parent;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        return dscr;
    }

    @Override
    public String getImage() {
        return img;
    }

    public int getPrice() {
        return price;
    }
    
    public Base getParent() {
        return parent;
    }

    public void setParent(Base parent) {
        this.parent = parent;
    }
    
}
