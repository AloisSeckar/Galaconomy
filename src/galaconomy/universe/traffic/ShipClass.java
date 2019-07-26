package galaconomy.universe.traffic;

import galaconomy.universe.IDisplayable;
import galaconomy.utils.DisplayUtils;

public class ShipClass implements IDisplayable {
    
    private final String name;
    private final String dscr;
    private final String img;
    
    private final int price;
    private final int upkeep;
    
    private final int hull;
    private final int cargo;
    private final double speed;

    public ShipClass(String name, String dscr, String img, int price, int upkeep, int hull, int cargo, double speed) {
        this.name = name;
        this.dscr = dscr;
        this.img = img;
        
        this.price = price;
        this.upkeep = upkeep;
        
        this.hull = hull;
        this.cargo = cargo;
        this.speed = speed;
    }

    @Override
    public String displayName() {
        return name;
    }

    @Override
    public String displayDscr() {
        StringBuilder shipDscr = new StringBuilder();
        
        shipDscr.append("Price: ").append(price).append("\n");
        shipDscr.append("Upkeep: ").append(upkeep).append("\n");
        shipDscr.append("Hull: ").append(hull).append("\n");
        shipDscr.append("Cargo: ").append(cargo).append("\n");
        shipDscr.append("Speed: ").append(DisplayUtils.formatDouble(speed)).append("\n\n");
        
        shipDscr.append(dscr);
        
        return shipDscr.toString();
    }

    @Override
    public String getImage() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getDscr() {
        return dscr;
    }

    public int getPrice() {
        return price;
    }

    public int getUpkeep() {
        return upkeep;
    }

    public int getHull() {
        return hull;
    }

    public int getCargo() {
        return cargo;
    }

    public double getSpeed() {
        return speed;
    }
}
