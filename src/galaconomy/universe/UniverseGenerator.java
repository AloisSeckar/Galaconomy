package galaconomy.universe;

import galaconomy.constants.Constants;
import galaconomy.db.DBManager;
import galaconomy.universe.player.Player;
import galaconomy.universe.systems.*;
import galaconomy.universe.traffic.*;
import java.awt.Color;
import java.util.*;

public class UniverseGenerator {
    
    private static final int STARS = 20;
    private static final int PLANETS = 42;
    
    public static boolean generate(UniverseManager universeManager) {
        try {
            universeManager.resetUniverse();
        
            Player centralAI = new Player("GLC AI", "Computer of Galactic League Command", Constants.PLAYERS_FOLDER + "player00.jpg", Color.CYAN, true);
            universeManager.addPlayer(centralAI);
            
            Player player = new Player("Human player", "Insert your text here...", Constants.PLAYERS_FOLDER + "player01.jpg", Color.GREEN, false);
            universeManager.addPlayer(player);
            
            Star sicopiaSystem = new Star("Sicopia", "Home world", Constants.STARS_FOLDER + "sicopia.jpg", Color.ORANGE, 45, 45);
            universeManager.addStar(sicopiaSystem);
            
            centralAI.addShip(new Ship("GLS Alpha", "Test ship 1", Constants.SHIPS_FOLDER + "ship01.jpg", 2d, sicopiaSystem));
            centralAI.addShip(new Ship("GLS Beta", "Test ship 2",  Constants.SHIPS_FOLDER + "ship02.jpg", 1.7d, sicopiaSystem));
            centralAI.addShip(new Ship("GLS Gama", "Test ship 3",  Constants.SHIPS_FOLDER + "ship03.jpg", 4d, sicopiaSystem));
            centralAI.addShip(new Ship("GLS Delta", "Test ship 4",  Constants.SHIPS_FOLDER + "ship04.jpg", 2.25d, sicopiaSystem));
            centralAI.addShip(new Ship("GLS Epsilon", "Test ship 5",  Constants.SHIPS_FOLDER + "ship05.jpg", 1.85d, sicopiaSystem));
        
            Random rand = new Random(8472); 

            List<String> names = DBManager.getInstance().getAvailableStarNames();

            int numberOfStars = Math.max(rand.nextInt(names.size() / 2), 15);

            for (int i = 0; i < numberOfStars; i++) {
                String starName = names.get(rand.nextInt(names.size()));
                String starImg = Constants.STARS_FOLDER + "star" + getRandomImageOrder(rand, STARS) + ".jpg";

                Star newStar = new Star(starName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta. Seldo súriquessë lalantila nil satto tyelca combë yualë aini telimbectar elda. Celma iltániel fëa laiquë eldanyárë vórëa am.", starImg, getRandomColor(rand.nextInt(12)), rand.nextInt(Constants.MAX_X), rand.nextInt(Constants.MAX_Y));

                int numberOfPlanets = rand.nextInt(13);
                for (int j = 1; j <= numberOfPlanets; j++) {
                    String planetName = newStar.displayName()+ " " + getPlanetOrder(j);
                    String planetImg = Constants.PLANETS_FOLDER + "planet" + getRandomImageOrder(rand, PLANETS) + ".jpg";

                    newStar.addStellarObject(new StellarObject(planetName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta.", planetImg, getRandomColor(rand.nextInt(12)), rand.nextInt(Constants.MAX_X), rand.nextInt(Constants.MAX_Y)));
                }

                universeManager.addStar(newStar);
                names.remove(starName);
            }
        } catch (Exception ex) {
            System.out.println("UniverseGenerator: " + ex.getMessage());
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
