package galaconomy.universe.building;

import galaconomy.universe.economy.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;

public class Farm extends Building {
    
    public Farm(Base base, Player owner) {
        super(FARM, "Food is being produced here", IMG_FARM, 600, base, owner);
    }
    
    @Override
    public String displayDscr() {
        StringBuilder farmDscr = new StringBuilder();
        farmDscr.append(super.displayDscr());
        farmDscr.append("\n\n");
        
        farmDscr.append("Level:").append(getLevel()).append("\n");
        farmDscr.append("Productivity:").append(getProductivity()).append("\n");
        
        farmDscr.append("OUTPUT: ").append(Goods.FOOD);
        
        return farmDscr.toString();
    }
    
    @Override
    public Cargo produce() {
        return new Cargo(Goods.getGoodsByName(Goods.FOOD), getProductivity(), getCurrentOwner(), this);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private int getProductivity() {
        return getLevel() * 20;
    }
    
}
