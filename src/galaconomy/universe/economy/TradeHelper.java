package galaconomy.universe.economy;

import galaconomy.universe.player.Player;
import galaconomy.universe.map.Star;
import galaconomy.universe.traffic.Ship;

public class TradeHelper {
    
    public static String checkPurchase(Ship ship, Goods goods, int amount, int price) {
        String ret = checkInputs(ship, goods, amount, price);
        
        if (ret.isEmpty()) {
            
            Player buyer = ship.getCurrentOwner();
            Star seller = ship.getCurrentLocation();
            
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
            Star buyer = ship.getCurrentLocation();
            
            if (seller == null) {
                ret = "Unknown seller!"; 
            } else if (buyer == null) {
                ret = "Impossible to trade while in deep space!";
            }
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

    private static int getAvailableSupplies(Star seller, Goods goods) {
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
}
