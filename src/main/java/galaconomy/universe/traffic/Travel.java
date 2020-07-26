package galaconomy.universe.traffic;

import galaconomy.universe.*;
import galaconomy.universe.economy.Cargo;
import galaconomy.universe.map.Star;
import galaconomy.utils.TrafficUtils;
import java.io.Serializable;
import java.util.*;
import org.slf4j.*;

public class Travel implements IDisplayable, Serializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(Travel.class);
    
    private final Ship ship;
    private final Star departure;
    private final Star arrival;
    
    private final List<Route> routes;
    private TravelStatus status;
    
    private final double distanceTotal;
    private double distanceElapsed;
    
    private final long eta;
    private final long started;
    private long finished;

    public Travel(Ship ship, Star departure, Star arrival) {
        this.ship = ship;
        this.departure = departure;
        this.arrival = arrival;
        this.routes = new ArrayList<>();
        this.status = TravelStatus.PROPOSED;
        
        List<Star> itinerary = TrafficUtils.getPath(departure, arrival);
        double totalDistance = 0;
        for (int i = 0; i < itinerary.size() - 1; i++) {
            Star stageDep = itinerary.get(i);
            Star stageArr = itinerary.get(i + 1);
            Route stage = new Route(stageDep, stageArr, ship.getRiftSpeed());
            routes.add(stage);
            totalDistance += stage.getDistanceTotal();
        }
        this.distanceTotal = totalDistance;
        
        this.status = TravelStatus.SCHEDULED;
        LOG.info(ship.displayName() + ": route from " + departure.displayName() + " to " + arrival.displayName() + " planned");
        
        this.started = UniverseManager.getInstance().getStellarTime();
        this.finished = -1;
        
        this.eta = started + new Double(Math.ceil(distanceTotal / ship.getRiftSpeed())).intValue();
        
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
            routeDscr.append("Speed: ").append(String.format("%.2f", ship.getRiftSpeed())).append("\n");
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

    public List<Route> getRoutes() {
        return routes;
    }
    
    public void progress() {
        double elapsed = ship.getRiftSpeed();
        this.distanceElapsed += elapsed;
        
        Route activeRoute = getActiveRoute();
        if (activeRoute != null) {
            activeRoute.progress();
        }
        
        if (distanceElapsed >= distanceTotal) {
            distanceElapsed = distanceTotal;
            ship.increaseMileage(elapsed - (distanceElapsed - distanceTotal));
            ship.setIdle(true);
            ship.setCurrentLocation(arrival);
            finished = UniverseManager.getInstance().getStellarTime();
            status = TravelStatus.FINISHED;
        } else {
            ship.increaseMileage(elapsed);
        }
    }
    
    public boolean isFinished() {
        return status == TravelStatus.FINISHED;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private Route getActiveRoute() {
        Route ret = null;
        
        for (Route route : routes) {
            if (route.getStatus() == TravelStatus.SCHEDULED) {
                route.start();
                ret = route;
                break;
            } else if (route.getStatus() == TravelStatus.ONGOING) {
                ret = route;
                break;
            }
        }
        
        return ret;
    }
}
