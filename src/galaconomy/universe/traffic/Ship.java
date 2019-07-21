package galaconomy.universe.traffic;

import java.util.*;

public class Ship {
    
    private final String name;
    private final String dscr; 
    private final String img; 
    private final double speed; 
    
    private final List<String> owners = new ArrayList<>();
    private final List<ShipRoute> routes = new ArrayList<>();

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

    public List<String> getOwners() {
        return Collections.unmodifiableList(owners);
    }

    public List<ShipRoute> getRoutes() {
        return Collections.unmodifiableList(routes);
    }
    
    public void addOwner(String newOwner) {
        owners.add(newOwner);
    }
    
    public void addRoute(ShipRoute newRoute) {
        routes.add(newRoute);
    }
}
