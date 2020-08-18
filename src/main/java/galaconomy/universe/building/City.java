package galaconomy.universe.building;

import galaconomy.universe.map.Base;

public class City extends Building {

    public City(Base base) {
        super(base.displayName(), base.displayDscr(), IMG_CITY, 0, base);
    }
    
}
