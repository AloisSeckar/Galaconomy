package galaconomy.universe.traffic;

import galaconomy.universe.*;
import galaconomy.universe.map.*;
import galaconomy.utils.TrafficUtils;
import java.io.Serializable;
import java.util.*;
import org.slf4j.*;

public class Travel implements IDisplayable, Serializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(Travel.class);
    
    private final Ship ship;
    private final Base departure;
    private final Base arrival;
    
    private final List<Route> routes;
    private TravelStatus status;
    
    private final double distanceRiftTotal;
    private double distanceRiftElapsed;
    private final double distancePulseTotal;
    private double distancePulseElapsed;
    
    private final long eta;
    private final long started;
    private long finished;

    public Travel(Ship ship, Base departure, Base arrival) {
        this.ship = ship;
        this.departure = departure;
        this.arrival = arrival;
        this.routes = new ArrayList<>();
        this.status = TravelStatus.PROPOSED;
        
        AbstractMapElement localDep = departure;
        List<Star> itinerary = TrafficUtils.getPath((Star) departure.getParent(), (Star) arrival.getParent());
        for (int i = 0; i < itinerary.size() - 1; i++) {
            Star stageDep = itinerary.get(i);
            Star stageArr = itinerary.get(i + 1);
            
            RiftGate depGate = stageDep.getRiftGateTo(stageArr);
            
            Route pulseStage = new Route(localDep, depGate, ship.getPulseSpeed());
            routes.add(pulseStage);
            
            Route riftStage = new Route(stageDep, stageArr, ship.getRiftSpeed());
            routes.add(riftStage);
            
            localDep = stageArr.getRiftGateTo(stageDep);
        }
        Route lastPulseStage = new Route(localDep, arrival, ship.getPulseSpeed());
        routes.add(lastPulseStage);
        
        double totalRiftDistance = 0;
        double totalPulseDistance = 0;
        for (Route stage : routes) { 
            if (stage.isRiftDrive()) {
                totalRiftDistance += stage.getDistanceTotal();
            } else {
                totalPulseDistance += stage.getDistanceTotal();
            }
        }
        this.distanceRiftTotal = totalRiftDistance;
        this.distancePulseTotal = totalPulseDistance;
        
        this.status = TravelStatus.SCHEDULED;
        LOG.info(ship.displayName() + ": route from " + departure.displayName() + " to " + arrival.displayName() + " planned");
        
        this.started = UniverseManager.getInstance().getStellarTime();
        this.finished = -1;
        
        this.eta = started + new Double(Math.ceil(distanceRiftTotal / ship.getRiftSpeed())).intValue();
        
        this.ship.setIdle(false);
        this.ship.setCurrentBase(null);
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
            routeDscr.append("Travelling from ").append(departure.displayName());
            routeDscr.append("to ").append(arrival.displayName()).append("\n");
            routeDscr.append("Distance: ").append(String.format("%.2f", distanceRiftTotal)).append("\n");
            routeDscr.append("Elapsed: ").append(String.format("%.2f", distanceRiftElapsed)).append("\n");
            routeDscr.append("Speed: ").append(String.format("%.2f", ship.getRiftSpeed())).append("\n");
            routeDscr.append("ETA: ").append(eta).append("\n\n");
            
            routeDscr.append("CARGO").append("\n");
            routeDscr.append("----------").append("\n");
            ship.getCurrentCargo().forEach(goods -> {
                routeDscr.append(goods.displayName()).append("\n");
            });
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
    
    public Base getDeparture() {
        return departure;
    }

    public Base getArrival() {
        return arrival;
    }

    public double getDistanceRiftTotal() {
        return distanceRiftTotal;
    }

    public double getDistanceRiftElapsed() {
        return distanceRiftElapsed;
    }

    public double getDistancePulseTotal() {
        return distancePulseTotal;
    }

    public double getDistancePulseElapsed() {
        return distancePulseElapsed;
    }
    
    public List<Route> getRoutes() {
        return routes;
    }
    
    public Route getActiveRoute() {
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
    
    public void progress() {
        Route activeRoute = getActiveRoute();
        if (activeRoute != null) {
            activeRoute.progress();
            
            if (activeRoute.isRiftDrive()) {
                double elapsed = ship.getRiftSpeed();
                this.distanceRiftElapsed += elapsed;
                if (distanceRiftElapsed >= distanceRiftTotal ) {
                    distanceRiftElapsed = distanceRiftTotal;
                }
                if (activeRoute.isFinished()) {
                    ship.setCurrentSystem((Star) activeRoute.getArrival());
                }
            } else {
                double elapsed = ship.getPulseSpeed();
                this.distancePulseElapsed += elapsed;
                if (distancePulseElapsed >= distancePulseTotal ) {
                    distancePulseElapsed = distancePulseTotal;
                }
            }
        } else {
            ship.setIdle(true);
            ship.setCurrentBase(arrival);
            finished = UniverseManager.getInstance().getStellarTime();
            status = TravelStatus.FINISHED;
        }
    }
    
    public boolean isFinished() {
        return status == TravelStatus.FINISHED;
    }
}
