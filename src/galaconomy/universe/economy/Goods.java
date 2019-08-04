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
        
        ret.add(new Goods("Metal", "High quality alloy", Constants.GOODS_FOLDER + "metal.jpg"));
        ret.add(new Goods("Arms", "Military class weapons and stuff", Constants.GOODS_FOLDER + "arms.jpg"));
        ret.add(new Goods("Food", "Basic foods", Constants.GOODS_FOLDER + "food.jpg"));
        ret.add(new Goods("Chips", "Advanced computer chips", Constants.GOODS_FOLDER + "chips.jpg"));
        ret.add(new Goods("Drugs", "Cure for every possible disease", Constants.GOODS_FOLDER + "drugs.jpg"));
        
        return ret;
    }

}
