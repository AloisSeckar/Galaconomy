package galaconomy.universe.systems;

import java.awt.Color;
import java.util.*;

public class Star extends MapElement {
    
    private final List<StellarObject> stellarObjects = new ArrayList<>();

    public Star(String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord);
    }

    public List<StellarObject> getStellarObjects() {
        return stellarObjects;
    }
    
    public boolean addStellarObject(StellarObject newObject) {
        return stellarObjects.add(newObject);
    }
    
}
