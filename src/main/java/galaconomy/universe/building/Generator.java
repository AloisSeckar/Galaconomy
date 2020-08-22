package galaconomy.universe.building;

import galaconomy.universe.economy.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;

public class Generator extends Building {
   
    public Generator(Base base, Player owner) {
        super(GENERATOR, "Producing energy to power up buildings", IMG_GENERATOR, 500, base, owner);
    }

    @Override
    public String displayDscr() {
        StringBuilder generatorDscr = new StringBuilder();
        generatorDscr.append(super.displayDscr());
        generatorDscr.append("\n\n");
        
        generatorDscr.append("Level:").append(getLevel());
        generatorDscr.append("Productivity:").append(getProductivity());
        generatorDscr.append("\n");
        
        generatorDscr.append("OUTPUT: ").append(Goods.CELLS);
        
        return generatorDscr.toString();
    }
    
    public Supplies produce() {
        return new Supplies(Goods.getGoodsByName(Goods.CELLS), getProductivity());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private int getProductivity() {
        return getLevel() * 25;
    }
    
}
