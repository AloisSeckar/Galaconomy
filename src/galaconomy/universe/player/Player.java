package galaconomy.universe.player;

import galaconomy.constants.Constants;
import galaconomy.universe.IDisplayable;
import galaconomy.universe.traffic.Ship;
import java.util.*;
import java.awt.Color;

public class Player implements IDisplayable {
    
    private final String name;
    private final String dscr;
    private final String img;
    private final Color color;
    
    private long credits;
    
    private final List<Ship> ships = new ArrayList<>();

    public Player(String name, String dscr, String img,  Color color) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        this.color = color;
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

    public long getCredits() {
        return credits;
    }
    
    public void earnCredits(long amountEarned) {
        credits += amountEarned;
    }
    
    public void spendCredits(long amountSpent) {
        credits -= amountSpent;
    }

    public List<Ship> getShips() {
        return ships;
    }
    
    public boolean addShip(Ship newShip) {
        newShip.addOwner(Player.this);
        return ships.add(newShip);
    }

}
