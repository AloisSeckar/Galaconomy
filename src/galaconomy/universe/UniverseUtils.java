package galaconomy.universe;

import galaconomy.universe.map.Connector;
import galaconomy.universe.map.RiftGate;
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
    
    public static Color getStarColor(int starType) {
        Color ret = Color.YELLOW;
        
        switch (starType) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                ret = Color.CYAN;
                break;
            case 9:
            case 10:
            case 11:
            case 12:
                ret = Color.ORANGE;
                break;
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
                ret = Color.RED;
                break;
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
                ret = Color.WHITE;
                break;
            case 29:
            case 30:
            case 31:
            case 32:
                ret = Color.YELLOW;
                break;
            case 33:
            case 34:
                ret = Color.GREEN;
                break;
            case 35:
            case 36:
                ret = Color.MAGENTA;
                break;
        }
        
        return ret;
    }
    
    public static Star getRandomSystem() {
        
        List<Star> systems = new ArrayList<>( UniverseManager.getInstance().getStars().values());
        
        Random rand = new Random(System.currentTimeMillis()); 
        int random = rand.nextInt(systems.size());
       
        return systems.get(random);
    }
    
    public static boolean hasRiftConnection(Star from, Star to) {
        boolean ret = false;
        
        if (from != null && to != null && !from.equals(to)) {
            for (Connector gate : UniverseManager.getInstance().getGates()) {
                Star point1 = gate.getPoint1();
                Star point2 = gate.getPoint2();
                if ((point1.equals(from) || point1.equals(to)) && (point2.equals(from) || point2.equals(to))) {
                    ret = true;
                    break;
                }
            }
        }
        
        return ret;
    }

}
