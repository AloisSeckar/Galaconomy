package galaconomy.utils;

public class MathUtils {
    
    public static double getDistance(int x1, int y1, int x2, int y2) {
        double a = Math.abs(x1 - x2);
        double b = Math.abs(y1 - y2);
        return Math.sqrt(a * a + b * b);
    }

}
