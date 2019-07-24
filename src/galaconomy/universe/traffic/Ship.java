package galaconomy.universe.traffic;

import galaconomy.universe.player.Player;
import java.util.*;

public class Ship {
    
    private final String name;
    private final String dscr; 
    private final String img; 
    private final double speed; 
    
    private final List<Player> owners = new ArrayList<>();
    private final List<Route> routes = new ArrayList<>();

    public Ship(String name, String dscr, String img, double speed) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        this.speed = speed;
    }

    public String getName() {
        return name;
    }

    public String getDscr() {
        return dscr;
    }

    public String getImg() {
        return img;
    }

    public double getSpeed() {
        return speed;
    }

    public List<Player> getOwners() {
        return Collections.unmodifiableList(owners);
    }
    
    public void addOwner(Player newOwner) {
        owners.add(newOwner);
    }

    public List<Route> getRoutes() {
        return Collections.unmodifiableList(routes);
    }
    
    public void addRoute(Route newRoute) {
        routes.add(newRoute);
    }
}
