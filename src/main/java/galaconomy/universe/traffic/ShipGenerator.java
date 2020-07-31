package galaconomy.universe.traffic;

import galaconomy.constants.Constants;
import galaconomy.universe.*;
import java.util.Random;

public class ShipGenerator {
    
    public static Ship createRandomShip() {
        Random rand = new Random(); 
        String randomName = "GLS Random " + UniverseManager.getInstance().getStellarTime();
        return new Ship(randomName, getRandomShipClass(rand), UniverseUtils.getRandomBase());
    }
    
    public static ShipClass getRandomShipClass(Random rand) {
        return getShipClassById(rand.nextInt(Constants.AVAILABLE_SHIPS));
    }
    
    public static ShipClass getShipClassById(int classId) {
        switch (classId) {
            case 1:
                return new ShipClass("Tiger", "Test class 01", Constants.SHIPS_FOLDER + "ship01.jpg", 2500, 500, 5, 25, 3.5d, 3.5d);
            case 2:
                return new ShipClass("Banshee", "Test class 02", Constants.SHIPS_FOLDER + "ship02.jpg", 11000, 800, 8, 50, 2d, 2d);
            case 3:
                return new ShipClass("Raptor", "Test class 03", Constants.SHIPS_FOLDER + "ship03.jpg", 6000, 500, 6, 20, 4.25d, 4.25d);
            case 4:
                return new ShipClass("Eagle", "Test class 04", Constants.SHIPS_FOLDER + "ship04.jpg", 3000, 650, 9, 60, 2.5d, 2.5d);
            default:
                return new ShipClass("Mule", "Test class 05", Constants.SHIPS_FOLDER + "ship05.jpg", 1500, 400, 3, 100, 1.5d, 1.5d);
        }
    }
    
}
