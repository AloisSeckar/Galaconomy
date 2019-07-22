package galaconomy.universe;

import galaconomy.universe.systems.*;
import galaconomy.universe.traffic.*;
import java.util.*;
import javafx.animation.*;
import javafx.util.Duration;

public class UniverseManager {
    
    private static UniverseManager INSTANCE;
    private final List<IEngineSubscriber> subscribers = new ArrayList<>();
    
    private Timeline universeEngine;
    
    private long stellarTime = 100000;
    private double engineDuration = 1;
    
    private final List<StarSystem> starSystems = new ArrayList<>();
    private final List<Ship> ships = new ArrayList<>();
    private final List<ShipRoute> shipRoutes = new ArrayList<>();
    private final List<ShipRoute> shipRoutesHistory = new ArrayList<>();
    
    private UniverseManager() {
        initEngineInstance();
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
        stopEngine();
        
        starSystems.clear();
        ships.clear();
        shipRoutes.clear();
        shipRoutesHistory.clear();
        
        stellarTime = 100000;
        engineDuration = 1;
    }
    
    public void startEngine() {
        if (isEngineRunning()) {
            universeEngine.stop();
        }
        universeEngine.play();
    }
    
    public void pauseEngine() {
        if (isEngineRunning()) {
            universeEngine.pause();
        } else {
            universeEngine.play();
        }
    }
    
    public void stopEngine() {
        if (isEngineRunning()) {
            universeEngine.stop();
        }
    }

    public double getEngineDuration() {
        return engineDuration;
    }

    public void setEngineDuration(double engineDuration) {
        this.engineDuration = engineDuration;
        initEngineInstance();
        System.out.println("GalaconomyEngine: engine duration changed to " + engineDuration);
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
    
    public boolean isEngineRunning() {
        return universeEngine != null && universeEngine.getStatus() == Animation.Status.RUNNING;
    }
    
    public boolean isEnginePaused() {
        return universeEngine != null && universeEngine.getStatus() == Animation.Status.PAUSED;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private void initEngineInstance() {
        boolean running = isEngineRunning();
        if (running) {
            universeEngine.stop();
        }
        
        universeEngine = new Timeline(
            new KeyFrame(Duration.seconds(engineDuration), e -> {
                stellarTime++;
                
                recalcRoutes();

                for (IEngineSubscriber subscriber : subscribers) {
                    subscriber.engineTaskFinished(stellarTime);
                }

                System.out.println("GalaconomyEngine: engine tick finished");
            })
        );
        universeEngine.setCycleCount(Timeline.INDEFINITE);
        
        if (running) {
            universeEngine.play();
        }
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
