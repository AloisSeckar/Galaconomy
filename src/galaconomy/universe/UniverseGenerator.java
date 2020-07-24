package galaconomy.universe;

import galaconomy.constants.Constants;
import galaconomy.db.DBManager;
import galaconomy.universe.economy.*;
import galaconomy.universe.map.*;
import galaconomy.universe.player.Player;
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
            
            Star sicopiaSystem = new Star("Sicopia", "Home world", Constants.STARS_FOLDER + "star09.png", Color.ORANGE, 45, 45);
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(0), rand.nextInt(5000) + 1));
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(1), rand.nextInt(5000) + 1));
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(2), rand.nextInt(5000) + 1));
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(3), rand.nextInt(5000) + 1));
            sicopiaSystem.updateSupplies(new Supplies(availableGoods.get(4), rand.nextInt(5000) + 1));
            universeManager.addStar(sicopiaSystem);

            List<String> names = DBManager.getInstance().getAvailableStarNames();

            int numberOfStars = Math.max(rand.nextInt(names.size() / 2), 15);

            for (int i = 0; i < numberOfStars; i++) {
                String starName = names.get(rand.nextInt(names.size()));
                
                int randStarImg = rand.nextInt(Constants.AVAILABLE_STARS) + 1;
                String starImg = Constants.STARS_FOLDER + "star" + String.format("%02d" , randStarImg) + ".png";

                Star newStar = new Star(starName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta. Seldo súriquessë lalantila nil satto tyelca combë yualë aini telimbectar elda. Celma iltániel fëa laiquë eldanyárë vórëa am.", starImg, UniverseUtils.getStarColor(randStarImg), rand.nextInt(Constants.MAX_X), rand.nextInt(Constants.MAX_Y));

                newStar.updateSupplies(new Supplies(availableGoods.get(0), rand.nextInt(2000) + 1));
                newStar.updateSupplies(new Supplies(availableGoods.get(1), rand.nextInt(2000) + 1));
                newStar.updateSupplies(new Supplies(availableGoods.get(2), rand.nextInt(2000) + 1));
                newStar.updateSupplies(new Supplies(availableGoods.get(3), rand.nextInt(2000) + 1));
                newStar.updateSupplies(new Supplies(availableGoods.get(4), rand.nextInt(2000) + 1));
                
                int numberOfPlanets = rand.nextInt(5); 
                if (rand.nextInt(10) % 2 == 0) {
                    numberOfPlanets += rand.nextInt(5);
                }
                if (rand.nextInt(10) % 3 == 0) {
                    numberOfPlanets += rand.nextInt(5);
                }
                int centerX = Constants.MAX_X / 2 + 1;
                int centerY = Constants.MAX_Y / 2 + 1;
                for (int j = 1; j <= numberOfPlanets; j++) {
                    String planetName = newStar.getName() + " " + UniverseUtils.getPlanetOrder(j);
                    
                    int randPlanetImg = rand.nextInt(Constants.AVAILABLE_PLANETS) + 1;
                    String planetImg = Constants.PLANETS_FOLDER + "planet" + String.format("%02d" , randPlanetImg) + ".png";

                    // x = r * sin(θ), y = r * cos(θ)
                    int angle = rand.nextInt(360);
                    double radius = 9 + (1 + rand.nextDouble() * 2) * j;
                    Double randomX = centerX + (radius * Math.sin(angle)); 
                    Double randomY = centerY + (radius * Math.cos(angle));
                    
                    int xCoord = Math.min(randomX.intValue(), Constants.MAX_X);
                    int yCoord = Math.min(randomY.intValue(), Constants.MAX_Y);
                    
                    newStar.addStellarObject(new StellarObject(planetName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta.", planetImg, Color.WHITE, xCoord, yCoord));
                }
                
                if (rand.nextInt() % 2 == 0) {
                    newStar.setRiftPortal(new RiftPortal("Rift portal", "Can be used to travel to any other system", Constants.MAX_X / 2 + 1, 5));
                }
                
                universeManager.addStar(newStar);
                names.remove(starName);
            }
            
            List<Star> systems = new ArrayList<>( UniverseManager.getInstance().getStars().values());
            int maxInt = systems.size();
            
            int numberOfGates = (rand.nextInt(maxInt) + 1) * 2; 
            for (int j = 1; j <= numberOfGates; j++) {
                Star randomOrigin;
                Star randomDestination;
                do {
                    randomOrigin = UniverseUtils.getRandomSystem();
                    do {
                        randomDestination = UniverseUtils.getRandomSystem();
                    } while (randomOrigin.equals(randomDestination));
                } while (UniverseUtils.hasRiftConnection(randomOrigin, randomDestination));

                String riftGateName1 = "Rift gate to " + randomDestination.getName();
                String riftGateName2 = "Rift gate to " + randomOrigin.getName();
                
                int xCoord = 10 * j;
                int yCoord = 12;
                
                randomOrigin.addRiftGate(new RiftGate(riftGateName1, "A connector betwen two systems", xCoord, yCoord, randomDestination));
                randomDestination.addRiftGate(new RiftGate(riftGateName2, "A connector betwen two systems", xCoord, yCoord, randomOrigin));
            }
            
            centralAI.addShip(new Ship("GLS Alpha", ShipGenerator.getRandomShipClass(rand), systems.get(rand.nextInt(maxInt))));
            centralAI.addShip(new Ship("GLS Beta", ShipGenerator.getRandomShipClass(rand), systems.get(rand.nextInt(maxInt))));
            centralAI.addShip(new Ship("GLS Gama", ShipGenerator.getRandomShipClass(rand), systems.get(rand.nextInt(maxInt))));
            centralAI.addShip(new Ship("GLS Delta", ShipGenerator.getRandomShipClass(rand), systems.get(rand.nextInt(maxInt))));
            centralAI.addShip(new Ship("GLS Epsilon", ShipGenerator.getRandomShipClass(rand), systems.get(rand.nextInt(maxInt))));
            
            LOG.info("New universe generating finished");
            ret = true;
            
        } catch (Exception ex) {
             LOG.error("UniverseGenerator:generate" + ex.getMessage());
        }
        
        return ret;
    }
}
