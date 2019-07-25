package galaconomy.universe.traffic;

import galaconomy.universe.UniverseManager;
import java.util.*;

public class TrafficManager {
    
    public static List<Route> recalcRoutes(List<Route> activeRoutes) {
        List<Route> finishedRoutes = new ArrayList<>();
        
        for (Route route : activeRoutes) {
            route.progress();
            if (route.isFinished()) {
                finishedRoutes.add(route);
                route.getShip().addRoute(route);
                System.out.println(UniverseManager.getInstance().getStellarTime() + ": " + route.getShip().displayName() + " arrived in " + route.getArrival().displayName() + " system");
            }
        }
        
        return finishedRoutes;
    }

}
