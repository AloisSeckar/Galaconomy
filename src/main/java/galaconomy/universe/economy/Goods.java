package galaconomy.universe.economy;

import galaconomy.constants.Constants;
import galaconomy.universe.IDisplayable;
import java.io.Serializable;
import java.util.*;

public class Goods implements IDisplayable, Serializable {
    
    private static final String FOLDER = Constants.GOODS_FOLDER;
    
    public static final String ARMS = "Arms";
    public static final String CELLS = "Energy Cells";
    public static final String CHIPS = "Chips";
    public static final String DRUGS = "Drugs";
    public static final String FOOD = "Food";
    public static final String GAS = "Gas";
    public static final String METAL = "Metal";
    public static final String ORE = "Ore";
    public static final String ORGANICS = "Organics";
    public static final String SILICA = "Silica";
    
    private final String name;
    private final String dscr; 
    private final String img; 
    private final boolean raw; 
    private final List<String> inputs; 

    public Goods(String name, String dscr, String img, boolean raw, List<String> inputs) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        this.raw = raw;
        this.inputs = inputs;
    }
    
    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        return dscr;
    }

    @Override
    public String getImage() {
        return img;
    }

    public boolean isRaw() {
        return raw;
    }

    public List<String> getInputs() {
        return inputs;
    }
    
    public static List<Goods> getAvailableGoods() {
        List<Goods> ret = new ArrayList<>();
        
        ret.add(getGoodsByName(ARMS));
        ret.add(getGoodsByName(CELLS));
        ret.add(getGoodsByName(CHIPS));
        ret.add(getGoodsByName(DRUGS));
        ret.add(getGoodsByName(FOOD));
        ret.add(getGoodsByName(GAS));
        ret.add(getGoodsByName(METAL));
        ret.add(getGoodsByName(ORE));
        ret.add(getGoodsByName(ORGANICS));
        ret.add(getGoodsByName(SILICA));
        
        return ret;
    }
    
    public static List<Goods> getRawMaterials() {
        List<Goods> ret = new ArrayList<>();
        
        ret.add(getGoodsByName(GAS));
        ret.add(getGoodsByName(ORE));
        ret.add(getGoodsByName(ORGANICS));
        ret.add(getGoodsByName(SILICA));
        
        return ret;
    }
    
    public static List<Goods> getFactoryProducts() {
        List<Goods> ret = new ArrayList<>();
        
        ret.add(getGoodsByName(ARMS));
        ret.add(getGoodsByName(METAL));
        ret.add(getGoodsByName(CHIPS));
        ret.add(getGoodsByName(DRUGS));
        
        return ret;
    }
    
    public static Goods getGoodsByName(String name) {
        Goods ret;
        
        switch (name) {
            case METAL:
                ret = new Goods(METAL, "High quality alloy", FOLDER + "metal.jpg", false, Arrays.asList(ORE));
                break;
            case ARMS:
                ret = new Goods(ARMS, "Military class weapons and stuff", FOLDER + "arms.jpg", false, Arrays.asList(ORE, SILICA));
                break;
            case FOOD:
                ret = new Goods(FOOD, "Basic foods", FOLDER + "food.jpg", true, null);
                break;
            case GAS:
                ret = new Goods(GAS, "Used in chemistry", FOLDER + "gas.jpg", true, null);
                break;
            case CHIPS:
                ret = new Goods(CHIPS, "Advanced computer chips", FOLDER + "chips.jpg", false, Arrays.asList(METAL, SILICA));
                break;
            case DRUGS:
                ret = new Goods(DRUGS, "Cure for every possible disease", FOLDER + "drugs.jpg", false, Arrays.asList(SILICA, ORGANICS));
                break;
            case CELLS:
                ret = new Goods(CELLS, "Cure for every possible disease", FOLDER + "cells.jpg", true, null);
                break;
            case ORE:
                ret = new Goods(ORE, "To be processed into metal", FOLDER + "ore.jpg", true, null);
                break;
            case ORGANICS:
                ret = new Goods(ORGANICS, "Used in pharmacy", FOLDER + "organics.jpg", true, null);
                break;
            case SILICA:
                ret = new Goods(SILICA, "Various usage", FOLDER + "silica.jpg", true, null);
                break;
            default:
                ret = new Goods("Goods", "Universal whatever...", FOLDER + "goods.jpg", false, null);
        }
        
        return ret;
    }
    
    public static Goods getRandomGoods() {
        Goods ret;
        
        Random rand = new Random();
        switch (rand.nextInt() % 10) {
            case 9:
                ret = getGoodsByName(GAS);
                break;
            case 8:
                ret = getGoodsByName(ORGANICS);
                break;
            case 7:
                ret = getGoodsByName(SILICA);
                break;
            case 6:
                ret = getGoodsByName(ORE);
                break;
            case 5:
                ret = getGoodsByName(CELLS);
                break;
            case 4:
                ret = getGoodsByName(ARMS);
                break;
            case 3:
                ret = getGoodsByName(FOOD);
                break;
            case 2:
                ret = getGoodsByName(CHIPS);
                break;
            case 1:
                ret = getGoodsByName(DRUGS);
                break;
            default:
                ret = getGoodsByName(METAL);
        }
        
        return ret;
    }

}
