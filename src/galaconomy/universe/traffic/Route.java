package galaconomy.universe.traffic;

import galaconomy.universe.systems.Star;

public class Route {
    
    private final Ship ship;
    private final Star departure;
    private final Star arrival;
    private final double distanceTotal;
    private double distanceElapsed;

    public Route(Ship ship, Star departure, Star arrival, double distanceTotal, double distanceElapsed) {
        this.ship = ship;
        this.departure = departure;
        this.arrival = arrival;
        this.distanceTotal = distanceTotal;
        this.distanceElapsed = distanceElapsed;
    }

    public Ship getShip() {
        return ship;
    }
    
    public Star getDeparture() {
        return departure;
    }

    public Star getArrival() {
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
