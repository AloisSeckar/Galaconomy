package galaconomy.universe.building;

import galaconomy.universe.economy.Goods;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import java.util.Arrays;
import java.util.List;

public class Factory extends Building {
    
    private List<Goods> input;
    private Goods output;
    
    public Factory(Base base, Player owner) {
        super("Factory", "Universal building for crafting goods", IMG_FACTORY, 1000, base, owner);
        this.input = Arrays.asList(Goods.getRandomGoods());
        this.output = Goods.getRandomGoods();
    }

    public List<Goods> getInput() {
        return input;
    }

    public void setInput(List<Goods> input) {
        this.input = input;
    }

    public Goods getOutput() {
        return output;
    }

    public void setOutput(Goods output) {
        this.output = output;
    }
    
    @Override
    public String displayDscr() {
        StringBuilder factoryDscr = new StringBuilder();
        factoryDscr.append(super.displayDscr());
        factoryDscr.append("\n\n");
        
        factoryDscr.append("INPUT:").append("\n");
        input.forEach((goods) -> {
            factoryDscr.append(goods.displayName()).append("\n");
        });
        factoryDscr.append("\n");
        
        factoryDscr.append("OUTPUT:").append("\n");
        factoryDscr.append(output.displayName());
        
        return factoryDscr.toString();
    }
    
}
