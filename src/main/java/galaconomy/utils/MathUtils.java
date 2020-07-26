package galaconomy.utils;

import galaconomy.universe.map.AbstractMapElement;

public class MathUtils {
    
    public static double getDistanceBetween(AbstractMapElement point1, AbstractMapElement point2) {
        return getDistance(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }
    
    public static double getDistance(int x1, int y1, int x2, int y2) {
        double a = Math.abs(x1 - x2);
        double b = Math.abs(y1 - y2);
        return Math.sqrt(a * a + b * b);
    }

}
