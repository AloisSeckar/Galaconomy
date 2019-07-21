package galaconomy.universe.systems;

import java.awt.Color;
import java.util.*;

public class StarSystem extends MapElement {
    
    private final List<StellarObject> stellarObjects = new ArrayList<>();

    public StarSystem(String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord);
    }

    public List<StellarObject> getStellarObjects() {
        return Collections.unmodifiableList(stellarObjects);
    }
    
    public boolean addStellarObject(StellarObject newObject) {
        return stellarObjects.add(newObject);
    }
    
}
