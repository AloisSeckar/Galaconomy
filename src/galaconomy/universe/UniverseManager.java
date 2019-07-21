package galaconomy.universe;

import galaconomy.universe.systems.*;
import galaconomy.universe.traffic.*;
import java.util.*;
import javafx.animation.*;
import javafx.util.Duration;

public class UniverseManager {
    
    private static UniverseManager INSTANCE;
    private final List<IEngineSubscriber> subscribers = new ArrayList<>();
    
    private final Timeline universeEngine;
    
    private long stellarTime;
    
    private final List<StarSystem> starSystems = new ArrayList<>();
    private final List<Ship> ships = new ArrayList<>();
    private final List<ShipRoute> shipRoutes = new ArrayList<>();
    private final List<ShipRoute> shipRoutesHistory = new ArrayList<>();
    
    private UniverseManager() {
        universeEngine = getEngineInstance();
        universeEngine.setCycleCount(Timeline.INDEFINITE);
    }
    
    public static UniverseManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniverseManager();
        }
        return INSTANCE;
    }
    
    public List<StarSystem> getStarSystems() {
        return Collections.unmodifiableList(starSystems);
    }
    
    public boolean addStarSystem(StarSystem newSystem) {
        return starSystems.add(newSystem);
    }

    public List<Ship> getShips() {
        return Collections.unmodifiableList(ships);
    }
    
    public boolean addShip(Ship newShip) {
        return ships.add(newShip);
    }

    public List<ShipRoute> getShipRoutes() {
        return Collections.unmodifiableList(shipRoutes);
    }
    
    public boolean addShipRoute(ShipRoute newRoute) {
        return shipRoutes.add(newRoute);
    }

    public List<ShipRoute> getShipRoutesHistory() {
        return Collections.unmodifiableList(shipRoutesHistory);
    }
    
    public boolean addShipRouteHistory(ShipRoute newRoute) {
        return shipRoutesHistory.add(newRoute);
    }
    
    public void resetUniverse() {
        starSystems.clear();
        ships.clear();
        shipRoutes.clear();
        shipRoutesHistory.clear();
        
        stellarTime = 100000;
    }
    
    public void startEngine() {
        if (universeEngine.getStatus() == Animation.Status.RUNNING) {
            universeEngine.stop();
        }
        universeEngine.play();
    }
    
    public void stopEngine() {
        universeEngine.stop();
    }
    
    public void registerSubscriber(IEngineSubscriber newSubscriber) {
        if (!subscribers.contains(newSubscriber)) {
            subscribers.add(newSubscriber);
        }
        System.out.println("GalaconomyEngine: new subscriber registered");
    }
    
    public long getStellarTime() {
        return stellarTime;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private Timeline getEngineInstance() {
        return new Timeline(
            new KeyFrame(Duration.seconds(1), e -> {
                stellarTime++;
                
                recalcRoutes();

                for (IEngineSubscriber subscriber : subscribers) {
                    subscriber.engineTaskFinished(stellarTime);
                }

                System.out.println("GalaconomyEngine: engine tick finished");
            })
        );
    }
    
    private void recalcRoutes() {
        List<ShipRoute> finishedRoutes = new ArrayList<>();
        for (ShipRoute route : getShipRoutes()) {
            route.progress();
            if (route.isFinished()) {
                finishedRoutes.add(route);
                System.out.println(route.getShip().getName() + " arrived in " + route.getArrival().getName() + " system");
            }
        }
        for (ShipRoute finishedRoute : finishedRoutes) {
            shipRoutes.remove(finishedRoute);
            shipRoutesHistory.add(finishedRoute);
        }
    }
}
