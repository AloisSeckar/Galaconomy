package galaconomy.utils;

public class DisplayUtils {
    
    public static final int DEFAULT_ZOOM_MULTIPLIER = 8;
    public static final int BORDER_OFFSET = 8;
    public static final int BASE_TILE_SIZE = 5;
    
    public static String formatDouble(double input) {
        return String.format("%.2f", input);
    }
    
    public static String getCoordinates(int x, int y) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(" [");
        sb.append(x);
        sb.append(":");
        sb.append(y);
        sb.append("] ");
        
        return sb.toString();                   
    }
    
    public static double fitCoordIntoDisplay(double coord) {
        return coord * DEFAULT_ZOOM_MULTIPLIER + BORDER_OFFSET;
    }
}
