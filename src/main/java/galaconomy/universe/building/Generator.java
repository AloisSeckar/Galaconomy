package galaconomy.universe.building;

import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import java.util.Random;

public class Generator extends Building {
    
    private int output;
    
    public Generator(Base base, Player owner) {
        super("Generator", "Producing energy to power up buildings", IMG_GENERATOR, 500, base, owner);
        this.output = new Random().nextInt(25);
    }

    public void increaseOutput(int increase) {
        this.output += increase;
    }
    
    @Override
    public String displayDscr() {
        StringBuilder generatorDscr = new StringBuilder();
        generatorDscr.append(super.displayDscr());
        generatorDscr.append("\n\n");
        
        generatorDscr.append("OUTPUT: ").append(output);
        
        return generatorDscr.toString();
    }
    
}
