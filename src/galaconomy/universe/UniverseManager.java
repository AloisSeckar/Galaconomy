package galaconomy.universe;

import galaconomy.constants.Constants;
import galaconomy.universe.player.Player;
import galaconomy.universe.player.PlayerManager;
import galaconomy.universe.systems.*;
import galaconomy.universe.traffic.*;
import java.awt.Color;
import java.util.*;
import javafx.animation.*;
import javafx.util.Duration;

public class UniverseManager {
    
    private static UniverseManager INSTANCE;
    private final List<IEngineSubscriber> subscribers = new ArrayList<>();
    
    private Timeline universeEngine;
    
    private long stellarTime = 100000;
    private double engineDuration = 1;
    
    private Player player;
    private final Map<String, Player> aiPlayers = new HashMap<>();
    
    private final Map<String, Star> stars = new HashMap<>();
    private final List<Route> routes = new ArrayList<>();
    
    private UniverseManager() {
        
        player = new Player("Human player", "Insert your text here...", Constants.PLAYERS_FOLDER + "player01.png", Color.GREEN, false);
            
        initEngineInstance();
    }
    
    public static UniverseManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniverseManager();
        }
        return INSTANCE;
    }
    
    public Map<String, Star> getStars() {
        return stars;
    }
    
    public Star findStar(String starName) {
        return stars.get(starName);
    }
    
    public void addStar(Star newStar) {
        stars.put(newStar.displayName(), newStar);
    }

    public Player getPlayer() {
        return player;
    }

    public void updatePlayer(Player player) {
        this.player = player;
    }
    
    public Map<String, Player> getAIPlayers() {
        return aiPlayers;
    }
    
    public Player findAIPlayer(String playerName) {
        return aiPlayers.get(playerName);
    }
    
    public void addAIPlayer(Player newPlayer) {
        aiPlayers.put(newPlayer.displayName(), newPlayer);
    }

    public List<Route> getRoutes() {
        return routes;
    }
    
    public void resetUniverse() {
        stopEngine();
        
        stars.clear();
        aiPlayers.clear();
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
                rethinkRoutes();

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
        List<Route> finishedRoutes = TrafficManager.recalcRoutes(routes);
        for (Route finishedRoute : finishedRoutes) {
            routes.remove(finishedRoute);
        }
    }
    
    private void rethinkRoutes() {
        List<Route> newRoutes = PlayerManager.rethinkRoutes(aiPlayers);
        for (Route newRoute : newRoutes) {
            routes.add(newRoute);
        }
    }
}
