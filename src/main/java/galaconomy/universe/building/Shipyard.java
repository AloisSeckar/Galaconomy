package galaconomy.universe.building;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.economy.Cargo;
import galaconomy.universe.map.SurfaceTile;

public class Shipyard extends Building {

    public Shipyard(SurfaceTile parent) {
        super(parent.getParent().displayName() + " - Shipyard", "New ships can be purchased here", IMG_SHIPYARD, 0, parent, UniverseManager.getInstance().getGLSPlayer());
    }

    @Override
    public Cargo produce() {
        return null;
    }
    
}
