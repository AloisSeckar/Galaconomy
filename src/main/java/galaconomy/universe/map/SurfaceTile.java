package galaconomy.universe.map;

import galaconomy.universe.*;
import galaconomy.universe.building.Building;
import galaconomy.universe.economy.*;
import galaconomy.universe.player.Player;
import galaconomy.utils.*;
import galaconomy.utils.result.ResultBean;
import java.awt.Color;
import java.util.*;

public class SurfaceTile extends AbstractMapElement implements ITradable, IStorage {

    private final HashMap<String, Cargo> rawMaterials = new HashMap<>();

    private Building building = null;

    private int price;
    private Player currentOwner;
    private final List<Player> owners = new ArrayList<>();

    public SurfaceTile(String name, String dscr, String img, Color color, int xCoord, int yCoord, Base parent, Player owner) throws Exception {
        super(name, dscr, img, color, xCoord, yCoord, parent);
        this.currentOwner = owner;
        this.price = 100;
    }

    @Override
    public String displayName() {
        return getParent().getName() + DisplayUtils.getCoordinates(getX(), getY());
    }

    @Override
    public String displayDscr() {
        StringBuilder tileDscr = new StringBuilder();

        tileDscr.append("Owner: ");
        if (currentOwner != null) {
            tileDscr.append(currentOwner.displayName());
        }
        tileDscr.append("\n\n");

        tileDscr.append("RAW MATERIALS").append("\n");
        tileDscr.append("----------").append("\n");
        rawMaterials.values().forEach((goods) -> {
            tileDscr.append(goods.displayName()).append("\n");
        });
        tileDscr.append("\n");

        tileDscr.append("INFO").append("\n");
        tileDscr.append("----------").append("\n");
        tileDscr.append(super.displayDscr());

        return tileDscr.toString();
    }

    @Override
    public int getPrice() {
        return price;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public Player getCurrentOwner() {
        return currentOwner;
    }

    @Override
    public List<Player> getOwners() {
        return owners;
    }

    @Override
    public void changeOwner(Player newOwner) {
        owners.add(0, newOwner);
        currentOwner = newOwner;
    }

    @Override
    public String getStorageIdentity() {
        return displayName();
    }
    
    @Override
    public int getStorageCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public Player getStorageOwner() {
        return UniverseManager.getInstance().getGLSPlayer();
    }

    @Override
    public List<Cargo> getCurrentCargo() {
        List<Cargo> ret = new ArrayList<>();
        ret.addAll(rawMaterials.values());
        return ret;
    }

    @Override
    public ResultBean storeCargo(Cargo cargo) {
        return new ResultBean(false, "SurfaceTile doesn't support storing");
    }

    @Override
    public ResultBean withdrawCargo(Goods goods, int amount) {
        return StorageUtils.withdrawCargo(goods, amount, this);
    }

    @Override
    public Cargo get(String key) {
         return rawMaterials.get(key);
    }

    @Override
    public Cargo put(String key, Cargo cargo) {
         return rawMaterials.put(key, cargo);
    }

    @Override
    public Cargo remove(String key) {
         return rawMaterials.remove(key);
    }

    public boolean isEmpty() {
        return building == null;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
    
    public void randomizeRawMaterials() {
        Random rand = new Random();
        Goods.getRawMaterials().forEach(goods -> {
            //if (rand.nextBoolean()) {
                Cargo rawMaterial = new Cargo(goods, rand.nextInt(9) * rand.nextInt(100000) + rand.nextInt(100000));
                rawMaterials.put(goods.getIdentity(), rawMaterial);
            //}
        });
    }

}
