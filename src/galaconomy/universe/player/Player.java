package galaconomy.universe.player;

import galaconomy.universe.traffic.Ship;
import java.util.*;
import java.awt.Color;

public class Player {
    
    private final String name;
    private final String img;
    private final Color color;
    
    private long credits = 10000;
    
    private final List<Ship> ships = new ArrayList<>();

    public Player(String name, String img,  Color color) {
        this.name = name;
        this.img = img;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
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
