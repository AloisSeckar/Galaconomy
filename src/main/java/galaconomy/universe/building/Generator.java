package galaconomy.universe.building;

import galaconomy.universe.economy.*;
import galaconomy.universe.map.SurfaceTile;
import galaconomy.universe.player.Player;

public class Generator extends Building {
   
    public Generator(SurfaceTile parent, Player owner) {
        super(GENERATOR, "Producing energy to power up buildings", IMG_GENERATOR, 500, parent, owner);
    }

    @Override
    public String displayDscr() {
        StringBuilder generatorDscr = new StringBuilder();
        generatorDscr.append(super.displayDscr());
        generatorDscr.append("\n\n");
        
        generatorDscr.append("Level:").append(getLevel()).append("\n");
        generatorDscr.append("Productivity:").append(getProductivity()).append("\n");
        
        generatorDscr.append("OUTPUT: ").append(Goods.CELLS);
        
        return generatorDscr.toString();
    }
    
    @Override
    public Cargo produce() {
        return new Cargo(Goods.getGoodsByName(Goods.CELLS), getProductivity(), getCurrentOwner(), this);
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private int getProductivity() {
        return getLevel() * 25;
    }
    
}
