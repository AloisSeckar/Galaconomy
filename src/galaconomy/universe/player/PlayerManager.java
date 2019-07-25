package galaconomy.universe.player;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.systems.Star;
import galaconomy.universe.traffic.*;
import galaconomy.utils.MathUtils;
import java.util.*;

public class PlayerManager {
    
    public static List<Route> rethinkRoutes(Map<String, Player> players) {
        List<Route> newRoutes = new ArrayList<>();
        
        Random rand = new Random(System.currentTimeMillis()); 
        List<Star> systems = new ArrayList<>( UniverseManager.getInstance().getStars().values());
        int maxInt = systems.size();
        
        for (Player player : players.values()) {
            if (player != null && player.isAi()) {
                for (Ship ship : player.getShips()) {
                    if (ship.isIdle()) {
                        Star departure = ship.getLocation();
                        int random = rand.nextInt(maxInt);
                        Star arrival = systems.get(random);
                        System.out.println("Random: " + random + ": " + arrival.displayName());

                        double distance = MathUtils.getDistance(departure.getX(), departure.getY(), arrival.getX(), arrival.getY());

                        Route newRoute = new Route(ship, departure, arrival, distance);
                        newRoutes.add(newRoute);

                        System.out.println(UniverseManager.getInstance().getStellarTime() + ": " + ship.displayName() + " set off from " + departure.displayName() + " system");
                    }
                }
            }
        }
        
        return newRoutes;
    }

}
