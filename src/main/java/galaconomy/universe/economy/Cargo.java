package galaconomy.universe.economy;

import galaconomy.universe.IDisplayable;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.map.Base;
import java.io.Serializable;

public class Cargo implements IDisplayable, Serializable {
    
    private final Goods goods;
    
    private final int amount;
    
    private final int price;
    
    private final Base origin;
    
    private final long purchased;
    
    public Cargo(Goods goods, int amount, int price, Base origin) {
        this.goods = goods;
        this.amount = amount;
        this.price = price;
        this.origin = origin;
        this.purchased = UniverseManager.getInstance().getStellarTime();
    }

    public Cargo(Goods goods, int amount, int price, Base origin, long purchased) {
        this.goods = goods;
        this.amount = amount;
        this.price = price;
        this.origin = origin;
        this.purchased = purchased;
    }

    // TODO rename to make it more obvious...
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

    public int getPrice() {
        return price;
    }
}
