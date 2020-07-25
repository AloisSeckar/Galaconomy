package galaconomy.universe.map;

import galaconomy.constants.Constants;
import java.awt.Color;

public class Connector extends AbstractMapElement {
    
    private final Star point1;
    private final Star point2;

    public Connector(Star point1, Star point2) throws Exception {
        super("Connector", "Permanent rift connection between two systems", Constants.FOLDER_IMG + "rift_gate.png", Color.GRAY, 0, 0);
        if (point1 != null && point2 != null) {
            this.point1 = point1;
            this.point2 = point2;
        } else {
            throw new Exception("Connected points cannot be NULL");
        }
    }

    public Star getPoint1() {
        return point1;
    }

    public Star getPoint2() {
        return point2;
    }

    @Override
    public String displayName() {
        return point1.getName() + " to " + point2.getName();
    }
    
}
