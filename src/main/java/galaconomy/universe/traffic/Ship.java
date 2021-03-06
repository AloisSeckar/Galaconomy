package galaconomy.universe.traffic;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import galaconomy.universe.map.*;
import galaconomy.utils.DisplayUtils;
import galaconomy.utils.StorageUtils;
import galaconomy.utils.result.ResultBean;
import java.io.Serializable;
import java.util.*;
import org.slf4j.*;

public class Ship implements IDisplayable, ITradable, IStorage, Serializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(Ship.class);
    
    private final String name;
    private final String shipClass;
    private final String dscr; 
    private final String img;
    
    private int price;
    private Player currentOwner = null;
    private final List<Player> owners = new ArrayList<>();
    
    private int upkeep;
    private int hull;
    private double riftSpeed;
    private double pulseSpeed;
    
    private final long comissioned;
    
    private Star currentSystem;
    private Base currentBase;
    
    private boolean idle = true;
    
    private int cargoCapacity;
    private final HashMap<String, Cargo> cargoList = new HashMap<>();
    
    private final List<Travel> travels = new ArrayList<>();

    public Ship(String name, ShipClass shipClass, Base location) {
        this.name = name;
        
        this.shipClass = shipClass.getName();
        this.dscr = shipClass.getDscr();
        this.img = shipClass.getImage();
        
        this.price = shipClass.getPrice();
        this.upkeep = shipClass.getUpkeep();
        this.hull = shipClass.getHull();
        this.cargoCapacity = shipClass.getCargo();
        this.riftSpeed = shipClass.getRiftSpeed();
        this.pulseSpeed = shipClass.getPulseSpeed();
        
        this.comissioned = UniverseManager.getInstance().getStellarTime();
        
        this.currentBase = location;
        this.currentSystem = (Star) location.getParent();
    }
    
    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        StringBuilder shipDscr = new StringBuilder();
        
        shipDscr.append("CARGO").append("\n");
        shipDscr.append("----------").append("\n");
        cargoList.values().forEach(cargo -> {
            shipDscr.append(cargo.displayName()).append("\n");
        });
        shipDscr.append("\n");
        
        shipDscr.append("INFO").append("\n");
        shipDscr.append("----------").append("\n");
        
        shipDscr.append("Owner: ");
        shipDscr.append(currentOwner != null ? currentOwner.displayName() : Constants.NONE);
        shipDscr.append("\n");
        
        shipDscr.append("Class: ").append(shipClass).append("\n");
        shipDscr.append("Upkeep: ").append(upkeep).append("\n");
        shipDscr.append("Hull: ").append(hull).append("\n");
        shipDscr.append("Cargo: ").append(cargoCapacity).append("\n");
        shipDscr.append("Speed: ").append(DisplayUtils.formatDouble(riftSpeed)).append("\n\n");
        
        shipDscr.append(dscr);
           
        return shipDscr.toString();
    }

    @Override
    public String getImage() {
        return img;
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Player getCurrentOwner() {
        return currentOwner;
    }

    @Override
    public List<Player> getOwners() {
        return owners;
    }
    
    @Override
    public void changeOwner(Player newOwner) {
        owners.add(0, newOwner);
        currentOwner = newOwner;
    }
    
    @Override
    public String getStorageIdentity() {
        return displayName();
    }

    @Override
    public int getStorageCapacity() {
        return cargoCapacity;
    }
    
    @Override
    public Player getStorageOwner() {
        return getCurrentOwner();
    }

    @Override
    public List<Cargo> getCurrentCargo() {
        List<Cargo> ret = new ArrayList<>();
        ret.addAll(cargoList.values());
        return ret;
    }
    
    @Override
    public ResultBean storeCargo(Cargo cargo) {
        return StorageUtils.storeCargo(cargo, this);
    }

    @Override
    public ResultBean withdrawCargo(Goods goods, int amount) {
        return StorageUtils.withdrawCargo(goods, amount, this);
    }

    @Override
    public Cargo get(String key) {
         return cargoList.get(key);
    }

    @Override
    public Cargo put(String key, Cargo cargo) {
         return cargoList.put(key, cargo);
    }

    @Override
    public Cargo remove(String key) {
         return cargoList.remove(key);
    }

    public List<Travel> getTravels() {
        return travels;
    }
    
    public void addTravel(Travel newTravel) {
        travels.add(0, newTravel);
    }
    
    public String performPurchase(Goods goods, int amount, int price) {
        String ret = TradeHelper.checkPurchase(this, goods, amount, price);
        
        if (ret.isEmpty()) {
            long totalPrice = amount * price;
            Cargo newCargo  = new Cargo(goods, amount, this.currentOwner, this);
            
            currentBase.performSale(goods, amount);
            currentOwner.spendCredits(totalPrice);
            cargoList.put(goods.getIdentity(), newCargo);
            
            LOG.info(name + " purchased " + newCargo.displayName() + " at " + currentBase.displayName() + " for " + totalPrice + " credits");
        }
        
        return ret;
    }
    
    public String performSale(Cargo cargo, int price) {
        String ret = TradeHelper.checkSale(this, cargo, price);
        
        if (ret.isEmpty()) {
            int amount = cargo.getAmount();
            long totalPrice = amount * price;
            
            currentBase.performPurchase(cargo);
            currentOwner.earnCredits(totalPrice);
            cargoList.remove(cargo.getIdentity());
            
            LOG.info(name + " sold " + cargo.displayName() + " at " + currentBase.displayName() + " for " + totalPrice + " credits");
            
            ret = "OK";
        }
        
        return ret;
    }

    public String getShipClass() {
        return shipClass;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public Star getCurrentSystem() {
        return currentSystem;
    }

    public void setCurrentSystem(Star currentSystem) {
        this.currentSystem = currentSystem;
    }

    public Base getCurrentBase() {
        return currentBase;
    }

    public void setCurrentBase(Base currentBase) {
        this.currentBase = currentBase;
    }

    public long getComissioned() {
        return comissioned;
    }

    public int getUpkeep() {
        return upkeep;
    }

    public void setUpkeep(int upkeep) {
        this.upkeep = upkeep;
    }

    public int getHull() {
        return hull;
    }

    public void setHull(int hull) {
        this.hull = hull;
    }

    public int getCargoCapacity() {
        return cargoCapacity;
    }

    public void setCargoCapacity(int cargoCapacity) {
        this.cargoCapacity = cargoCapacity;
    }

    public double getRiftSpeed() {
        return riftSpeed;
    }

    public void setRiftSpeed(double riftSpeed) {
        this.riftSpeed = riftSpeed;
    }

    public double getPulseSpeed() {
        return pulseSpeed;
    }

    public void setPulseSpeed(double pulseSpeed) {
        this.pulseSpeed = pulseSpeed;
    }
    
    @Override
    public String toString() {
        return displayName();
    }
    
    // TODO must allow different cargos from different owners
}
