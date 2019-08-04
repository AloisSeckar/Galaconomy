package galaconomy.universe.economy;

import galaconomy.universe.IDisplayable;
import java.io.Serializable;
import java.util.Random;

public class Supplies implements IDisplayable, Serializable {
    
    private final Goods goods;
    
    private int amount;
    private int priceBuy;
    private int priceSell;

    public Supplies(Goods goods, int amount) {
        this.goods = goods;
        this.amount = amount;
        
        Random rand = new Random();
        this.priceBuy = rand.nextInt(1000) + 1;
        this.priceSell = this.priceBuy + rand.nextInt(500) + 1;
    }

    public Supplies(Goods goods, int amount, int priceBuy, int priceSell) {
        this.goods = goods;
        this.amount = amount;
        this.priceBuy = priceBuy;
        this.priceSell = priceSell;
    }

    public String getId() {
        return goods.displayName();
    }

    @Override
    public String displayName() {
        StringBuilder sb = new StringBuilder();
        sb.append(goods.displayName());
        sb.append(" (").append(amount).append(")");
        return sb.toString();
    }

    @Override
    public String displayDscr() {
        return goods.displayDscr();
    }

    @Override
    public String getImage() {
        return goods.getImage();
    }

    public Goods getGoods() {
        return goods;
    }

    public int getAmount() {
        return amount;
    }

    public void increaseAmount(int amount) {
        this.amount += amount;
    }

    public void decreaseAmount(int amount) {
        this.amount -= amount;
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
