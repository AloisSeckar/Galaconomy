package galaconomy.universe;

import galaconomy.constants.Constants;
import galaconomy.db.DBManager;
import galaconomy.universe.economy.Goods;
import galaconomy.universe.economy.Supplies;
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
            
            Random rand = new Random(System.currentTimeMillis()); 
        
            universeManager.resetUniverse();
            
            Player humanPlayer = new Player("Human player", "Insert your text here...", Constants.PLAYERS_FOLDER + "player01.png", Color.GREEN, false);
            universeManager.updatePlayer(humanPlayer);
        
            Player centralAI = new Player("GLC AI", "Computer of Galactic League Command", Constants.PLAYERS_FOLDER + "player00.png", Color.CYAN, true);
            universeManager.addAIPlayer(centralAI);
            
            List<Goods> availableGoods = Goods.getAvailableGoods();
            
            Star sicopiaSystem = new Star("Sicopia", "Home world", Constants.STARS_FOLDER + "sicopia.jpg", Color.ORANGE, 45, 45);
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(0), rand.nextInt(5000) + 1));
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(1), rand.nextInt(5000) + 1));
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(2), rand.nextInt(5000) + 1));
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(3), rand.nextInt(5000) + 1));
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(4), rand.nextInt(5000) + 1));
            universeManager.addStar(sicopiaSystem);
            
            centralAI.addShip(new Ship("GLS Alpha", ShipGenerator.getRandomShipClass(rand), sicopiaSystem));
            centralAI.addShip(new Ship("GLS Beta", ShipGenerator.getRandomShipClass(rand), sicopiaSystem));
            centralAI.addShip(new Ship("GLS Gama", ShipGenerator.getRandomShipClass(rand), sicopiaSystem));
            centralAI.addShip(new Ship("GLS Delta", ShipGenerator.getRandomShipClass(rand), sicopiaSystem));
            centralAI.addShip(new Ship("GLS Epsilon", ShipGenerator.getRandomShipClass(rand), sicopiaSystem));

            List<String> names = DBManager.getInstance().getAvailableStarNames();

            int numberOfStars = Math.max(rand.nextInt(names.size() / 2), 15);

            for (int i = 0; i < numberOfStars; i++) {
                String starName = names.get(rand.nextInt(names.size()));
                String starImg = Constants.STARS_FOLDER + "star" + UniverseUtils.getRandomImageOrder(rand, Constants.AVAILABLE_STARS) + ".jpg";

                Star newStar = new Star(starName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta. Seldo súriquessë lalantila nil satto tyelca combë yualë aini telimbectar elda. Celma iltániel fëa laiquë eldanyárë vórëa am.", starImg, UniverseUtils.getRandomColor(rand.nextInt(12)), rand.nextInt(Constants.MAX_X), rand.nextInt(Constants.MAX_Y));

                newStar.updateSupplies(new Supplies(availableGoods.get(0), rand.nextInt(2000) + 1));
                newStar.updateSupplies(new Supplies(availableGoods.get(1), rand.nextInt(2000) + 1));
                newStar.updateSupplies(new Supplies(availableGoods.get(2), rand.nextInt(2000) + 1));
                newStar.updateSupplies(new Supplies(availableGoods.get(3), rand.nextInt(2000) + 1));
                newStar.updateSupplies(new Supplies(availableGoods.get(4), rand.nextInt(2000) + 1));
                
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
