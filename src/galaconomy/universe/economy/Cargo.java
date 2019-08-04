package galaconomy.universe.economy;

import galaconomy.universe.IDisplayable;
import galaconomy.universe.systems.Star;
import java.io.Serializable;

public class Cargo implements IDisplayable, Serializable {
    
    private final Goods goods;
    
    private final int amount;
    
    private final int price;
    
    private final Star origin;
    
    private final long purchased;

    public Cargo(Goods goods, int amount, int price, Star origin, long purchased) {
        this.goods = goods;
        this.amount = amount;
        this.price = price;
        this.origin = origin;
        this.purchased = purchased;
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
        StringBuilder sb = new StringBuilder();
        sb.append("Bought on ").append(origin.displayName());
        sb.append(" for ").append(price).append(" credits");
        sb.append(" (ST: ").append(purchased).append(")");
        return sb.toString();
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
}
