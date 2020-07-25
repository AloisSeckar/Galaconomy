package galaconomy.universe.traffic;

import galaconomy.universe.*;
import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import galaconomy.universe.map.Star;
import galaconomy.utils.DisplayUtils;
import java.io.Serializable;
import java.util.*;
import org.slf4j.*;

public class Ship implements IDisplayable, Serializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(Ship.class);
    
    private final String name;
    private final String shipClass;
    private final String dscr; 
    private final String img;
    
    private final int price;
    private int upkeep;
    
    private int hull;
    private int cargo;
    private double speed;
    
    private final long comissioned;
    
    private Star currentLocation;
    
    private double mileage = 0;
    private boolean idle = true;
    private Player currentOwner = null;
    
    private final List<Cargo> cargoList = new ArrayList<>();
    
    private final List<Player> owners = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();

    public Ship(String name, ShipClass shipClass, Star location) {
        this.name = name;
        
        this.shipClass = shipClass.getName();
        this.dscr = shipClass.getDscr();
        this.img = shipClass.getImage();
        
        this.price = shipClass.getPrice();
        this.upkeep = shipClass.getUpkeep();
        this.hull = shipClass.getHull();
        this.cargo = shipClass.getCargo();
        this.speed = shipClass.getSpeed();
        
        this.comissioned = UniverseManager.getInstance().getStellarTime();
        
        this.currentLocation = location;
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
        for (Cargo goods : cargoList) {
            shipDscr.append(goods.displayName()).append("\n");
        }
        shipDscr.append("\n");
        
        shipDscr.append("INFO").append("\n");
        shipDscr.append("----------").append("\n");
        
        shipDscr.append("Owner: ");
        if (currentOwner != null) {
            shipDscr.append(currentOwner.displayName());
        } else {
            shipDscr.append("N/A");
        }
        shipDscr.append("\n");
        
        shipDscr.append("Class: ").append(shipClass).append("\n");
        shipDscr.append("Upkeep: ").append(upkeep).append("\n");
        shipDscr.append("Hull: ").append(hull).append("\n");
        shipDscr.append("Cargo: ").append(cargo).append("\n");
        shipDscr.append("Speed: ").append(DisplayUtils.formatDouble(speed)).append("\n\n");
        
        shipDscr.append(dscr);
           
        return shipDscr.toString();
    }

    @Override
    public String getImage() {
        return img;
    }

    public Player getCurrentOwner() {
        return currentOwner;
    }

    public List<Player> getOwners() {
        return owners;
    }
    
    public void addOwner(Player newOwner) {
        owners.add(0, newOwner);
        currentOwner = newOwner;
    }

    public List<Route> getRoutes() {
        return routes;
    }
    
    public void addRoute(Route newRoute) {
        routes.add(0, newRoute);
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }
    
    public String performPurchase(Goods goods, int amount, int price) {
        String ret = TradeHelper.checkPurchase(this, goods, amount, price);
        
        if (ret.isEmpty()) {
            long totalPrice = amount * price;
            Cargo newCargo  = new Cargo(goods, amount, price, currentLocation, UniverseManager.getInstance().getStellarTime());
            
            currentLocation.performSale(goods, amount);
            currentOwner.spendCredits(totalPrice);
            cargoList.add(newCargo);
            
            LOG.info(name + " purchased " + newCargo.displayName() + " at " + currentLocation.displayName() + " for " + totalPrice + " credits");
        }
        
        return ret;
    }
    
    public String performSale(Cargo cargo, int price) {
        String ret = TradeHelper.checkSale(this, cargo, price);
        
        if (ret.isEmpty()) {
            int amount = cargo.getAmount();
            long totalPrice = amount * price;
            
            currentLocation.performPurchase(cargo);
            currentOwner.earnCredits(totalPrice);
            cargoList.remove(cargo);
            
            LOG.info(name + " sold " + cargo.displayName() + " at " + currentLocation.displayName() + " for " + totalPrice + " credits");
        }
        
        return ret;
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public Star getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Star currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getPrice() {
        return price;
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

    public int getCargo() {
        return cargo;
    }

    public void setCargo(int cargo) {
        this.cargo = cargo;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    
    public double getMileage() {
        return mileage;
    }

    public void increaseMileage(double distanceElapsed) {
        this.mileage += mileage;
    }
    
    @Override
    public String toString() {
        return displayName();
    }
}
