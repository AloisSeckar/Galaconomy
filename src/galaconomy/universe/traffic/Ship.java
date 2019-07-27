package galaconomy.universe.traffic;

import galaconomy.universe.*;
import galaconomy.universe.player.Player;
import galaconomy.universe.systems.Star;
import galaconomy.utils.DisplayUtils;
import java.io.Serializable;
import java.util.*;

public class Ship implements IDisplayable, Serializable {
    
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
    
    private Star location;
    
    private double mileage = 0;
    private boolean idle = true;
    private Player currentOwner = null;
    
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
        
        this.location = location;
    }
    
    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        StringBuilder shipDscr = new StringBuilder();
        
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

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
    }

    public Star getLocation() {
        return location;
    }

    public void setLocation(Star location) {
        this.location = location;
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
