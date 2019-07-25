package galaconomy.universe.systems;

import galaconomy.constants.Constants;
import galaconomy.universe.IDisplayable;
import java.awt.Color;
import javafx.scene.paint.Paint;

public abstract class MapElement implements IDisplayable {
    
    private final String name;
    private final String dscr; 
    private final String img; 
    private final Color color;
    private final int xCoord;
    private final int yCoord;
    
    public MapElement(String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        if (name != null) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Given name cannot be blank");
        }
        
        if (dscr != null) {
            this.dscr = dscr;
        } else {
            this.dscr = "Unknown";
        }
        
        if (img != null) {
            this.img = img;
        } else {
            this.img = Constants.DEFAULT_IMAGE;
        }
        
        if (color != null) {
            this.color = color;
        } else {
            this.color = Color.YELLOW;
        }
        
        if (xCoord <= Constants.MAX_X) {
            this.xCoord = xCoord;
        } else {
            throw new IllegalArgumentException("Given xCoord is out of universe bounds");
        }
        
        if (yCoord <= Constants.MAX_Y) {
            this.yCoord = yCoord;
        } else {
            throw new IllegalArgumentException("Given yCoord is out of universe bounds");
        }
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        return dscr;
    }

    @Override
    public String getImage() {
        return img;
    }
    
    public Paint getFXColor() {
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        return javafx.scene.paint.Color.rgb(r, g, b);
    }
    
    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    @Override
    public String toString() {
        return name + " [" + xCoord + ";" + yCoord + "]";
    }
    
}
