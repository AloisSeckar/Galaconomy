package galaconomy.universe.map;

import galaconomy.constants.Constants;
import java.awt.Color;

public class RiftPortal extends AbstractMapElement {

    public RiftPortal(String name, String dscr, int xCoord, int yCoord, Star origin) throws Exception {
        super(name, dscr, Constants.FOLDER_IMG + "rift_portal.png", Color.GRAY, xCoord, yCoord, origin);
    }
    
}
