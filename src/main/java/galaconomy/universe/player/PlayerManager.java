package galaconomy.universe.player;

import galaconomy.universe.map.*;
import galaconomy.universe.*;
import galaconomy.universe.building.Building;
import galaconomy.universe.economy.*;
import galaconomy.universe.traffic.*;
import galaconomy.utils.result.ResultBean;
import java.util.*;
import org.slf4j.*;

public class PlayerManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(PlayerManager.class);
    
    private static final Random rand = new Random();
    
    public static void rethinkPurchases(Map<String, Player> players) {
        players.values().forEach((player) -> {
            try {
                AIPersonality personality = player.getAiPersonality();

                // TODO include priorities

                AIStrategySet landsStrategy = personality.getLandsStrategy();
                if (landsStrategy.isFocus()) {
                    switch (landsStrategy.getBuyStrategy()) {
                        case RANDOM:
                        case IF_AFFORDABLE:
                            SurfaceTile land;
                            switch (landsStrategy.getLocationStrategy()) {
                                case FOCUS_ON_BASE:
                                    land = UniverseUtils.getRandomFreeTile(player.getHomeBase());
                                    break;
                                case RANDOM:
                                    land = UniverseUtils.getRandomFreeTile(null);
                                    break;
                                default:
                                    land = null;
                            }
                            if (land != null && land.getPrice() <= player.getCredits()) {
                                TradeHelper.tradeAsset(land, player);
                                player.addLand(land);
                            }
                            break;
                    }
                }

                AIStrategySet buildingsStrategy = personality.getBuildingsStrategy();
                if (buildingsStrategy.isFocus()) {
                    switch (buildingsStrategy.getBuyStrategy()) {
                        case RANDOM:
                        case IF_AFFORDABLE:
                            List<SurfaceTile> playerLands;
                            if (buildingsStrategy.getLocationStrategy() == AIStrategy.FOCUS_ON_BASE) {
                                playerLands = player.getLands(player.getHomeBase());
                            } else {
                                playerLands = player.getLands();
                            }
                            SurfaceTile land = UniverseUtils.getFreeTileIfExists(playerLands);
                            if (land != null) {
                                Building building = GLSFactory.deliverBuilding(Building.FACTORY);
                                if (building.getPrice() <= player.getCredits()) {
                                    ResultBean tradeResult = TradeHelper.tradeAsset(building, player);
                                    if (tradeResult.isSuccess()) {
                                        building.setParent((Base) land.getParent());
                                        land.setBuilding(building);
                                        player.addBuilding(building);
                                    } else {
                                        LOG.debug("PlayerManager:rethinkPurchases - purchase failed: " + tradeResult.getMessage());
                                    }
                                }
                            }
                                   
                            break;
                    }
                }

                AIStrategySet shipsStrategy = personality.getShipsStrategy();
                if (shipsStrategy.isFocus()) {
                    switch (shipsStrategy.getBuyStrategy()) {
                        case RANDOM:
                        case IF_AFFORDABLE:
                            ShipClass randomShipClass = ShipGenerator.getRandomShipClass(rand);
                            if (randomShipClass.getPrice() <= player.getCredits()) {
                                if (shipsStrategy.getLocationStrategy() == AIStrategy.FOCUS_ON_BASE) {
                                    player.addShip(new Ship("GLS " + Math.abs(rand.nextInt()), randomShipClass, player.getHomeBase()));
                                } else {
                                    player.addShip(new Ship("GLS " + Math.abs(rand.nextInt()), randomShipClass, UniverseUtils.getRandomBase()));
                                }
                            }
                            break;
                    }
                }
            } catch (Exception ex) {
                LOG.error("PlayerManager:rethinkPurchases", ex);
            }
        });
    }
    
    public static List<Travel> rethinkTravels(Map<String, Player> players) {
        List<Travel> newTravels = new ArrayList<>();
        
        players.values().stream().filter((player) -> player.getAiPersonality().getShipsStrategy().isFocus()).forEach((player) -> {
            player.getShips().stream().filter((ship) -> ship.isIdle()).forEach((ship) -> {
                try {
                    Base location = ship.getCurrentBase();
                    Base destination = null;

                    // TODO apply personality for trading
                    while (!ship.getCargoList().isEmpty()) {
                        Cargo cargo = ship.getCargoList().get(0);
                        Supplies supply = location.findSupplies(cargo.getGoods().displayName());
                        ship.performSale(ship.getCargoList().get(0), supply.getPriceBuy());
                    }

                    // TODO apply personality for route planning
                    Supplies itemToBuy = EconomyHelper.findLowestPrice(location);
                    if (itemToBuy != null) {
                        Goods goodsToBuy = itemToBuy.getGoods();

                        int available = itemToBuy.getAmount();
                        int price = itemToBuy.getPriceSell();
                        int capacity = ship.getCargo();
                        long credits = ship.getCurrentOwner().getCredits();

                        int amount;
                        if (price * capacity <= credits) {
                            amount = Math.min(capacity, available);
                        } else {
                            amount = Math.min(new Long(credits / price).intValue(), available);
                        }

                        ship.performPurchase(goodsToBuy, amount, price);

                        destination = EconomyHelper.findBestPrice(goodsToBuy);
                        if (location.equals(destination)) {
                            destination = null;
                        }
                    } 

                    if (destination == null) {
                        destination = UniverseUtils.getRandomBase();
                    }

                    Travel newTravel = new Travel(ship, location, destination);
                    newTravels.add(newTravel);

                    logRoute(ship.displayName(), location.displayName(), destination.displayName());
                } catch (Exception ex) {
                    LOG.error("PlayerManager:rethinkTravels", ex);
                }
            });
        });
        
        return newTravels;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private static void logRoute(String ship, String location, String destination) {
        StringBuilder sb = new StringBuilder(UniverseManager.getInstance().getStellarTimeString());
        sb.append(" - Route start: ").append(ship);
        sb.append(" set off from ").append(location);
        sb.append(" to ").append(destination);
        
        LOG.info(sb.toString());
    }
}
