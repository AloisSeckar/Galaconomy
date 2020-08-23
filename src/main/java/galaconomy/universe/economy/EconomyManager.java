package galaconomy.universe.economy;

import galaconomy.universe.map.Base;
import galaconomy.universe.player.Player;
import java.util.*;
import org.slf4j.*;

public class EconomyManager {
    
    private static final Random rand = new Random();
    
    private static final Logger LOG = LoggerFactory.getLogger(TradeHelper.class);
    
    private EconomyManager() {
    }
    
    public static void recalcSupplies(List<Base> bases) {
        bases.forEach((base) -> {
            try {
                base.getSupplies().values().forEach((current) -> {
                    int amount = current.getAmount();
                    if (amount < 1) {
                        amount = 1;
                    }
                    int amountDelta = rand.nextInt(amount / 10 + 1) + 1;
                    if (rand.nextBoolean()) {
                        current.increaseAmount(amountDelta);
                        current.setPriceBuy(Math.max(current.getPriceBuy() - rand.nextInt(amountDelta), 101));
                        current.setPriceSell(current.getPriceBuy() + rand.nextInt(amountDelta));
                    } else {
                        current.decreaseAmount(amountDelta);
                        current.setPriceBuy(current.getPriceBuy() + rand.nextInt(amountDelta));
                        current.setPriceSell(Math.max(current.getPriceBuy() - rand.nextInt(amountDelta), 101));
                    }
                });
            } catch (Exception ex) {
                LOG.error("EconomyManager.recalcSupplies", ex);
            }
        });
    }

    public static void harvestProduction(List<Player> players) {
        players.forEach((player) -> {
            try {
                player.getBuildings().forEach((building) -> {
                    Cargo harvest = building.produce();
                    if (harvest != null) {
                        TradeHelper.sellCargo(harvest, player, building.getParent());
                    }
                });
            } catch (Exception ex) {
                LOG.error("EconomyManager.harvestProduction", ex);
            }
        });
    }

}
