package galaconomy.universe;

import galaconomy.constants.Constants;
import galaconomy.db.DBManager;
import galaconomy.universe.building.Building;
import galaconomy.universe.economy.*;
import galaconomy.universe.map.*;
import galaconomy.universe.player.AIFactory;
import galaconomy.universe.player.Player;
import galaconomy.universe.traffic.*;
import galaconomy.utils.DisplayUtils;
import galaconomy.utils.StorageUtils;
import galaconomy.utils.result.ResultBean;
import java.awt.Color;
import java.util.*;
import org.slf4j.*;

public class UniverseGenerator {
    
    private static final Logger LOG = LoggerFactory.getLogger(UniverseGenerator.class);
    
    private static Random rand; 
    private static List<String> availableNames; 
    private static List<Goods> availableGoods;
    private static UniverseManager universeManager;
    
    public static boolean generate() {
        boolean ret = false;
        
        try {
            LOG.info("New universe generating started");
            
            // INIT
            
            rand = new Random(); 
            availableGoods = Goods.getAvailableGoods();
            availableNames = DBManager.getInstance().getAvailableStarNames();
            universeManager = UniverseManager.getInstance();
        
            universeManager.resetUniverse();
            
            Player glsPlayer = new Player("GLS - Galactic League Service", "Insert your text here...", Constants.PLAYERS_FOLDER + "player00.png", Color.CYAN, null, true, AIFactory.AI_GLS);
            universeManager.updateGLSPlayer(glsPlayer);
            
            // GENERATED STARS
            
            Star sicopiaSystem = new Star("Sicopia", "Home world", Constants.STARS_FOLDER + "star09.png", Color.ORANGE, DisplayUtils.getMAX_X() / 2, DisplayUtils.getMAX_Y() / 2);
            
            Base sicopiaPlanet = new Base(sicopiaSystem, "Sicopia Prime", "GLS Capital", Constants.PLANETS_FOLDER + "planet52.png", Color.WHITE, 28, 35);
            sicopiaPlanet.setShipyard(true);
            
            IStorage planetaryStorage = sicopiaPlanet.getCity();
            Cargo randomCargo0 = new Cargo(availableGoods.get(0), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo0, planetaryStorage);
            Cargo randomCargo1 = new Cargo(availableGoods.get(1), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo1, planetaryStorage);
            Cargo randomCargo2 = new Cargo(availableGoods.get(2), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo2, planetaryStorage);
            Cargo randomCargo3 = new Cargo(availableGoods.get(3), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo3, planetaryStorage);
            Cargo randomCargo4 = new Cargo(availableGoods.get(4), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo4, planetaryStorage);

            sicopiaSystem.addStellarObject(sicopiaPlanet);
            
            universeManager.addStar(sicopiaSystem);
            
            // RANDOM STARS

            int numberOfStars = Math.max(rand.nextInt(availableNames.size() / 2), 20);
            for (int i = 0; i < numberOfStars; i++) {
                String starName = availableNames.get(rand.nextInt(availableNames.size()));
                Star newStar = generateSystem(starName);
                universeManager.addStar(newStar);
                availableNames.remove(starName);
            }
            
            // PLAYERS
            
            Player humanPlayer = new Player("Human player", "Insert your text here...", Constants.PLAYERS_FOLDER + "player01.png", Color.GREEN, sicopiaPlanet, false, null);
            universeManager.updatePlayer(humanPlayer);
        
            Player trader = new Player("Tom the Trader", "Demo player who focuses on ships", Constants.PLAYERS_FOLDER + "player02.png", Color.YELLOW, sicopiaPlanet, true, AIFactory.AI_STAR_TRADER);
            universeManager.addAIPlayer(trader);
            
            Player trader2 = new Player("Tanya the Trader", "Demo player who focuses on ships", Constants.PLAYERS_FOLDER + "player02.png", Color.YELLOW, sicopiaPlanet, true, AIFactory.AI_STAR_TRADER);
            universeManager.addAIPlayer(trader2);
            
            Player trader3 = new Player("Trevor the Trader", "Demo player who focuses on ships", Constants.PLAYERS_FOLDER + "player02.png", Color.YELLOW, sicopiaPlanet, true, AIFactory.AI_STAR_TRADER);
            universeManager.addAIPlayer(trader3);
            
            Player builder = new Player("Bob the Builder", "Demo player who focuses on buildings", Constants.PLAYERS_FOLDER + "player03.png", Color.ORANGE, sicopiaPlanet, true, AIFactory.AI_BUILDER);
            universeManager.addAIPlayer(builder);
            
            Player builder2 = new Player("Barbara the Builder", "Demo player who focuses on buildings", Constants.PLAYERS_FOLDER + "player03.png", Color.ORANGE, sicopiaPlanet, true, AIFactory.AI_BUILDER);
            universeManager.addAIPlayer(builder2);
            
            Player builder3 = new Player("Bruno the Builder", "Demo player who focuses on buildings", Constants.PLAYERS_FOLDER + "player03.png", Color.ORANGE, sicopiaPlanet, true, AIFactory.AI_BUILDER);
            universeManager.addAIPlayer(builder3);
            
            Player landlord = new Player("Luke the Landlord", "Demo player who focuses on lands", Constants.PLAYERS_FOLDER + "player04.png", Color.MAGENTA, sicopiaPlanet, true, AIFactory.AI_LANDLORD);
            universeManager.addAIPlayer(landlord);
            
            // SHIPS
            // TODO demo only
            
            trader.earnCredits(1000000);
            trader2.earnCredits(1000000);
            trader3.earnCredits(1000000);
            
            trader.addShip(new Ship("GLS Alpha", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Beta", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Gama", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Delta", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Epsilon", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Zeta", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Eta", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Theta", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Iota", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Kappa", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Lambda", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Mu", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Nu", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Xi", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Omicron", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Pi", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Rho", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Sigma", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Tau", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Upsilon", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Phi", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Chi", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Psi", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            trader.addShip(new Ship("GLS Omega", ShipGenerator.getRandomShipClass(rand), UniverseUtils.getRandomBase()));
            
            // BUILDINGS
            // TODO demo only
            
            builder.earnCredits(10000);
            builder2.earnCredits(10000);
            builder3.earnCredits(10000);
            
            for (int i = 5; i < 15; i++) {
                SurfaceTile land = sicopiaPlanet.getSurfaceTile(i, 3);
                ResultBean tradeLandResult = TradeHelper.tradeAsset(land, builder);
                if (tradeLandResult.isSuccess()) {
                    Building factory = GLSFactory.deliverBuilding(Building.FACTORY);
                    ResultBean tradeBuildingResult = TradeHelper.tradeAsset(factory, builder);
                    if (tradeBuildingResult.isSuccess()) {
                        factory.setParent(land);
                        land.setBuilding(factory);
                        builder.addBuilding(factory);
                        LOG.info("Builder: New factory purchased");
                    } else {
                        LOG.warn("Builder: " + tradeBuildingResult.getMessage());
                    }
                } else {
                    LOG.warn("Builder: " + tradeLandResult.getMessage());
                }
            }
            
            // DONE
            
            LOG.info("New universe generating finished");
            ret = true;
            
        } catch (Exception ex) {
             LOG.error("UniverseGenerator:generate" + ex.getMessage());
        }
        
        return ret;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private static Star generateSystem(String starName) throws Exception {
        int randStarImg = rand.nextInt(Constants.AVAILABLE_STARS) + 1;
        String starImg = Constants.STARS_FOLDER + "star" + String.format("%02d" , randStarImg) + ".png";
        
        int randX;
        int randY;
        do {
            randX = rand.nextInt(DisplayUtils.getMAX_X() - 1) + 1;
            randY = rand.nextInt(DisplayUtils.getMAX_Y() - 1) + 1;
        } while (!UniverseUtils.isFreeUniverseSpot(randX, randY));

        Star newStar = new Star(starName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta. Seldo súriquessë lalantila nil satto tyelca combë yualë aini telimbectar elda. Celma iltániel fëa laiquë eldanyárë vórëa am.", starImg, UniverseUtils.getStarColor(randStarImg), randX, randY);

        int numberOfPlanets = rand.nextInt(5); 
        if (rand.nextInt(10) % 2 == 0) {
            numberOfPlanets += rand.nextInt(5);
        }
        if (rand.nextInt(10) % 3 == 0) {
            numberOfPlanets += rand.nextInt(5);
        }
        int centerX = DisplayUtils.getMAX_X() / 2 + 1;
        int centerY = DisplayUtils.getMAX_Y() / 2 + 1;
        for (int j = 1; j <= numberOfPlanets; j++) {
            String planetName = newStar.getName() + " " + UniverseUtils.getPlanetOrder(j);

            int randPlanetImg = rand.nextInt(Constants.AVAILABLE_PLANETS) + 1;
            String planetImg = Constants.PLANETS_FOLDER + "planet" + String.format("%02d" , randPlanetImg) + ".png";

            do {
                // x = r * sin(θ), y = r * cos(θ)
                int angle = rand.nextInt(360);
                double radius = 9 + (1 + rand.nextDouble() * 2) * j;
                Double randomX = centerX + (radius * Math.sin(angle)); 
                Double randomY = centerY + (radius * Math.cos(angle));

                randX = Math.min(randomX.intValue(), DisplayUtils.getMAX_X());
                randY = Math.min(randomY.intValue(), DisplayUtils.getMAX_Y());
            } while (!UniverseUtils.isFreeSystemSpot(randX, randY, newStar));
            
            Base newPlanet = new Base(newStar, planetName, "Návëa nyarro findel vénë lenta ango nirwa axa tárië. úrion valmo alcarinqua naina uë mixa. Laurina vasarya yunquenta nícë síma aranya tyasta.", planetImg, Color.WHITE, randX, randY);
            
            if (rand.nextInt() % 5 == 0) {
                newPlanet.setShipyard(true);
            }
            
            Player glsPlayer = UniverseManager.getInstance().getGLSPlayer();
            IStorage planetaryStorage = newPlanet.getCity();
            Cargo randomCargo0 = new Cargo(availableGoods.get(0), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo0, planetaryStorage);
            Cargo randomCargo1 = new Cargo(availableGoods.get(1), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo1, planetaryStorage);
            Cargo randomCargo2 = new Cargo(availableGoods.get(2), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo2, planetaryStorage);
            Cargo randomCargo3 = new Cargo(availableGoods.get(3), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo3, planetaryStorage);
            Cargo randomCargo4 = new Cargo(availableGoods.get(4), rand.nextInt(2000) + 1, glsPlayer, null);
            StorageUtils.storeCargo(randomCargo4, planetaryStorage);

            newStar.addStellarObject(newPlanet);
        }

        if (rand.nextInt() % 2 == 0) {
            newStar.setRiftPortal(new RiftPortal("Rift portal", "Can be used to travel to any other system", DisplayUtils.getMAX_X() / 2 + 1, DisplayUtils.getMAX_Y() - 10, newStar));
        }
        
        connectSystemToRiftNetwork(newStar);
        
        return newStar;
    }
    
    private static void connectSystemToRiftNetwork(Star newStar) throws Exception {
        List<Star> availableNeighbors = universeManager.getAvailableStars();
        int maxSystems = availableNeighbors.size();
        int numberOfGates = rand.nextInt(Math.min(maxSystems, 3)) + 1;
        for (int j = 1; j <= numberOfGates; j++) {
            int index = rand.nextInt(availableNeighbors.size());
            Star randomDestination = availableNeighbors.get(index);
            availableNeighbors.remove(index);
            
            String riftGateName1 = "Gate to " + randomDestination.getName();
            String riftGateName2 = "Gate to " + newStar.getName();


            int gatesInSystem = newStar.getRiftGates().size();
            int xCoord1 = 10 + 10 * (gatesInSystem % 6);
            int yCoord1 = 4 + 12 * (gatesInSystem / 6);
            
            int gatesInTarget = randomDestination.getRiftGates().size();
            int xCoord2 = 10 + 10 * (gatesInTarget % 6);
            int yCoord2 = 4 + 12 * (gatesInTarget / 6);

            newStar.addRiftGate(new RiftGate(riftGateName1, "Permanent rift connector betwen two systems", xCoord1, yCoord1, newStar, randomDestination));
            randomDestination.addRiftGate(new RiftGate(riftGateName2, "Permanent rift connector betwen two systems", xCoord2, yCoord2, randomDestination, newStar));

            universeManager.addGate(new Connector(newStar, randomDestination));
        }
    }
}
