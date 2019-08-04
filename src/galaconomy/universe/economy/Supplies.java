package galaconomy.universe.economy;

import galaconomy.universe.IDisplayable;
import java.io.Serializable;

public class Supplies implements IDisplayable, Serializable {
    
    private final Goods goods;
    
    private int amount;
    private Integer priceBuy;
    private Integer priceSell;

    public Supplies(Goods goods, int amount) {
        this.goods = goods;
        this.amount = amount;
    }

    public Supplies(Goods goods, int amount, Integer priceBuy, Integer priceSell) {
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

    public Integer getPriceBuy() {
        return priceBuy;
    }

    public void setPriceBuy(Integer priceBuy) {
        this.priceBuy = priceBuy;
    }

    public Integer getPriceSell() {
        return priceSell;
    }

    public void setPriceSell(Integer priceSell) {
        this.priceSell = priceSell;
    }

}
