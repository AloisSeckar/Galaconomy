package galaconomy.universe.building;

import galaconomy.universe.economy.Goods;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;

public class Farm extends Building {
    
    private final Goods output;
    
    public Farm(Base base, Player owner) {
        super("Farm", "Food is being produced here", IMG_FARM, 600, base, owner);
        this.output = Goods.getGoodsByName("Food");
    }

    public Goods getOutput() {
        return output;
    }
    
    @Override
    public String displayDscr() {
        StringBuilder farmDscr = new StringBuilder();
        farmDscr.append(super.displayDscr());
        farmDscr.append("\n\n");
        
        farmDscr.append("OUTPUT:").append("\n");
        farmDscr.append(output.displayName());
        
        return farmDscr.toString();
    }
    
}