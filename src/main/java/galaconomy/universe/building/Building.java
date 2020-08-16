package galaconomy.universe.building;

import galaconomy.universe.IDisplayable;
import galaconomy.universe.map.Base;
import java.io.Serializable;

public abstract class Building implements IDisplayable, Serializable {
    
    private final String name;
    private final String dscr; 
    private final String img;
    
    private final Base parent;

    public Building(String name, String dscr, String img, Base parent) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
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

    public Base getParent() {
        return parent;
    }
    
}
