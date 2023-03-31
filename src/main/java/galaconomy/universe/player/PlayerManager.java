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
    
    private PlayerManager() {
    }
    
    public static void rethinkPurchases(Player player) {
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
                                land = UniverseUtils.getRandomFreeTileIfPossible(player.getHomeBase());
                                break;
                            case RANDOM:
                                land = UniverseUtils.getRandomFreeTileIfPossible(null);
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
                                    building.setParent(land);
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
    }
    
    public static List<Travel> rethinkTravels(Player player) {
        List<Travel> newTravels = new ArrayList<>();
        
        if (player.getAiPersonality().getShipsStrategy().isFocus()) {
            player.getShips().stream().filter((ship) -> ship.isIdle()).forEach((ship) -> {
                try {
                    Base location = ship.getCurrentBase();
                    Base destination = null;

                    // TODO apply personality for trading
                    // TODO fix ship trading to follow standards
                    String status = "OK";
                    while (!ship.getCurrentCargo().isEmpty() && "OK".equals(status)) {
                        status = ship.performSale(ship.getCurrentCargo().get(0), new Random().nextInt(1000) + 25); // TODO eliminate this random
                    }

                    // TODO apply personality for route planning
                    Supplies itemToBuy = EconomyHelper.findLowestPrice(location);
                    if (itemToBuy != null) {
                        Goods goodsToBuy = itemToBuy.getCargo().getGoods();

                        int available = itemToBuy.getCargo().getAmount();
                        int price = itemToBuy.getPriceSell();
                        int capacity = ship.getCargoCapacity();
                        long credits = ship.getCurrentOwner().getCredits();

                        int amount;
                        if (price * capacity <= credits) {
                            amount = Math.min(capacity, available);
                        } else {
                            amount = Math.min(Long.valueOf(credits / price).intValue(), available);
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
        }
        
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
