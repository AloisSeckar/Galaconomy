package galaconomy.universe.economy;

import galaconomy.universe.map.Star;
import java.util.*;

public class EconomyManager {
    
    public static void recalcSupplies(Collection<Star> stars) {
        Random rand = new Random();
        for (Star star : stars) {
            for (Supplies current : star.getSupplies().values()) {
                
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
