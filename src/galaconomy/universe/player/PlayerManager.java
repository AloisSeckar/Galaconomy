package galaconomy.universe.player;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.economy.*;
import galaconomy.universe.systems.*;
import galaconomy.universe.traffic.*;
import galaconomy.utils.MathUtils;
import java.util.*;
import org.slf4j.*;

public class PlayerManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(PlayerManager.class);
    
    public static List<Route> rethinkRoutes(Map<String, Player> players) {
        List<Route> newRoutes = new ArrayList<>();
        
        Random rand = new Random(System.currentTimeMillis()); 
        List<Star> systems = new ArrayList<>( UniverseManager.getInstance().getStars().values());
        int maxInt = systems.size();
        
        for (Player player : players.values()) {
            for (Ship ship : player.getShips()) {
                if (ship.isIdle()) {
                    Star location = ship.getCurrentLocation();
                    Star destination = null;
                    
                    while (!ship.getCargoList().isEmpty()) {
                        Cargo cargo = ship.getCargoList().get(0);
                        Supplies supply = location.findSupplies(cargo.getGoods().displayName());
                        ship.performSale(ship.getCargoList().get(0), supply.getPriceBuy());
                    }
                    
                    Supplies itemToBuy = EconomyHelper.findLowestPrice(location);
                    if (itemToBuy != null) {
                        Goods goodsToBuy = itemToBuy.getGoods();
                        
                        int available = itemToBuy.getAmount();
                        int price = itemToBuy.getPriceSell();
                        int capacity = ship.getCargo();
                        long credits = ship.getCurrentOwner().getCredits();
                        
                        int amount;
                        if (price * capacity <= credits) {
                            amount = Math.min(capacity, available);
                        } else {
                            amount = Math.min(new Long(credits / price).intValue(), available);
                        }
                        
                        ship.performPurchase(goodsToBuy, amount, price);
                        
                        destination = EconomyHelper.findBestPrice(goodsToBuy);
                        if (location.equals(destination)) {
                            destination = null;
                        }
                    } 
                    
                    if (destination == null) {
                        destination = systems.get(rand.nextInt(maxInt));
                    }
                    
                    double distance = MathUtils.getDistance(location.getX(), location.getY(), destination.getX(), destination.getY());

                    Route newRoute = new Route(ship, location, destination, distance);
                    newRoutes.add(newRoute);

                    LOG.info(UniverseManager.getInstance().getStellarTime() + ": " + ship.displayName() + " set off from " + location.displayName() + " system to " + destination.displayName());
                }
            }
        }
        
        return newRoutes;
    }
}
