package galaconomy.universe;

import galaconomy.constants.Constants;
import galaconomy.db.DBManager;
import galaconomy.universe.systems.*;
import galaconomy.universe.traffic.*;
import galaconomy.utils.MathUtils;
import java.awt.Color;
import java.util.*;

public class UniverseGenerator {
    
    private static final int STARS = 20;
    private static final int PLANETS = 42;
    
    public static boolean generate(UniverseManager universeManager) {
        universeManager.resetUniverse();
        
        Random rand = new Random(8472); 
        
        List<String> names = DBManager.getInstance().getAvailableStarNames();
        
        int numberOfStars = Math.max(rand.nextInt(names.size() / 2), 15);
        
        for (int i = 0; i < numberOfStars; i++) {
            try {
                rand.setSeed(System.currentTimeMillis());
                Thread.sleep((long) 1);
                
                String starName = names.get(rand.nextInt(names.size()));
                String starImg = Constants.STARS_FOLDER + "star" + getRandomImageOrder(rand, STARS) + ".jpg";

                StarSystem newStar = new StarSystem(starName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta. Seldo súriquessë lalantila nil satto tyelca combë yualë aini telimbectar elda. Celma iltániel fëa laiquë eldanyárë vórëa am.", starImg, getRandomColor(rand.nextInt(12)), rand.nextInt(Constants.MAX_X), rand.nextInt(Constants.MAX_Y));
                
                int numberOfPlanets = rand.nextInt(13);
                for (int j = 1; j <= numberOfPlanets; j++) {
                    String planetName = newStar.getName() + " " + getPlanetOrder(j);
                    String planetImg = Constants.PLANETS_FOLDER + "planet" + getRandomImageOrder(rand, PLANETS) + ".jpg";
                    
                    newStar.addStellarObject(new StellarObject(planetName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta.", planetImg, getRandomColor(rand.nextInt(12)), rand.nextInt(Constants.MAX_X), rand.nextInt(Constants.MAX_Y)));
                }
                
                universeManager.addStarSystem(newStar);
                names.remove(starName);
            } catch (Exception ex) {
                System.out.println("UniverseGenerator - systems: " + ex.getMessage());
            }
        }
        
        universeManager.addShip(new Ship("GLS Alpha", "Test ship 1", Constants.SHIPS_FOLDER + "ship01.jpg", 2d));
        universeManager.addShip(new Ship("GLS Beta", "Test ship 2",  Constants.SHIPS_FOLDER + "ship02.jpg", 1.7d));
        universeManager.addShip(new Ship("GLS Gama", "Test ship 3",  Constants.SHIPS_FOLDER + "ship03.jpg", 4d));
        universeManager.addShip(new Ship("GLS Delta", "Test ship 4",  Constants.SHIPS_FOLDER + "ship04.jpg", 2.25d));
        universeManager.addShip(new Ship("GLS Epsilon", "Test ship 5",  Constants.SHIPS_FOLDER + "ship05.jpg", 1.85d));
        
        List<Ship> ships = universeManager.getShips();
        List<StarSystem> systems = universeManager.getStarSystems();
        int maxInt = systems.size();
        for (int i = 0; i < ships.size() ; i++) {
            try {
                rand.setSeed(System.currentTimeMillis());
                Thread.sleep((long) 1);
                
                StarSystem departure = systems.get(rand.nextInt(maxInt));
                StarSystem arrival = systems.get(rand.nextInt(maxInt));
                
                double distance = MathUtils.getDistance(departure.getX(), departure.getY(), arrival.getX(), arrival.getY());
                
                ShipRoute newRoute = new ShipRoute(ships.get(i), departure, arrival, distance, 0);
                universeManager.addShipRoute(newRoute);
                
            } catch (Exception ex) {
                System.out.println("UniverseGenerator - ships: " + ex.getMessage());
            }
        }
        
        return true;
    }
    
    ////////////////////////////////////////////////////////////////////////////

    private static String getPlanetOrder(int order) {
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
    
    private static Color getRandomColor(int color) {
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

    private static String getRandomImageOrder(Random rand, int options) {
        String ret = "";
        
        int randValue = rand.nextInt(options) + 1;
        if (randValue < 10) {
            ret += "0";
        }
        ret += String.valueOf(randValue);
        
        return ret;
    }
    
}
