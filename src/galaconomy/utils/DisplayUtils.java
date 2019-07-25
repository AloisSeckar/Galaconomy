package galaconomy.utils;

public class DisplayUtils {
    
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
}
