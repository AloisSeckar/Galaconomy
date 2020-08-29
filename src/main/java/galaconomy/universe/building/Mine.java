package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.economy.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;

public class Mine extends Building {
    
    private Goods output;
    
    public Mine(Base base, Player owner) {
        super(MINE, "Universal platform for harvesting resources", IMG_MINE, 850, base, owner);
        output = Goods.getRandomFactoryProduct();
    }
    
    @Override
    public String displayDscr() {
        StringBuilder mineDscr = new StringBuilder();
        mineDscr.append(super.displayDscr());
        mineDscr.append("\n\n");
        
        mineDscr.append("Level:").append(getLevel()).append("\n");
        mineDscr.append("Productivity:").append(getProductivity()).append("\n");
        
        mineDscr.append("OUTPUT: ").append(output != null ? output.displayName() : Constants.NONE);
        
        return mineDscr.toString();
    }

    public Goods getOutput() {
        return output;
    }

    public void setOutput(Goods output) {
        this.output = output;
    }
    
    @Override
    public Cargo produce() {
        return new Cargo(output, getProductivity(), getCurrentOwner(), this);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private int getProductivity() {
        return getLevel() * 10;
    }
    
}
