package galaconomy.universe;

import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import galaconomy.utils.result.ResultBean;
import java.util.List;

public interface IStorage {
    
    public String getStorageIdentity();
    
    public int getStorageCapacity();
    
    public Player getStorageOwner();
    
    public List<Cargo> getCurrentCargo();
    
    public ResultBean storeCargo(Cargo cargo);
    
    public ResultBean withdrawCargo(Goods goods, int amount);
    
    public Cargo get(String key);
    
    public Cargo put(String key, Cargo cargo);
    
    public Cargo remove(String key);
    
}
