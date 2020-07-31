package galaconomy.universe.traffic;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.utils.MathUtils;
import java.io.Serializable;

public class Route implements IDisplayable, Serializable {
    
    private final Star system;
    private final AbstractMapElement departure;
    private final AbstractMapElement arrival;
    private final double speed;
    
    private final double distanceTotal;
    private double distanceElapsed;
    
    private TravelStatus status;
    private final RouteType type;

    public Route(AbstractMapElement departure, AbstractMapElement arrival, double speed) {
        this.departure = departure;
        this.arrival = arrival;
        
        if (departure instanceof Star && arrival instanceof Star) {
            type = RouteType.RIFT_DRIVE;
            system = null;
        } else {
            type = RouteType.WITHIN_SYSTEM;
            system = (Star) departure.getParent();
        }
        
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

    public Star getSystem() {
        return system;
    }
    
    public AbstractMapElement getDeparture() {
        return departure;
    }

    public AbstractMapElement getArrival() {
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

    public boolean isRiftDrive() {
        return type == RouteType.RIFT_DRIVE;
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
