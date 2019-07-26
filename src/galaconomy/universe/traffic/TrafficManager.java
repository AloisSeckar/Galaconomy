package galaconomy.universe.traffic;

import galaconomy.universe.UniverseManager;
import java.util.*;
import org.slf4j.*;

public class TrafficManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(TrafficManager.class);
    
    public static List<Route> recalcRoutes(List<Route> activeRoutes) {
        List<Route> finishedRoutes = new ArrayList<>();
        
        for (Route route : activeRoutes) {
            route.progress();
            if (route.isFinished()) {
                finishedRoutes.add(route);
                route.getShip().addRoute(route);
                LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + route.getShip().displayName() + " arrived in " + route.getArrival().displayName() + " system");
            }
        }
        
        return finishedRoutes;
    }

}
