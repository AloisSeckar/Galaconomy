package galaconomy.universe.economy;

import galaconomy.universe.*;
import galaconomy.universe.player.Player;
import java.io.Serializable;

public class Cargo implements IDisplayable, Serializable {
    
    private final Goods goods;
    
    private int amount;
    
    private Player owner;
    private IStorage location;
    
    public Cargo(Goods goods, int amount, Player owner, IStorage location) {
        this.goods = goods;
        this.amount = amount;
        this.owner = owner;
        this.location = location;
    }

    public String getIdentity() {
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
        sb.append("Owner: ").append(owner.displayName()).append("\n");
        sb.append("Stored at: ").append(location.getStorageIdentity()).append("\n");
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

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public IStorage getLocation() {
        return location;
    }

    public void setLocation(IStorage location) {
        this.location = location;
    }
}
