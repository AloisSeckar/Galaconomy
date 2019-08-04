package galaconomy.universe;

import galaconomy.universe.economy.EconomyManager;
import galaconomy.universe.player.*;
import galaconomy.universe.systems.*;
import galaconomy.universe.traffic.*;
import java.io.*;
import java.util.*;
import javafx.animation.*;
import javafx.util.Duration;
import org.slf4j.*;

public class UniverseManager implements Serializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(UniverseManager.class);
    
    private static UniverseManager INSTANCE;
    private final List<IEngineSubscriber> subscribers = new ArrayList<>();
    
    private Timeline universeEngine;
    
    private long stellarTime = 100000;
    private double enginePeriod = 1;
    
    private Player player;
    private final Map<String, Player> aiPlayers = new HashMap<>();
    
    private final Map<String, Star> stars = new HashMap<>();
    private final List<Route> routes = new ArrayList<>();
    
    private UniverseManager() {
        setUpEngine();
    }
    
    public static UniverseManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UniverseManager();
        }
        return INSTANCE;
    }
    
    public static boolean saveUniverse(String path) {
        boolean ret = false;
        
        try (FileOutputStream f = new FileOutputStream(new File(path)); ObjectOutputStream o = new ObjectOutputStream(f)) {
            
            INSTANCE.tearDownEngine();
            
            o.writeObject(INSTANCE);
            
            LOG.info("Universe saved");
            ret = true;
        } catch (IOException ex) {
            LOG.error("UniverseManager.saveUniverse", ex);
        }
        
        return ret;
    }
    
    public static boolean loadUniverse(String path) {
        boolean ret = false;
        
        try (FileInputStream fi = new FileInputStream(new File(path)); ObjectInputStream oi = new ObjectInputStream(fi)) {
            
            INSTANCE = (UniverseManager) oi.readObject();

            INSTANCE.setUpEngine();

            LOG.info("Universe loaded");
            ret = true;
        } catch (IOException | ClassNotFoundException ex) {
            LOG.error("UniverseManager.loadUniverse", ex);
        }
        
        return ret;
        
    }
    
    public Map<String, Star> getStars() {
        return stars;
    }
    
    public Star findStar(String starName) {
        return stars.get(starName);
    }
    
    public void addStar(Star newStar) {
        stars.put(newStar.displayName(), newStar);
        LOG.info("Star added: " + newStar.displayName());
    }

    public Player getPlayer() {
        return player;
    }

    public void updatePlayer(Player player) {
        this.player = player;
        LOG.info("Player updated: " + player.displayName());
    }
    
    public Map<String, Player> getAIPlayers() {
        return aiPlayers;
    }
    
    public Player findAIPlayer(String playerName) {
        return aiPlayers.get(playerName);
    }
    
    public void addAIPlayer(Player newPlayer) {
        aiPlayers.put(newPlayer.displayName(), newPlayer);
        LOG.info("AI Player added: " + newPlayer.displayName());
    }

    public List<Route> getRoutes() {
        return routes;
    }
    
    public void addRoute(Route newRoute) {
        routes.add(newRoute);
        LOG.info("Route added: " + newRoute.displayName());
    }
    
    public void resetUniverse() {
        stopEngine();
        
        stars.clear();
        aiPlayers.clear();
        routes.clear();
        
        stellarTime = 100000;
        enginePeriod = 1;
        
        LOG.info("Universe reset completed");
    }
    
    public void startEngine() {
        if (universeEngine == null) {
            initEngineInstance();
        }
        universeEngine.play();
        LOG.info("Engine started");
    }
    
    public void pauseEngine() {
        if (isEngineRunning()) {
            universeEngine.pause();
            LOG.info("Engine paused");
        } else {
            universeEngine.play();
            LOG.info("Engine resumed");
        }
    }
    
    public void stopEngine() {
        if (isEngineRunning()) {
            universeEngine.stop();
            LOG.info("Engine stopped");
        }
    }

    public double getEnginePeriod() {
        return enginePeriod;
    }

    public void setEnginePeriod(double enginePeriod) {
        this.enginePeriod = enginePeriod;
        LOG.info("Engine period changed to " + enginePeriod);
        
        restartEngine();
    }
    
    public void registerSubscriber(IEngineSubscriber newSubscriber) {
        if (!subscribers.contains(newSubscriber)) {
            subscribers.add(newSubscriber);
            LOG.info("Subscriber added: " + newSubscriber.toString());
        } else {
            LOG.warn("Subscriber already registered: " + newSubscriber.toString());
        }
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
    
    private void restartEngine() {
        if (isEngineRunning() || isEnginePaused()) {
            universeEngine.stop();
            LOG.info("Engine stopped");
        }
        initEngineInstance();
        universeEngine.play();
    }
   
    private void setUpEngine() {
        startEngine();
        LOG.info("Engine set up");
    }
    
    private void initEngineInstance() {
        universeEngine = new Timeline(
            new KeyFrame(Duration.seconds(enginePeriod), e -> {
                stellarTime++;
                
                recalcSupplies();
                recalcRoutes();
                rethinkRoutes();

                for (IEngineSubscriber subscriber : subscribers) {
                    subscriber.engineTaskFinished(stellarTime);
                }
            })
        );
        universeEngine.setCycleCount(Timeline.INDEFINITE);
        LOG.info("Engine initiated");
    }

    private void tearDownEngine() {
        stopEngine();
        subscribers.clear();
        universeEngine = null;
        LOG.info("Engine torn down");
    }

    private void recalcSupplies() {
        EconomyManager.recalcSupplies(stars.values());
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
