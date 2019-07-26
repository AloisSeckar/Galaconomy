package galaconomy.universe.traffic;

import galaconomy.universe.IDisplayable;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.systems.Star;
import galaconomy.utils.DisplayUtils;
import java.io.Serializable;

public class Route implements IDisplayable, Serializable {
    
    private final Ship ship;
    private final Star departure;
    private final Star arrival;
    
    private final double distanceTotal;
    private double distanceElapsed;
    
    private final long eta;
    private final long started;
    private long finished;

    public Route(Ship ship, Star departure, Star arrival, double distanceTotal) {
        this.ship = ship;
        this.departure = departure;
        this.arrival = arrival;
        this.distanceTotal = distanceTotal;
        
        this.distanceElapsed = 0;
        this.started = UniverseManager.getInstance().getStellarTime();
        this.finished = -1;
        
        this.eta = started + new Double(Math.ceil(distanceTotal / ship.getSpeed())).intValue();
        
        this.ship.setIdle(false);
        this.ship.setLocation(null);
    }

    @Override
    public String displayName() {
        return ship.displayName();
    }

    @Override
    public String displayDscr() {
        StringBuilder routeDscr = new StringBuilder();
        
        if (isFinished()) {
            routeDscr.append("Travelled from ").append(departure.displayName()).append(DisplayUtils.getCoordinates(departure.getX(), departure.getY()));
            routeDscr.append(" to ").append(arrival.displayName()).append(DisplayUtils.getCoordinates(arrival.getX(), arrival.getY())).append("system\n");
            routeDscr.append("Landed: ").append(finished).append("\n");
        } else {
            routeDscr.append("Traveling from ").append(departure.displayName()).append(DisplayUtils.getCoordinates(departure.getX(), departure.getY()));
            routeDscr.append(" to ").append(arrival.displayName()).append(DisplayUtils.getCoordinates(arrival.getX(), arrival.getY())).append("system\n");
            routeDscr.append("Distance: ").append(String.format("%.2f", distanceTotal)).append("\n");
            routeDscr.append("Elapsed: ").append(String.format("%.2f", distanceElapsed)).append("\n");
            routeDscr.append("Speed: ").append(String.format("%.2f", ship.getSpeed())).append("\n");
            routeDscr.append("ETA: ").append(eta).append("\n");
        }

        return routeDscr.toString();
    }

    @Override
    public String getImage() {
        return ship.getImage();
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
        if (distanceElapsed > distanceTotal) {
            distanceElapsed = distanceTotal;
            finished = UniverseManager.getInstance().getStellarTime();
            ship.setIdle(true);
            ship.setLocation(arrival);
        }
    }
    
    public boolean isFinished() {
        return finished > -1;
    }

}
