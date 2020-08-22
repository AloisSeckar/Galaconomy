package galaconomy.universe.building;

import galaconomy.universe.economy.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import java.util.*;

public class Factory extends Building {
    
    private List<Goods> input;
    private Goods output;
    
    public Factory(Base base, Player owner) {
        super(FACTORY, "Universal building for crafting goods", IMG_FACTORY, 1000, base, owner);
        output = Goods.getRandomFactoryProduct();
        setInputs(output.getInputs());
    }

    public List<Goods> getInput() {
        return input;
    }

    public Goods getOutput() {
        return output;
    }

    public void setOutput(Goods newOutput) {
        this.output = newOutput;
        if (output != null) {
            setInputs(output.getInputs());
        } else {
            input.clear();
        }
    }
    
    @Override
    public String displayDscr() {
        StringBuilder factoryDscr = new StringBuilder();
        factoryDscr.append(super.displayDscr());
        factoryDscr.append("\n\n");
        
        factoryDscr.append("Level:").append(getLevel());
        factoryDscr.append("Productivity:").append(getProductivity());
        factoryDscr.append("\n");
        
        factoryDscr.append("INPUT: ");
        input.forEach((goods) -> {
            factoryDscr.append(goods.displayName()).append(" ");
        });
        factoryDscr.append("\n");
        
        factoryDscr.append("OUTPUT: ");
        factoryDscr.append(output.displayName());
        
        return factoryDscr.toString();
    }
    
    public Supplies produce() {
        return new Supplies(output, getProductivity());
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private void setInputs(List<String> inputs) {
        input.clear();
        if (inputs != null) {
            inputs.forEach((inputName) -> {
                input.add(Goods.getGoodsByName(inputName));
            });
        }
    }
    
    private int getProductivity() {
        return getLevel() * 5;
    }
    
}
