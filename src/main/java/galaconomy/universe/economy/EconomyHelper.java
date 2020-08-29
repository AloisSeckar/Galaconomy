package galaconomy.universe.economy;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.map.*;

public class EconomyHelper {
    
    public static Supplies findLowestPrice(Base base) {
        Supplies ret = null;
        
        if (base != null) {
            for (Supplies current : base.getSupplies()) {
                if (ret == null || current.getPriceSell() < ret.getPriceSell()) {
                    ret = current;
                }
            }
        }
        
        return ret;
    }
    
    public static Base findBestPrice(Goods goods) {
        Base ret = null;
        Supplies bestOffer = null;
        
        if (goods != null) {
            String goodsName = goods.displayName();
            for (Star currentStar : UniverseManager.getInstance().getStars().values()) {
                for (Base currentBase : currentStar.getBases()) {
                    Supplies currentSupply = currentBase.findSupplies(goodsName);
                    if (currentSupply != null) {
                        if (bestOffer == null || currentSupply.getPriceBuy() > bestOffer.getPriceBuy()) {
                            ret = currentBase;
                            bestOffer = currentSupply;
                        }
                    }
                }
            }
        }
        
        return ret;
    }

}
