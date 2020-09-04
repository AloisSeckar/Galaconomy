package galaconomy.universe;

import galaconomy.universe.map.Connector;
import galaconomy.universe.map.Star;
import galaconomy.universe.economy.EconomyManager;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.*;
import galaconomy.universe.traffic.*;
import galaconomy.utils.ConfigUtils;
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
    private boolean enhancedLogging;
    
    private long stellarTime = 100000;
    private double enginePeriod = 1;
    
    private Player humanPlayer;
    private Player glsPlayer;
    private final Map<String, Player> aiPlayers = new HashMap<>();
    
    private final Map<String, Star> stars = new HashMap<>();
    private final List<Base> bases = new ArrayList<>();
    private final List<Connector> gates = new ArrayList<>();
    private final List<Travel> travels = new ArrayList<>();
    
    private UniverseManager() {
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

            LOG.info("Universe loaded");

            INSTANCE.startEngine();
            
            ret = true;
        } catch (IOException | ClassNotFoundException ex) {
            LOG.error("UniverseManager.loadUniverse", ex);
        }
        
        return ret;
    }
    
    public Map<String, Star> getStars() {
        return stars;
    }
    
    public List<Star> getAvailableStars() {
        List<Star> ret = new ArrayList<>();
        ret.addAll(stars.values());
        return ret;
    }
    
    public List<Base> getAvailableBases() {
        List<Base> ret = Collections.unmodifiableList(bases);
        return ret;
    }
    
    public Star findStar(String starName) {
        return stars.get(starName);
    }
    
    public void addStar(Star newStar) {
        stars.put(newStar.displayName(), newStar);
        bases.addAll(newStar.getBases());
        LOG.info("Star added: " + newStar.displayName());
    }

    public Player getHumanPlayer() {
        return humanPlayer;
    }

    public void updatePlayer(Player player) {
        this.humanPlayer = player;
        LOG.info("Player updated: " + player.displayName());
    }

    public Player getGLSPlayer() {
        return glsPlayer;
    }

    public void updateGLSPlayer(Player glsPlayer) {
        this.glsPlayer = glsPlayer;
        LOG.info("GLS Player set up");
    }
    
    public Map<String, Player> getAIPlayers() {
        return aiPlayers;
    }
    
    public List<Player> getAllPlayers() {
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(humanPlayer);
        allPlayers.addAll(aiPlayers.values());
        return allPlayers;
    }
    
    public List<Ship> getAllShips() {
        List<Ship> allShips = new ArrayList<>();
        List<Player> allPlayers = UniverseManager.getInstance().getAllPlayers();
        allPlayers.forEach((record) -> {
            allShips.addAll(record.getShips());
        });
        return allShips;
    }
    
    public Player findAIPlayer(String playerName) {
        return aiPlayers.get(playerName);
    }
    
    public void addAIPlayer(Player newPlayer) {
        aiPlayers.put(newPlayer.displayName(), newPlayer);
        LOG.info("AI Player added: " + newPlayer.displayName());
    }

    public List<Connector> getGates() {
        return gates;
    }
    
    public void addGate(Connector newGate) {
        gates.add(newGate);
        LOG.info("Gate added: " + newGate.displayName());
    }

    public List<Travel> getTravels() {
        return travels;
    }
    
    public void addTravel(Travel newTravel) {
        travels.add(newTravel);
        LOG.info("Travel added: " + newTravel.displayName());
    }
    
    public void resetUniverse() {
        stopEngine();
        
        humanPlayer = null;
        glsPlayer = null;
        aiPlayers.clear();
        
        stars.clear();
        gates.clear();
        travels.clear();
        
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
        if (newSubscriber != null) {
            if (!subscribers.contains(newSubscriber)) {
                subscribers.add(newSubscriber);
                LOG.info("Subscriber added: " + newSubscriber.toString());
            } else {
                LOG.warn("Subscriber already registered: " + newSubscriber.toString());
            }
        }
    }
    
    public void unRegisterSubscriber(IEngineSubscriber subscriber) {
        if (subscriber != null) {
            subscribers.remove(subscriber);
            LOG.info("Subscriber removed: " + subscriber.toString());
        }
    }
    
    public long getStellarTime() {
        return stellarTime;
    }

    public String getStellarTimeString() {
        return String.valueOf(stellarTime);
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
    
    private void initEngineInstance() {
        enhancedLogging = ConfigUtils.isEnhancedLogging();
        universeEngine = new Timeline(
            new KeyFrame(Duration.seconds(enginePeriod), e -> {
                try {
                    stellarTime++;
                    
                    long start = System.currentTimeMillis();
                    if (enhancedLogging) {
                        LOG.info("ENGINE TICK " + stellarTime + " STARTED");
                    }

                    harvestProduction();
                    recalcSupplies();
                    recalcTravels();
                    rethinkPurchases();
                    rethinkTravels();

                    subscribers.stream().filter((subscriber) -> (subscriber.isActive())).forEachOrdered((subscriber) -> {
                        subscriber.engineTaskFinished(stellarTime);
                    });
                    
                    long end = System.currentTimeMillis();
                    if (enhancedLogging) {
                        LOG.info("DURATION: " + (end - start) + "ms");
                        LOG.info("ENGINE TICK " + stellarTime + " FINISHED");
                    }
                } catch (Exception ex) {
                    LOG.error("UniverseManager.universeEngine", ex);
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
    
    private void harvestProduction() {
        getAllPlayers().forEach(player -> {
            EconomyManager.harvestProduction(player);
        });
    }

    private void recalcSupplies() {
        bases.forEach(base -> {
            base.recalcSupplies();
        });
    }
    
    private void recalcTravels() {
        List<Travel> finishedTravels = new ArrayList<>();
        travels.forEach(travel -> {
            boolean isFinished = TrafficManager.recalcTravel(travel);
            if (isFinished) {
                finishedTravels.add(travel);
            }
        });
        finishedTravels.forEach((finishedTravel) -> {
            travels.remove(finishedTravel);
        });
    }
    
    private void rethinkTravels() {
        aiPlayers.values().forEach(player -> {
            List<Travel> newTravels = PlayerManager.rethinkTravels(player);
            travels.addAll(newTravels);
        });
    }
    
    private void rethinkPurchases() {
        aiPlayers.values().forEach(player -> {
            PlayerManager.rethinkPurchases(player);
        });
    }
}
