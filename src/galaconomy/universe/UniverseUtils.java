package galaconomy.universe;

import galaconomy.universe.map.Star;
import java.awt.Color;
import java.util.*;

public class UniverseUtils {

    public static String getPlanetOrder(int order) {
        String ret = "Zero";
        
        switch (order) {
            case 1:
                ret = "Prime";
                break;
            case 2:
                ret = "II";
                break;
            case 3:
                ret = "III";
                break;
            case 4:
                ret = "IV";
                break;
            case 5:
                ret = "V";
                break;
            case 6:
                ret = "VI";
                break;
            case 7:
                ret = "VII";
                break;
            case 8:
                ret = "VIII";
                break;
            case 9:
                ret = "IX";
                break;
            case 10:
                ret = "X";
                break;
            case 11:
                ret = "XI";
                break;
            case 12:
                ret = "XII";
                break;
        }
        
        return ret;
    }
    
    public static Color getRandomColor(int color) {
        Color ret = Color.YELLOW;
        
        switch (color) {
            case 1:
            case 8:
                ret = Color.WHITE;
                break;
            case 2:
                ret = Color.BLUE;
                break;
            case 3:
                ret = Color.RED;
                break;
            case 4:
            case 9:
                ret = Color.ORANGE;
                break;
            case 5:
                ret = Color.GREEN;
                break;
            case 6:
                ret = Color.CYAN;
                break;
            case 7:
                ret = Color.MAGENTA;
                break;
        }
        
        return ret;
    }
    
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
