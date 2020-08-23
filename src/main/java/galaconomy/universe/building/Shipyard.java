package galaconomy.universe.building;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.economy.Cargo;
import galaconomy.universe.map.Base;

public class Shipyard extends Building {

    public Shipyard(Base base) {
        super(base.displayName() + " - Shipyard", "New ships can be purchased here", IMG_SHIPYARD, 0, base, UniverseManager.getInstance().getGLSPlayer());
    }

    @Override
    public Cargo produce() {
        return null;
    }
    
}
