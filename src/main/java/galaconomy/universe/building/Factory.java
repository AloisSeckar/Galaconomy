package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.economy.*;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import java.util.*;

public class Factory extends Building {
    
    private final List<Goods> input = new ArrayList<>();
    private Goods output;
    
    public Factory(Base base, Player owner) {
        super(FACTORY, "Universal building for crafting goods", IMG_FACTORY, 1000, base, owner);
        output = Goods.getRandomFactoryProduct();
        setInputs(output.getInputs());
    }
    
    @Override
    public String displayDscr() {
        StringBuilder factoryDscr = new StringBuilder();
        factoryDscr.append(super.displayDscr());
        factoryDscr.append("\n\n");
        
        factoryDscr.append("Level:").append(getLevel()).append("\n");
        factoryDscr.append("Productivity:").append(getProductivity()).append("\n");
        
        factoryDscr.append("INPUT: ");
        input.forEach((goods) -> {
            factoryDscr.append(goods.displayName()).append(" ");
        });
        factoryDscr.append("\n");
        
        factoryDscr.append("OUTPUT: ");
        factoryDscr.append(output != null ? output.displayName() : Constants.NONE);
        
        return factoryDscr.toString();
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
    public Cargo produce() {
        // TODO check if capacity allows it
        // TODO dont sell automatically
        return new Cargo(output, getProductivity(), getCurrentOwner(), this);
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
