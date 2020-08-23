package galaconomy.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.slf4j.*;

public class DisplayUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(DisplayUtils.class);
    
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
    
    public static ImageView getImageView(String source, int dimension) {
        return getImageView(source, dimension, dimension);
    }
    
    public static ImageView getImageView(String source, int width, int height) {
        ImageView object = new ImageView();
        object.setPreserveRatio(false);
        object.setSmooth(true);
        object.setCache(true);
        
        try {
            Image objectImg = new Image(DisplayUtils.class.getClass().getResourceAsStream(source));
            object.setImage(objectImg);
        } catch(Exception ex) {
            LOG.error("DisplayUtils.getImageView", ex);
        }
        
        object.setFitWidth(width);
        object.setFitHeight(height);
        
        return object;
    }
}
