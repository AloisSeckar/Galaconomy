package galaconomy.universe.economy;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.map.Star;

public class EconomyHelper {
    
    public static Supplies findLowestPrice(Star star) {
        Supplies ret = null;
        
        if (star != null) {
            for (Supplies current : star.getSupplies().values()) {
                if (ret == null || current.getPriceSell() < ret.getPriceSell()) {
                    ret = current;
                }
            }
        }
        
        return ret;
    }
    
    public static Star findBestPrice(Goods goods) {
        Star ret = null;
        Supplies bestOffer = null;
        
        if (goods != null) {
            String goodsName = goods.displayName();
            for (Star currentStar : UniverseManager.getInstance().getStars().values()) {
                Supplies currentSupply = currentStar.findSupplies(goodsName);
                if (currentSupply != null) {
                    if (bestOffer == null || currentSupply.getPriceBuy() > bestOffer.getPriceBuy()) {
                        ret = currentStar;
                        bestOffer = currentSupply;
                    }
                }
            }
        }
        
        return ret;
    }

}
