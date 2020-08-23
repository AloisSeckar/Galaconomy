package galaconomy.universe;

import galaconomy.universe.map.Connector;
import galaconomy.universe.map.Star;
import galaconomy.universe.economy.EconomyManager;
import galaconomy.universe.map.Base;
import galaconomy.universe.player.*;
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
        Collections.shuffle(ret); // TODO why this?
        return ret;
    }
    
    public List<Base> getAvailableBases() {
        List<Base> ret = Collections.unmodifiableList(bases);
        Collections.shuffle(ret); // TODO why this?
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

    public Player getPlayer() {
        return player;
    }

    public void updatePlayer(Player player) {
        this.player = player;
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
        
        player = null;
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
        universeEngine = new Timeline(
            new KeyFrame(Duration.seconds(enginePeriod), e -> {
                try {
                    stellarTime++;

                    harvestProduction();
                    recalcSupplies();
                    recalcTravels();
                    rethinkPurchases();
                    rethinkTravels();

                    subscribers.stream().filter((subscriber) -> (subscriber.isActive())).forEachOrdered((subscriber) -> {
                        subscriber.engineTaskFinished(stellarTime);
                    });
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
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(player);
        allPlayers.addAll(aiPlayers.values());
        EconomyManager.harvestProduction(allPlayers);
    }

    private void recalcSupplies() {
        EconomyManager.recalcSupplies(bases);
    }
    
    private void recalcTravels() {
        List<Travel> finishedTravels = TrafficManager.recalcTravels(travels);
        finishedTravels.forEach((finishedTravel) -> {
            travels.remove(finishedTravel);
        });
    }
    
    private void rethinkTravels() {
        List<Travel> newTravels = PlayerManager.rethinkTravels(aiPlayers);
        newTravels.forEach((newTravel) -> {
            travels.add(newTravel);
        });
    }
    
    private void rethinkPurchases() {
        PlayerManager.rethinkPurchases(aiPlayers);
    }
}
