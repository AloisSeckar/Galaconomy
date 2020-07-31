package galaconomy.universe.map;

import galaconomy.constants.Constants;
import java.awt.Color;

public class RiftGate extends AbstractMapElement {
    
    private final Star target;

    public RiftGate(String name, String dscr, int xCoord, int yCoord, Star origin, Star target) throws Exception {
        super(name, dscr, Constants.FOLDER_IMG + "rift_gate.png", Color.GRAY, xCoord, yCoord, origin);
        if (target != null) {
            this.target = target;
        } else {
            throw new Exception("Target cannot be NULL");
        }
    }

    public Star getTarget() {
        return target;
    }
    
}
