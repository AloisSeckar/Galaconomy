package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.economy.Goods;
import galaconomy.universe.map.Base;

public class Factory extends Building {
    
    private Goods output;
    
    public Factory(Base base) {
        super("Factory", "Universal building for harvesting resources and crafting goods", Constants.BUILDINGS_FOLDER + "factory.png", base);
        this.output = Goods.getRandomGoods();
    }

    public void setOutput(Goods output) {
        this.output = output;
    }
    
    @Override
    public String displayDscr() {
        StringBuilder factoryDscr = new StringBuilder();
        factoryDscr.append(super.displayDscr());
        factoryDscr.append("\n");
        
        factoryDscr.append("PRODUCTION").append("\n");
        factoryDscr.append("----------").append("\n");
        factoryDscr.append(output.displayName());
        
        return factoryDscr.toString();
    }
    
}
