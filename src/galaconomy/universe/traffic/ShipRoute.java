package galaconomy.universe.traffic;

import galaconomy.universe.systems.StarSystem;

public class ShipRoute {
    
    private final Ship ship;
    private final StarSystem departure;
    private final StarSystem arrival;
    private final double distanceTotal;
    private double distanceElapsed;

    public ShipRoute(Ship ship, StarSystem departure, StarSystem arrival, double distanceTotal, double distanceElapsed) {
        this.ship = ship;
        this.departure = departure;
        this.arrival = arrival;
        this.distanceTotal = distanceTotal;
        this.distanceElapsed = distanceElapsed;
    }

    public Ship getShip() {
        return ship;
    }

    public StarSystem getDeparture() {
        return departure;
    }

    public StarSystem getArrival() {
        return arrival;
    }

    public double getDistanceTotal() {
        return distanceTotal;
    }

    public double getDistanceElapsed() {
        return distanceElapsed;
    }
    
    public void progress() {
        this.distanceElapsed += ship.getSpeed();
    }
    
    public boolean isFinished() {
        return distanceElapsed >= distanceTotal;
    }

}
