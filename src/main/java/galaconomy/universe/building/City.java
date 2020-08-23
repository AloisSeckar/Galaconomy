package galaconomy.universe.building;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.economy.Cargo;
import galaconomy.universe.map.Base;

public class City extends Building {

    public City(Base base) {
        super(base.displayName(), "", IMG_CITY, 0, base, UniverseManager.getInstance().getGLSPlayer());
    }
    
    @Override
    public String displayDscr() {
        return getParent().displayDscr();
    }

    @Override
    public Cargo produce() {
        return null;
    }
    
}
