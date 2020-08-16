package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.map.Base;

public class City extends Building {

    public City(Base base) {
        super(base.displayName(), base.displayDscr(), Constants.BUILDINGS_FOLDER + "city.png", base);
    }
    
}
