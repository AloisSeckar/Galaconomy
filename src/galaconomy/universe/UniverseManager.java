package galaconomy.universe;

import galaconomy.universe.player.Player;
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
    
    private final Map<String, Star> stars = new HashMap<>();
    private final Map<String, Player> players = new HashMap<>();
    private final List<Route> routes = new ArrayList<>();
    
    private UniverseManager() {
        initEngineInstance();
    }
    
    public static UniverseManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniverseManager();
        }
        return INSTANCE;
    }
    
    public Map<String, Star> getStars() {
        return Collections.unmodifiableMap(stars);
    }
    
    public Star findStar(String starName) {
        return stars.get(starName);
    }
    
    public void addStar(Star newStar) {
        stars.put(newStar.getName(), newStar);
    }
    
    public Map<String, Player> getPlayers() {
        return Collections.unmodifiableMap(players);
    }
    
    public Player findPlayer(String playerName) {
        return players.get(playerName);
    }
    
    public void addPlayer(Player newPlayer) {
        players.put(newPlayer.getName(), newPlayer);
    }

    public List<Route> getRoutes() {
        return Collections.unmodifiableList(routes);
    }
    
    public boolean addRoute(Route newRoute) {
        return routes.add(newRoute);
    }
    
    public void resetUniverse() {
        stopEngine();
        
        stars.clear();
        players.clear();
        routes.clear();
        
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
        List<Route> finishedRoutes = new ArrayList<>();
        for (Route route : routes) {
            route.progress();
            if (route.isFinished()) {
                finishedRoutes.add(route);
                route.getShip().addRoute(route);
                System.out.println(route.getShip().getName() + " arrived in " + route.getArrival().getName() + " system");
            }
        }
        for (Route finishedRoute : finishedRoutes) {
            routes.remove(finishedRoute);
        }
    }
}
