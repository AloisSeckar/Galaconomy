package galaconomy.universe.building;

import galaconomy.universe.economy.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;

public class Mine extends Building {
    
    // TODO similar to factory it can mine different raw materials...
    
    public Mine(Base base, Player owner) {
        super(MINE, "Universal platform for harvesting resources", IMG_MINE, 850, base, owner);
    }
    
    @Override
    public String displayDscr() {
        StringBuilder mineDscr = new StringBuilder();
        mineDscr.append(super.displayDscr());
        mineDscr.append("\n\n");
        
        mineDscr.append("Level:").append(getLevel()).append("\n");
        mineDscr.append("Productivity:").append(getProductivity()).append("\n");
        
        mineDscr.append("OUTPUT: ").append(Goods.ORE);
        
        return mineDscr.toString();
    }
    
    @Override
    public Cargo produce() {
        return new Cargo(Goods.getGoodsByName(Goods.ORE), getProductivity(), getCurrentOwner(), getParent());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private int getProductivity() {
        return getLevel() * 10;
    }
    
}
