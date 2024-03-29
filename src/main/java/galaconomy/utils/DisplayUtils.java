package galaconomy.utils;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

public class DisplayUtils {
    
    public static final int SIDE_PANEL_X = 280;
    public static final int SIDE_PANEL_X_WRAP = 300;
    
    public static final int BOTTOM_PANEL_Y = 250;
    public static final int BOTTOM_PANEL_Y_WRAP = 300;
    
    public static final int DEFAULT_TILE_SIZE = 8;
    public static final int BASE_TILE_SIZE = 5;
    
    private static int SCREEN_X;
    private static int SCREEN_Y;
    
    private static int MAX_X;
    private static int MAX_Y;
    
    private static int MAIN_PANEL_X;
    private static int MAIN_PANEL_Y;
    
    private static DisplayUtils INSTANCE;
    
    private DisplayUtils() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        
        SCREEN_X = Double.valueOf(screenBounds.getWidth()).intValue();
        MAX_X = Double.valueOf((SCREEN_X - SIDE_PANEL_X_WRAP) / DEFAULT_TILE_SIZE).intValue();
        MAIN_PANEL_X = (MAX_X + 2) * DEFAULT_TILE_SIZE;
        
        SCREEN_Y = Double.valueOf(screenBounds.getHeight()).intValue();
        MAX_Y = Double.valueOf((SCREEN_Y - BOTTOM_PANEL_Y_WRAP) / DEFAULT_TILE_SIZE).intValue();
        MAIN_PANEL_Y = (MAX_Y + 2) * DEFAULT_TILE_SIZE;
    }
    
    public static void init() {
        if (INSTANCE == null) {
            INSTANCE = new DisplayUtils();
        }
    }

    public static int getSCREEN_X() {
        return SCREEN_X;
    }

    public static int getSCREEN_Y() {
        return SCREEN_Y;
    }

    public static int getMAX_X() {
        return MAX_X;
    }

    public static int getMAX_Y() {
        return MAX_Y;
    }

    public static int getMAIN_PANEL_X() {
        return MAIN_PANEL_X;
    }

    public static int getMAIN_PANEL_Y() {
        return MAIN_PANEL_Y;
    }
    
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
        return (coord + 1) * DEFAULT_TILE_SIZE; // +1 as border offset
    }
    
}
