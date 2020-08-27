package galaconomy.universe.economy;

import galaconomy.universe.IDisplayable;
import java.io.Serializable;
import java.util.Random;

public class Supplies implements IDisplayable, Serializable {
    
    private final Cargo cargo;
    
    private int priceBuy;
    private int priceSell;

    public Supplies(Cargo cargo) {
        this.cargo = cargo;
        
        Random rand = new Random();
        this.priceBuy = rand.nextInt(900) + 101;
        this.priceSell = this.priceBuy + rand.nextInt(500) + 101;
    }

    public Supplies(Cargo cargo, int priceBuy, int priceSell) {
        this.cargo = cargo;
        this.priceBuy = priceBuy;
        this.priceSell = priceSell;
    }

    public String getIdentity() {
        return cargo.displayName();
    }

    @Override
    public String displayName() {
        return cargo.displayName();
    }

    @Override
    public String displayDscr() {
        return cargo.displayDscr();
    }

    @Override
    public String getImage() {
        return cargo.getImage();
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void increaseSupply(int increase) {
        cargo.setAmount(cargo.getAmount() + increase);
    }

    public void decreaseSupply(int decrease) {
        cargo.setAmount(Math.min(cargo.getAmount() - decrease, 0));
    }

    public int getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(int priceBuy) {
        this.priceBuy = priceBuy;
    }

    public int getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(int priceSell) {
        this.priceSell = priceSell;
    }

}
