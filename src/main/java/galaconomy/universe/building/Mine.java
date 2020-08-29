package galaconomy.universe.building;

import galaconomy.constants.Constants;
import galaconomy.universe.economy.*;
import galaconomy.universe.map.SurfaceTile;
import galaconomy.universe.player.Player;
import galaconomy.universe.player.PlayerManager;
import galaconomy.utils.result.ResultBean;
import org.slf4j.*;

public class Mine extends Building {
    
    private static final Logger LOG = LoggerFactory.getLogger(PlayerManager.class);
    
    private Goods output;
    
    public Mine(SurfaceTile parent, Player owner) {
        super(MINE, "Universal platform for harvesting resources", IMG_MINE, 850, parent, owner);
        output = Goods.getRandomRawMaterial();
    }
    
    @Override
    public String displayDscr() {
        StringBuilder mineDscr = new StringBuilder();
        mineDscr.append(super.displayDscr());
        mineDscr.append("\n\n");
        
        mineDscr.append("Level:").append(getLevel()).append("\n");
        mineDscr.append("Productivity:").append(getProductivity()).append("\n");
        
        mineDscr.append("OUTPUT: ").append(output != null ? output.displayName() : Constants.NONE);
        
        return mineDscr.toString();
    }

    public Goods getOutput() {
        return output;
    }

    public void setOutput(Goods output) {
        // TODO dont allow anything but raw material
        this.output = output;
    }
    
    @Override
    public Cargo produce() {
        Cargo production = null;
        
        if (output != null) {
            SurfaceTile parent = getParent();
            if ( parent != null) {
                ResultBean withdrawal = parent.withdrawCargo(output, getProductivity());
                if (withdrawal.isSuccess()) {
                    production = (Cargo) withdrawal.getReturnObject();
                    if (production.getAmount() < 1) {
                        this.output = null;
                        LOG.info(getIdentity() + " - mine production halted: " + production.getIdentity() + " resource depleated");
                    }
                } else {
                    this.output = null;
                    LOG.info(getIdentity() + " - mine production halted: " + withdrawal.getMessage());
                }
            }
        }
        
        return production;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private int getProductivity() {
        return getLevel() * 10;
    }
    
}
