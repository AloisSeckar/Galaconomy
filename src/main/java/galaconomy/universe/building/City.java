package galaconomy.universe.building;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.economy.Cargo;
import galaconomy.universe.map.Base;

public class City extends Building {

    public City(Base base) {
        super(base.displayName(), "", IMG_CITY, 0, base, UniverseManager.getInstance().getGLSPlayer());
    }
    
    @Override
    public String displayDscr() {
        return getParent().displayDscr();
    }

    @Override
    public Cargo produce() {
        return null;
    }
    
    // TODO handle supply/demand changes here
    public void recalcSupplies() {
        /*
        int amount = current.getCargo().getAmount();
        if (amount < 1) {
            amount = 1;
        }
        int amountDelta = rand.nextInt(amount / 10 + 1) + 1;
        if (rand.nextBoolean()) {
            current.increaseSupply(amountDelta);
            current.setPriceBuy(Math.max(current.getPriceBuy() - rand.nextInt(amountDelta), 101));
            current.setPriceSell(current.getPriceBuy() + rand.nextInt(amountDelta));
        } else {
            current.decreaseSupply(amountDelta);
            current.setPriceBuy(current.getPriceBuy() + rand.nextInt(amountDelta));
            current.setPriceSell(Math.max(current.getPriceBuy() - rand.nextInt(amountDelta), 101));
        }
        */
    }
    
}
