package galaconomy.universe.player;

import galaconomy.constants.Constants;
import galaconomy.universe.IDisplayable;
import galaconomy.universe.building.Building;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.Ship;
import java.util.*;
import java.awt.Color;
import java.io.Serializable;
import java.util.stream.Collectors;
import org.slf4j.*;

public class Player implements IDisplayable, Serializable {
    
    private static final Logger LOG = LoggerFactory.getLogger(Player.class);
    
    private final String name;
    private final String dscr;
    private final String img;
    private final Color color;
    
    private Base homeBase;
    
    private final boolean ai;
    private final AIPersonality aiPersonality;
    
    private long credits;
    
    private final List<Ship> ships = new ArrayList<>();
    private final List<SurfaceTile> lands = new ArrayList<>();
    private final List<Building> buildings = new ArrayList<>();
    
    // TODO history of transactions

    public Player(String name, String dscr, String img,  Color color, Base homeBase, boolean ai, AIPersonality aiPersonality) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        this.color = color;
        
        this.homeBase = homeBase;
        
        this.ai = ai;
        this.aiPersonality = aiPersonality;
        
        this.credits = Constants.PLAYER_MONEY;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        return dscr;
    }

    @Override
    public String getImage() {
        return img;
    }
    
    public Color getColor() {
        return color;
    }

    public Base getHomeBase() {
        return homeBase;
    }

    public void setHomeBase(Base homeBase) {
        this.homeBase = homeBase;
    }

    public long getCredits() {
        return credits;
    }
    
    public void earnCredits(long amountEarned) {
        credits += amountEarned;
        LOG.info("Player " + name + " earned " + amountEarned + " credits [new balance: " + credits + "]");
    }
    
    public void spendCredits(long amountSpent) {
        credits -= amountSpent;
        LOG.info("Player " + name + " spent " + amountSpent + " credits [new balance: " + credits + "]");
    }

    public List<Ship> getShips() {
        return ships;
    }
    
    public boolean addShip(Ship newShip) {
        LOG.info("Player " + name + " acquired new ship " + newShip.displayName());
        newShip.changeOwner(Player.this);
        return ships.add(newShip);
    }

    public List<SurfaceTile> getLands() {
        return lands;
    }

    public List<SurfaceTile> getLands(Base base) {
        return lands.stream().filter((land) -> land.getParent().equals(base)).collect(Collectors.toList());
    }
    
    public boolean addLand(SurfaceTile newLand) {
        LOG.info("Player " + name + " acquired new land " + newLand.displayName());
        return lands.add(newLand);
    }

    public List<Building> getBuildings() {
        return buildings;
    }
    
    public boolean addBuilding(Building newBuilding) {
        LOG.info("Player " + name + " acquired new building " + newBuilding.displayName());
        return buildings.add(newBuilding);
    }

    public boolean isAi() {
        return ai;
    }

    public AIPersonality getAiPersonality() {
        return aiPersonality;
    }

    @Override
    public String toString() {
        return displayName();
    }

}
