package galaconomy.universe.building;

import galaconomy.universe.economy.Goods;
import galaconomy.universe.map.Base;

public class Mine extends Building {
    
    private Goods output;
    
    public Mine(Base base) {
        super("Mine", "Universal platform for harvesting resources", IMG_MINE, 850, base);
        this.output = Goods.getGoodsByName("Metal");
    }

    public Goods getOutput() {
        return output;
    }

    public void setOutput(Goods output) {
        this.output = output;
    }
    
    @Override
    public String displayDscr() {
        StringBuilder mineDscr = new StringBuilder();
        mineDscr.append(super.displayDscr());
        mineDscr.append("\n");
        
        mineDscr.append("OUTPUT:").append("\n");
        mineDscr.append(output.displayName());
        
        return mineDscr.toString();
    }
    
}
