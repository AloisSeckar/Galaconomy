package galaconomy.universe.economy;

import galaconomy.universe.ITradable;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.player.Player;
import galaconomy.universe.map.*;
import galaconomy.universe.traffic.Ship;
import galaconomy.utils.InfoUtils;
import galaconomy.utils.result.ResultBean;
import org.slf4j.*;

public class TradeHelper {
    
    private static final Logger LOG = LoggerFactory.getLogger(TradeHelper.class);
    
    public static String checkPurchase(Ship ship, Goods goods, int amount, int price) {
        String ret = checkInputs(ship, goods, amount, price);
        
        if (ret.isEmpty()) {
            
            Player buyer = ship.getCurrentOwner();
            Base seller = ship.getCurrentBase();
            
            if (buyer == null) {
                ret = "Unknown buyer!"; 
            } else if (seller == null) {
                ret = "Impossible to trade while in deep space!";
            } else if (getAvailableCredits(buyer) < amount * price) {
                ret = "Not enough credits available!";
            } else if (getAvailableSupplies(seller, goods) < amount) {
                ret = "Not enough goods available!";
            } else if (getAvailableCargoSpace(ship) < amount) {
                ret = "Not enough room in ship's cargo bay!";
            }
        }
        
        return ret;
    }
    
    public static String checkSale(Ship ship, Cargo cargo, int price) {
        String ret = checkInputs(ship, cargo, price);
        
        if (ret.isEmpty()) {
            
            Player seller = ship.getCurrentOwner();
            Star buyer = ship.getCurrentSystem();
            
            if (seller == null) {
                ret = "Unknown seller!"; 
            } else if (buyer == null) {
                ret = "Impossible to trade while in deep space!";
            }
        }
        
        return ret;
    }
    
    public static ResultBean tradeAsset(ITradable asset, Player newOwner) {
        ResultBean ret = new ResultBean(false, "Operation wasn't performed");
        
        try {
            if (asset != null) {
                if (newOwner != null) {
                    Player currentOwner = asset.getCurrentOwner();
                    if (currentOwner != null) {
                        int price = asset.getPrice();
                        if (newOwner.getCredits() >= price) {
                            asset.changeOwner(newOwner);
                            newOwner.spendCredits(price);
                            currentOwner.earnCredits(price);
                            logTrade(asset.getIdentity(), currentOwner.displayName(), newOwner.displayName(), price);
                            ret.setSuccess(true);
                        } else {
                            ret.setMessage("Insufficient credits");
                        }
                    } else {
                        ret.setMessage("Current owner is NULL");
                    }
                } else {
                    ret.setMessage("Given new owner is NULL");
                }
            } else {
                ret.setMessage("Given asset is NULL");
            }
        } catch (Exception ex) {
            LOG.error("TradeHelper.tradeAsset", ex);
            ret.setMessage(InfoUtils.getErrorMessage(ex));
        }
        
        return ret;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private static String checkInputs(Ship ship, Goods goods, int amount, int price) {
        String ret = "";
        
        if (ship == null) {
            ret = "Target ship not specified!";
        } else if (goods == null) {
            ret = "Target goods not specified!";
        } else if (amount < 1) {
            ret = "Target amount not specified!";
        } else if (price < 0) {
            ret = "Target price not specified!";
        }
        
        return ret;
    }
    
    private static String checkInputs(Ship ship, Cargo cargo, int price) {
        String ret = "";
        
        if (ship == null) {
            ret = "Target ship not specified!";
        } else if (cargo == null) {
            ret = "Target cargo not specified!";
        } else if (price < 0) {
            ret = "Target price not specified!";
        }
        
        return ret;
    }
    
    private static long getAvailableCredits(Player buyer) {
        long ret = 0;
        
        if (buyer != null) {
            ret = buyer.getCredits();
        }
        
        return ret;
    }

    private static int getAvailableSupplies(Base seller, Goods goods) {
        int ret = 0;
        
        Supplies currentSupply = seller.findSupplies(goods.displayName());
        if (currentSupply != null) {
            ret = currentSupply.getAmount();
        }
        
        return ret;
    }

    private static int getAvailableCargoSpace(Ship ship) {
        int ret = ship.getCargo();
        
        for (Cargo cargoItem : ship.getCargoList()) {
            ret -= cargoItem.getAmount();
        }
        
        return ret;
    }
    
    private static void logTrade(String asset, String currentOwner, String newOwner, int price) {
        StringBuilder sb = new StringBuilder(UniverseManager.getInstance().getStellarTimeString());
        sb.append(" - Asset trade: ").append(asset);
        sb.append(" was sold by ").append(currentOwner);
        sb.append(" to ").append(newOwner);
        sb.append(" for ").append(price).append(" credits");
        
        LOG.info(sb.toString());
    }
}
