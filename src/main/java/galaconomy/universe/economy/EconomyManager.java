package galaconomy.universe.economy;

import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import java.util.*;
import org.slf4j.*;

public class EconomyManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(TradeHelper.class);
    
    private EconomyManager() {
    }

    public static void harvestProduction(Player player) {
        try {
            player.getBuildings().forEach((building) -> {
                Cargo harvest = building.produce();
                if (harvest != null) {
                    TradeHelper.sellCargo(harvest, player, building.getParentBase());
                }
            });
        } catch (Exception ex) {
            LOG.error("EconomyManager.harvestProduction", ex);
        }
    }

}
