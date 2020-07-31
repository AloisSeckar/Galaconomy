package galaconomy.universe.map;

import java.awt.Color;
import java.util.*;

public class Star extends AbstractMapElement {
    
    private final List<StellarObject> stellarObjects = new ArrayList<>();
    
    private RiftPortal riftPortal;
    private final List<RiftGate> riftGates = new ArrayList<>();

    public Star(String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord, null);
    }
    
    @Override
    public String displayDscr() {
        StringBuilder starDscr = new StringBuilder();
        
        starDscr.append("INFO").append("\n");
        starDscr.append("----------").append("\n");
        starDscr.append(super.displayDscr());
        
        return starDscr.toString();
    }
    
    public List<StellarObject> getStellarObjects() {
        return stellarObjects;
    }
    
    public List<Base> getBases() {
        List<Base> bases = new ArrayList<>();
        for (StellarObject stellarObject : stellarObjects) {
            if (stellarObject instanceof Base) {
                bases.add((Base) stellarObject);
            }
        }
        return bases;
    }
    
    public boolean addStellarObject(StellarObject newObject) {
        return stellarObjects.add(newObject);
    }

    public RiftPortal getRiftPortal() {
        return riftPortal;
    }

    public void setRiftPortal(RiftPortal riftPortal) {
        this.riftPortal = riftPortal;
    }
    
    public List<RiftGate> getRiftGates() {
        return riftGates;
    }
    
    public RiftGate getRiftGateTo(Star system) {
        RiftGate ret = null;
        
        if (system != null) {
            for (RiftGate gate : riftGates) {
                if (system.equals(gate.getTarget())) {
                    ret = gate;
                    break;
                }
            }
        }
        
        return ret;
    }
    
    public boolean addRiftGate(RiftGate newObject) {
        return riftGates.add(newObject);
    }
}
