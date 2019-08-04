package galaconomy.universe.traffic;

import galaconomy.universe.*;
import galaconomy.universe.economy.Cargo;
import galaconomy.universe.systems.Star;
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
        this.ship.setCurrentLocation(null);
    }

    @Override
    public String displayName() {
        return ship.displayName();
    }

    @Override
    public String displayDscr() {
        StringBuilder routeDscr = new StringBuilder();
        
        if (isFinished()) {
            routeDscr.append("Travelled from ").append(departure.displayName());
            routeDscr.append("to ").append(arrival.displayName()).append("system\n");
            routeDscr.append("Landed: ").append(finished).append("\n");
        } else {
            routeDscr.append("Traveling from ").append(departure.displayName());
            routeDscr.append("to ").append(arrival.displayName()).append("system\n");
            routeDscr.append("Distance: ").append(String.format("%.2f", distanceTotal)).append("\n");
            routeDscr.append("Elapsed: ").append(String.format("%.2f", distanceElapsed)).append("\n");
            routeDscr.append("Speed: ").append(String.format("%.2f", ship.getSpeed())).append("\n");
            routeDscr.append("ETA: ").append(eta).append("\n\n");
            
            routeDscr.append("CARGO").append("\n");
            routeDscr.append("----------").append("\n");
            for (Cargo goods : ship.getCargoList()) {
                routeDscr.append(goods.displayName()).append("\n");
            }
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
        double elapsed = ship.getSpeed();
        this.distanceElapsed += elapsed;
        
        if (distanceElapsed > distanceTotal) {
            ship.increaseMileage(elapsed - (distanceElapsed - distanceTotal));
            ship.setIdle(true);
            ship.setCurrentLocation(arrival);
            finished = UniverseManager.getInstance().getStellarTime();
            distanceElapsed = distanceTotal;
        } else {
            ship.increaseMileage(elapsed);
        }
    }
    
    public boolean isFinished() {
        return finished > -1;
    }
}
