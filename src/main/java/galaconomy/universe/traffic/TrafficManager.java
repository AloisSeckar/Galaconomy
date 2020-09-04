package galaconomy.universe.traffic;

import galaconomy.universe.UniverseManager;
import org.slf4j.*;

public class TrafficManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(TrafficManager.class);
    
    public static boolean recalcTravel(Travel travel) {
        boolean ret = false;
        
        try {
            travel.progress();
            if (travel.isFinished()) {
                ret = true;
                travel.getShip().addTravel(travel);
                LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + travel.getShip().displayName() + " arrived in " + travel.getArrival().displayName() + " system");
            }
        } catch (Exception ex) {
            LOG.error("TrafficManager.recalcTravel", ex);
        }
        
        return ret;
    }

}
