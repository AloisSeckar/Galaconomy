package galaconomy.universe;

import galaconomy.universe.player.Player;
import java.util.List;

// allows entity to be traded between two players
public interface ITradable {
    
    public int getPrice();
    
    public void setPrice(int newPrice);
    
    public Player getCurrentOwner();

    public List<Player> getOwners();
    
    public void changeOwner(Player newOwner);
    
    public default String getIdentity() {
        if (this instanceof IDisplayable) {
            return ((IDisplayable) this).displayName();
        } else {
            return this.toString();
        }
    }
    
}
