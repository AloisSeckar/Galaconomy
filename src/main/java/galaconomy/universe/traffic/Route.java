package galaconomy.universe.traffic;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.map.Star;
import galaconomy.utils.MathUtils;
import java.io.Serializable;

public class Route implements IDisplayable, Serializable {
    
    private final Star departure;
    private final Star arrival;
    private final double speed;
    
    private final double distanceTotal;
    private double distanceElapsed;
    
    private TravelStatus status;

    public Route(Star departure, Star arrival, double speed) {
        this.departure = departure;
        this.arrival = arrival;
        this.speed = speed;
        
        this.distanceTotal = MathUtils.getDistanceBetween(departure, arrival);
        this.distanceElapsed = 0;
        this.status = TravelStatus.SCHEDULED;
    }

    @Override
    public String displayName() {
        return departure.displayName() + " TO " + arrival.displayName();
    }

    @Override
    public String displayDscr() {
        StringBuilder routeDscr = new StringBuilder();
        
        if (isFinished()) {
            routeDscr.append("Travelled from ").append(departure.displayName());
            routeDscr.append("to ").append(arrival.displayName()).append("system\n");
        } else {
            routeDscr.append("Traveling from ").append(departure.displayName());
            routeDscr.append("to ").append(arrival.displayName()).append("system\n");
            routeDscr.append("Distance: ").append(String.format("%.2f", distanceTotal)).append("\n");
            routeDscr.append("Elapsed: ").append(String.format("%.2f", distanceElapsed)).append("\n");
            routeDscr.append("Speed: ").append(String.format("%.2f", speed)).append("\n\n");
        }

        return routeDscr.toString();
    }

    @Override
    public String getImage() {
        return Constants.FOLDER_IMG + "rift_travel.png";
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

    public TravelStatus getStatus() {
        return status;
    }
    
    public void start() {
        status = TravelStatus.ONGOING;
    }
    
    public void progress() {
        this.distanceElapsed += this.speed;
        if (distanceElapsed >= distanceTotal) {
            distanceElapsed = distanceTotal;
            status = TravelStatus.FINISHED;
        }
    }
    
    public boolean isFinished() {
        return status == TravelStatus.FINISHED;
    }
}
