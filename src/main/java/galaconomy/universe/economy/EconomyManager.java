package galaconomy.universe.economy;

import galaconomy.universe.map.Base;
import java.util.*;

public class EconomyManager {
    
    public static void recalcSupplies(List<Base> bases) {
        Random rand = new Random();
        for (Base base : bases) {
            for (Supplies current : base.getSupplies().values()) {
                
                int amount = current.getAmount();
                if (amount < 1) {
                    amount = 1;
                }
                int amountDelta = rand.nextInt(amount / 10 + 1) + 1;
                if (rand.nextBoolean()) {
                    current.increaseAmount(amountDelta);
                    current.setPriceBuy(Math.max(current.getPriceBuy() - rand.nextInt(amountDelta), 101));
                    current.setPriceSell(current.getPriceBuy() + rand.nextInt(amountDelta));
                } else {
                    current.decreaseAmount(amountDelta);
                    current.setPriceBuy(current.getPriceBuy() + rand.nextInt(amountDelta));
                    current.setPriceSell(Math.max(current.getPriceBuy() - rand.nextInt(amountDelta), 101));
                }
            }
        }
    }

}
