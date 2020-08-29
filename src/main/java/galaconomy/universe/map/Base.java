package galaconomy.universe.map;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.building.*;
import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import galaconomy.utils.DisplayUtils;
import galaconomy.utils.StorageUtils;
import java.awt.Color;
import java.io.Serializable;
import java.util.*;

public class Base extends StellarObject implements Serializable {
    
    // TODO different sized bases
    public static final int COLS = DisplayUtils.getMAX_X() / DisplayUtils.BASE_TILE_SIZE;
    public static final int ROWS = DisplayUtils.getMAX_Y() / DisplayUtils.BASE_TILE_SIZE;
    
    private final SurfaceTile[][] surface = new SurfaceTile[COLS][ROWS];
    
    private final City city;
    private Shipyard shipyard;

    public Base(Star system, String name, String dscr, String img, Color color, int xCoord, int yCoord) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord, system);
        
        Player glsPlayer = UniverseManager.getInstance().getGLSPlayer();
        city = new City(this);
        shipyard = new Shipyard(this);
        
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Random rand = new Random();
                switch (rand.nextInt() % 4) {
                    case 3:
                        surface[col][row] = new SurfaceTile("Mountains", "Brown", Constants.TILES_FOLDER + "04.png", Color.GREEN, col, row, this, glsPlayer);
                        break;
                    case 2:
                        surface[col][row] = new SurfaceTile("Water", "Blue", Constants.TILES_FOLDER + "03.png", Color.GREEN, col, row, this, glsPlayer);
                        break;
                    case 1:
                        surface[col][row] = new SurfaceTile("Desert", "Yellow", Constants.TILES_FOLDER + "02.png", Color.GREEN, col, row, this, glsPlayer);
                        break;
                    default:
                        surface[col][row] = new SurfaceTile("Grass", "Green", Constants.TILES_FOLDER + "01.png", Color.GREEN, col, row, this, glsPlayer);
                }
                
                if (row == ROWS / 2 && col == COLS / 2) {
                    surface[col][row].setBuilding(city);
                }
            }
        }
    }
    
    @Override
    public String displayDscr() {
        StringBuilder baseDscr = new StringBuilder();
        
        baseDscr.append("Shipyard: ").append(shipyard != null);
        baseDscr.append("\n\n");
        
        baseDscr.append("SUPPLIES").append("\n");
        baseDscr.append("----------").append("\n");
        city.getCurrentCargo().forEach(cargo -> {
            baseDscr.append(cargo.displayName()).append("\n");
        });
        baseDscr.append("\n");
        
        baseDscr.append("INFO").append("\n");
        baseDscr.append("----------").append("\n");
        baseDscr.append(super.displayDscr());
        
        return baseDscr.toString();
    }

    public SurfaceTile[][] getSurface() {
        return surface;
    }
    
    public SurfaceTile getSurfaceTile(int xCoord, int yCoord) {
        return surface[xCoord][yCoord];
    }

    public City getCity() {
        return city;
    }
    
    public boolean isShipyard() {
        return shipyard != null;
    }

    public void setShipyard(boolean shipyard) {
        if (shipyard) {
            this.shipyard = new Shipyard(this);
        } else {
            this.shipyard = null;
        }
        surface[COLS / 2][ROWS / 2 + 1].setBuilding(this.shipyard);
    }
    
    public void performPurchase(Cargo cargo) {
        StorageUtils.storeCargo(cargo, shipyard); // TODO return and handle ResultBean
    }
    
    public void performSale(Goods goods, int amount) {
        StorageUtils.withdrawCargo(goods, amount, shipyard);
    }    

    public int countLands() {
        return COLS * ROWS;
    }

    public int countBuildings() {
        int ret = 0;
        
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (!surface[col][row].isEmpty()) {
                    // TODO GLS buildings shouldn't count...
                    ret++;
                }
            }
        }
        
        return ret;
    }

    public int countFreeLands() {
        return countLands() - countBuildings();
    }

    // TODO implement real supply/demand logic (inside "City" class)
    public Supplies findSupplies(String displayName) {
        Supplies ret = null;
        
        Cargo cargo = city.get(displayName);
        if (cargo != null) {
            Random rand = new Random();
            ret = new Supplies(cargo, rand.nextInt(1500) + 25, rand.nextInt(1500) + 25); // TODO remove "random" logic
        }
        
        return ret;
    }
    
    public List<Supplies> getSupplies() {
        List<Supplies> ret = new ArrayList();
        
        Random rand = new Random();
        city.getCurrentCargo().forEach(cargo -> {
            ret.add(new Supplies(cargo, rand.nextInt(1500) + 25, rand.nextInt(1500) + 25)); // TODO remove "random" logic
        });
        
        return ret;
    }

    public void recalcSupplies() {
        city.recalcSupplies();
    }
}
