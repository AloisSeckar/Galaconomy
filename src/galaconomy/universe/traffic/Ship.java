package galaconomy.universe.traffic;

import galaconomy.universe.IDisplayable;
import galaconomy.universe.player.Player;
import galaconomy.universe.systems.Star;
import galaconomy.utils.DisplayUtils;
import java.util.*;

public class Ship implements IDisplayable {
    
    private final String name;
    private final String dscr; 
    private final String img; 
    private final double speed; 
    
    private boolean idle;
    private Star location;
    
    private Player currentOwner = null;
    private final List<Player> owners = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();

    public Ship(String name, String dscr, String img, double speed, Star location) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        this.speed = speed;
        this.location = location;
        
        this.idle = true;
    }
    
    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        StringBuilder shipDscr = new StringBuilder();
        
        shipDscr.append(dscr).append("\n\n");
                
        shipDscr.append("Owner: ");
        if (currentOwner != null) {
            shipDscr.append(currentOwner.displayName());
        } else {
            shipDscr.append("N/A");
        }
        shipDscr.append("\n");
        
        shipDscr.append("Speed: ").append(DisplayUtils.formatDouble(speed)).append("\n");
        
        return shipDscr.toString();
    }

    @Override
    public String getImage() {
        return img;
    }

    public double getSpeed() {
        return speed;
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
}
