package galaconomy.universe.map;

import galaconomy.constants.Constants;
import galaconomy.utils.MathUtils;
import java.awt.Color;

public class Connector extends AbstractMapElement {
    
    private static long created = 0;
    
    private final Star point1;
    private final Star point2;
    private final double distance;

    public Connector(Star point1, Star point2) throws Exception {
        super("Connector" + created++, "Permanent rift connection between two systems", Constants.FOLDER_IMG + "rift_gate.png", Color.GRAY, 0, 0, null);
        if (point1 != null && point2 != null) {
            this.point1 = point1;
            this.point2 = point2;
            
            int x1 = point1.getX();
            int y1 = point1.getY();
            int x2 = point2.getX();
            int y2 = point2.getY();
            this.distance = MathUtils.getDistance(x1, y1, x2, y2);
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

    public double getDistance() {
        return distance;
    }

    @Override
    public String displayName() {
        return point1.getName() + " to " + point2.getName();
    }
    
}
