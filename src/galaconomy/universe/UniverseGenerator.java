package galaconomy.universe;

import galaconomy.constants.Constants;
import galaconomy.db.DBManager;
import galaconomy.universe.player.Player;
import galaconomy.universe.systems.*;
import galaconomy.universe.traffic.*;
import java.awt.Color;
import java.util.*;
import org.slf4j.*;

public class UniverseGenerator {
    
    private static final Logger LOG = LoggerFactory.getLogger(UniverseGenerator.class);
    
    public static boolean generate(UniverseManager universeManager) {
        boolean ret = false;
        
        try {
            LOG.info("New universe generating started");
        
            universeManager.resetUniverse();
            
            Player humanPlayer = new Player("Human player", "Insert your text here...", Constants.PLAYERS_FOLDER + "player01.png", Color.GREEN, false);
            universeManager.updatePlayer(humanPlayer);
        
            Player centralAI = new Player("GLC AI", "Computer of Galactic League Command", Constants.PLAYERS_FOLDER + "player00.png", Color.CYAN, true);
            universeManager.addAIPlayer(centralAI);
            
            Star sicopiaSystem = new Star("Sicopia", "Home world", Constants.STARS_FOLDER + "sicopia.jpg", Color.ORANGE, 45, 45);
            universeManager.addStar(sicopiaSystem);
            
            centralAI.addShip(new Ship("GLS Alpha", "Test ship 1", Constants.SHIPS_FOLDER + "ship01.jpg", 2d, sicopiaSystem));
            centralAI.addShip(new Ship("GLS Beta", "Test ship 2",  Constants.SHIPS_FOLDER + "ship02.jpg", 1.7d, sicopiaSystem));
            centralAI.addShip(new Ship("GLS Gama", "Test ship 3",  Constants.SHIPS_FOLDER + "ship03.jpg", 4d, sicopiaSystem));
            centralAI.addShip(new Ship("GLS Delta", "Test ship 4",  Constants.SHIPS_FOLDER + "ship04.jpg", 2.25d, sicopiaSystem));
            centralAI.addShip(new Ship("GLS Epsilon", "Test ship 5",  Constants.SHIPS_FOLDER + "ship05.jpg", 1.85d, sicopiaSystem));
        
            Random rand = new Random(System.currentTimeMillis()); 

            List<String> names = DBManager.getInstance().getAvailableStarNames();

            int numberOfStars = Math.max(rand.nextInt(names.size() / 2), 15);

            for (int i = 0; i < numberOfStars; i++) {
                String starName = names.get(rand.nextInt(names.size()));
                String starImg = Constants.STARS_FOLDER + "star" + UniverseUtils.getRandomImageOrder(rand, Constants.AVAILABLE_STARS) + ".jpg";

                Star newStar = new Star(starName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta. Seldo súriquessë lalantila nil satto tyelca combë yualë aini telimbectar elda. Celma iltániel fëa laiquë eldanyárë vórëa am.", starImg, UniverseUtils.getRandomColor(rand.nextInt(12)), rand.nextInt(Constants.MAX_X), rand.nextInt(Constants.MAX_Y));

                int numberOfPlanets = rand.nextInt(13);
                for (int j = 1; j <= numberOfPlanets; j++) {
                    String planetName = newStar.displayName()+ " " + UniverseUtils.getPlanetOrder(j);
                    String planetImg = Constants.PLANETS_FOLDER + "planet" + UniverseUtils.getRandomImageOrder(rand, Constants.AVAILABLE_PLANETS) + ".jpg";

                    newStar.addStellarObject(new StellarObject(planetName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta.", planetImg, UniverseUtils.getRandomColor(rand.nextInt(12)), rand.nextInt(Constants.MAX_X), rand.nextInt(Constants.MAX_Y)));
                }

                universeManager.addStar(newStar);
                names.remove(starName);
            }
            
            LOG.info("New universe generating finished");
            ret = true;
            
        } catch (Exception ex) {
             LOG.error("UniverseGenerator:generate" + ex.getMessage());
        }
        
        return ret;
    }
}
