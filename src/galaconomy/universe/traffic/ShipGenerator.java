package galaconomy.universe.traffic;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import galaconomy.universe.systems.Star;
import java.util.Random;

public class ShipGenerator {
    
    public static Ship createRandomShip() {
        Random rand = new Random(System.currentTimeMillis()); 
        
        String randomName = "GLS Random " + UniverseManager.getInstance().getStellarTime();
        String randomDscr = "This will have to improve a lot soon...";
        String randomImg = Constants.SHIPS_FOLDER + "ship" + UniverseUtils.getRandomImageOrder(rand, Constants.AVAILABLE_SHIPS) + ".jpg";
        double randomSpeed = 1 + rand.nextDouble() * 4;
        Star randomStar = UniverseUtils.getRandomSystem();
        
        return new Ship(randomName, randomDscr, randomImg, randomSpeed, randomStar);
    }
    
}
