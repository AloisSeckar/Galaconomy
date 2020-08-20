package galaconomy.universe.economy;

import galaconomy.constants.Constants;
import galaconomy.universe.IDisplayable;
import java.io.Serializable;
import java.util.*;

public class Goods implements IDisplayable, Serializable {
    
    private final String name;
    private final String dscr; 
    private final String img; 

    public Goods(String name, String dscr, String img) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
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
    
    
    public static List<Goods> getAvailableGoods() {
        List<Goods> ret = new ArrayList<>();
        
        // TODO make types of goods as constants
        ret.add(getGoodsByName("Metal"));
        ret.add(getGoodsByName("Arms"));
        ret.add(getGoodsByName("Food"));
        ret.add(getGoodsByName("Chips"));
        ret.add(getGoodsByName("Drugs"));
        
        return ret;
    }
    
    public static Goods getGoodsByName(String name) {
        Goods ret;
        
        switch (name) {
            case "Metal":
                ret = new Goods("Metal", "High quality alloy", Constants.GOODS_FOLDER + "metal.jpg");
                break;
            case "Arms":
                ret = new Goods("Arms", "Military class weapons and stuff", Constants.GOODS_FOLDER + "arms.jpg");
                break;
            case "Food":
                ret = new Goods("Food", "Basic foods", Constants.GOODS_FOLDER + "food.jpg");
                break;
            case "Chips":
                ret = new Goods("Chips", "Advanced computer chips", Constants.GOODS_FOLDER + "chips.jpg");
                break;
            case "Drugs":
                ret = new Goods("Drugs", "Cure for every possible disease", Constants.GOODS_FOLDER + "drugs.jpg");
                break;
            default:
                ret = new Goods("Goods", "Universal whatever...", Constants.GOODS_FOLDER + "goods.jpg");
        }
        
        return ret;
    }
    
    public static Goods getRandomGoods() {
        Goods ret;
        
        Random rand = new Random();
        switch (rand.nextInt() % 5) {
            case 4:
                ret = getGoodsByName("Arms");
                break;
            case 3:
                ret = getGoodsByName("Food");
                break;
            case 2:
                ret = getGoodsByName("Chips");
                break;
            case 1:
                ret = getGoodsByName("Drugs");
                break;
            default:
                ret = getGoodsByName("Metal");
        }
        
        return ret;
    }

}
