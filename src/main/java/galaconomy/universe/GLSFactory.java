package galaconomy.universe;

import galaconomy.universe.building.*;
import static galaconomy.universe.building.Building.*;
import galaconomy.universe.player.Player;
import org.slf4j.*;

public class GLSFactory {
    
    private static final Logger LOG = LoggerFactory.getLogger(GLSFactory.class);
    
    private GLSFactory() {
    }
    
    public static Building deliverBuilding(String buildingType) {
        Building ret = null;
        
        Player glsPlayer = UniverseManager.getInstance().getGLSPlayer();
        if (glsPlayer != null) {
            
            switch (buildingType) {
                case FACTORY:
                    ret = new Factory(null, glsPlayer);
                    break;
                case FARM:
                    ret = new Farm(null, glsPlayer);
                    break;
                case GENERATOR:
                    ret = new Generator(null, glsPlayer);
                    break;
                case MINE:
                    ret = new Mine(null, glsPlayer);
                    break;
                case WAREHOUSE:
                    ret = new Warehouse(null, glsPlayer);
                    break;
                default:
                    LOG.warn("GLSFactory.deliverBuilding - unsupported building type '" + buildingType + "'");
            }
            
        } else {
            LOG.warn("GLSFactory.deliverBuilding - method called while GLS player is not set");
        }
        
        return ret;
    }
    
}
