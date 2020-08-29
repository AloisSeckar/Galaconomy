package galaconomy.utils;

import galaconomy.universe.IStorage;
import galaconomy.universe.economy.*;
import galaconomy.utils.result.ResultBean;
import org.slf4j.*;

public class StorageUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(StorageUtils.class);
    
    public static ResultBean storeCargo(Cargo cargo, IStorage storage) {
        ResultBean ret = ResultBean.createDefaultBean();
        
        try {
            if (storage != null) {
                if (cargo != null) {
                    int amountToStore = cargo.getAmount();
                    if (storage.getStorageCapacity() < amountToStore) {
                        String cargoIdentity = cargo.getIdentity();

                        Cargo previouslyStored = storage.get(cargoIdentity);
                        if (previouslyStored != null) {
                            previouslyStored.increaseAmount(amountToStore);
                        } else {
                            cargo.setLocation(storage);
                            cargo.setOwner(storage.getStorageOwner());
                            storage.put(cargoIdentity, cargo);
                        }

                        ret.setSuccess();
                    } else {
                        ret.setMessage("Insufficient capacity");
                    }
                } else {
                ret.setMessage("Invalid input - cargo == null");
                }
            } else {
                ret.setMessage("Invalid input - storage == null");
            }
        } catch (Exception ex) {
            LOG.error("Building.withdrawCargo", ex);
            ret.setMessage(InfoUtils.getErrorMessage(ex));
        }
        
        return ret;
    }
    
    public static ResultBean withdrawCargo(Goods goods, int amount, IStorage storage) {
        ResultBean ret = ResultBean.createDefaultBean();

        try {
            if (storage != null) {
                if (goods != null) {
                    if (amount > 0) {
                        int amountToWithdraw = 0;
                        String cargoIdentity = goods.displayName();

                        Cargo currentSupply = storage.get(cargoIdentity);
                        if ((currentSupply) != null) {
                            amountToWithdraw = Math.min(amount, currentSupply.getAmount());
                            currentSupply.decreaseAmount(amount);
                            if (currentSupply.getAmount() < 1) {
                                storage.remove(cargoIdentity);
                            }
                        }

                        Cargo withdraval = new Cargo(goods, amountToWithdraw);

                        ret.setReturnObject(withdraval);
                        ret.setSuccess();
                    } else {
                        ret.setMessage("Invalid input - amount < 1");
                    }
                } else {
                    ret.setMessage("Invalid input - goods == null");
                }
            } else {
                ret.setMessage("Invalid input - storage == null");
            }
        } catch (Exception ex) {
            LOG.error("StorageUtils.withdrawCargo", ex);
            ret.setMessage(InfoUtils.getErrorMessage(ex));
        }

        return ret;
    }
    
}
