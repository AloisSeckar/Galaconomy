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
        
        farmDscr.append("Level:").append(getLevel());
        farmDscr.append("Productivity:").append(getProductivity());
        farmDscr.append("\n");
        
        farmDscr.append("OUTPUT: ").append(Goods.FOOD);
        
        return farmDscr.toString();
    }
    
    public Supplies produce() {
        return new Supplies(Goods.getGoodsByName(Goods.FOOD), getProductivity());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private int getProductivity() {
        return getLevel() * 20;
    }
    
}
