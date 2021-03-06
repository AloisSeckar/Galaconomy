package galaconomy.universe.map;

import galaconomy.constants.Constants;
import galaconomy.universe.IDisplayable;
import galaconomy.utils.DisplayUtils;
import java.awt.Color;
import java.io.Serializable;
import java.util.Objects;
import javafx.scene.paint.Paint;

public abstract class AbstractMapElement implements IDisplayable, Serializable {
    
    private final String name;
    private final String dscr; 
    private final String img; 
    private final Color color;
    private final int xCoord;
    private final int yCoord;
    private final AbstractMapElement parent;
    
    public AbstractMapElement(String name, String dscr, String img, Color color, int xCoord, int yCoord, AbstractMapElement parent) throws Exception {
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
        
        if (xCoord <= DisplayUtils.getMAX_X()) {
            this.xCoord = xCoord;
        } else {
            throw new IllegalArgumentException("Given xCoord is out of map bounds");
        }
        
        if (yCoord <= DisplayUtils.getMAX_Y()) {
            this.yCoord = yCoord;
        } else {
            throw new IllegalArgumentException("Given yCoord is out of map bounds");
        }
        
        this.parent = parent;
    }

    @Override
    public String displayName() {
        return name + DisplayUtils.getCoordinates(xCoord, yCoord);
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

    public String getName() {
        return name;
    }
    
    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public AbstractMapElement getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return displayName();
    }

    @Override
    public int hashCode() {
        return name.hashCode() + xCoord + yCoord;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AbstractMapElement other = (AbstractMapElement) obj;
        if (this.xCoord != other.xCoord) {
            return false;
        }
        if (this.yCoord != other.yCoord) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.parent, other.parent)) {
            return false;
        }
        return true;
    }
    
}
