package galaconomy.universe.traffic;

import galaconomy.universe.UniverseManager;
import java.util.*;
import org.slf4j.*;

public class TrafficManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(TrafficManager.class);
    
    public static List<Travel> recalcTravels(List<Travel> activeTravels) {
        List<Travel> finishedTravels = new ArrayList<>();
        
        for (Travel travel : activeTravels) {
            travel.progress();
            if (travel.isFinished()) {
                finishedTravels.add(travel);
                travel.getShip().addTravel(travel);
                LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + travel.getShip().displayName() + " arrived in " + travel.getArrival().displayName() + " system");
            }
        }
        
        return finishedTravels;
    }

}
