package galaconomy.universe.building;

import galaconomy.universe.economy.Cargo;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import java.util.*;

public class Warehouse extends Building {
    
    private int capacity = 100;
    private final List<Cargo> storage = new ArrayList<>();
    
    public Warehouse(Base base, Player owner) {
        super("Warehouse", "Place for storing excessive goods", IMG_WAREHOUSE, 750, base, owner);
    }
    
    public void increaseCapacity(int increase) {
        this.capacity += increase;
    }
    
    @Override
    public String displayDscr() {
        StringBuilder warehouseDscr = new StringBuilder();
        warehouseDscr.append(super.displayDscr());
        warehouseDscr.append("\n");
        
        warehouseDscr.append("CAPACITY: ").append(capacity).append("\n\n");
        
        warehouseDscr.append("STORAGE").append("\n");
        warehouseDscr.append("----------").append("\n");
        storage.forEach((supplies) -> {
            warehouseDscr.append(supplies.displayName()).append(" x").append(supplies.getAmount());
        });
        
        return warehouseDscr.toString();
    }

    @Override
    public Cargo produce() {
        return null;
    }
    
    // TODO must allow different cargos from different owners
}
