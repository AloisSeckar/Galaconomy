package galaconomy.universe.economy;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.player.Player;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Cargo implements IDisplayable, Serializable {
    
    private final UUID cargoID = UUID.randomUUID();
    
    private final Goods goods;
    
    private int amount;
    
    private Player owner;
    private IStorage location;
    
    public Cargo(Goods goods, int amount) {
        this(goods, amount, null, null);
    }
    
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
        sb.append("Owner: ").append(owner != null ? owner.displayName() : Constants.NONE).append("\n");
        sb.append("Stored at: ").append(location != null ? location.getStorageIdentity() : Constants.NONE).append("\n");
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

    public void increaseAmount(int increase) {
        setAmount(getAmount() + increase);
    }

    public void decreaseAmount(int decrease) {
        setAmount(Math.max(getAmount() - decrease, 0));
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode(this.cargoID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cargo other = (Cargo) obj;
        return Objects.equals(this.cargoID, other.cargoID);
    }
    
}
