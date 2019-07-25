package galaconomy.universe;

import galaconomy.universe.systems.Star;
import java.util.*;

public class UniverseUtils {
    
    public static String getRandomImageOrder(Random rand, int options) {
        String ret = "";
        
        int randValue = rand.nextInt(options) + 1;
        if (randValue < 10) {
            ret += "0";
        }
        ret += String.valueOf(randValue);
        
        return ret;
    }
    
    public static Star getRandomSystem() {
        
        List<Star> systems = new ArrayList<>( UniverseManager.getInstance().getStars().values());
        
        Random rand = new Random(System.currentTimeMillis()); 
        int random = rand.nextInt(systems.size());
       
        return systems.get(random);
    }

}
