package galaconomy.gui;

import galaconomy.gui.pane.DisplayPane;
import galaconomy.gui.pane.PlayerPane;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BasicGameLayout extends BorderPane {
    
    private final InfoFrame info;
    private final UniverseMapFrame universeMap;
    private final SystemMapFrame systemMap;
    private final BaseMapFrame baseMap;
    private final ControlsFrame controls;
    
    private static BasicGameLayout INSTANCE;
    
    public static BasicGameLayout getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BasicGameLayout();
        }
        return INSTANCE;
    }
    
    private BasicGameLayout() {
        info = InfoFrame.getInstance();
        universeMap = UniverseMapFrame.getInstance();
        systemMap = SystemMapFrame.getInstance();
        baseMap = BaseMapFrame.getInstance();
        controls = ControlsFrame.getInstance();
        
        MenuFrame menu = MenuFrame.getInstance();
        
        this.setTop(menu);
        this.setCenter(universeMap);
        this.setRight(info);
        this.setBottom(controls);
    }
    
    public void newUniverse() {
        UniverseGenerator.generate();
        UniverseManager universe = UniverseManager.getInstance();

        List<Star> stars = new ArrayList<>(universe.getStars().values());
        List<Connector> gates = universe.getGates();
        universeMap.paintUniverseMap(stars, gates);
        universeMap.paintShipTravels(universe.getTravels());

        PlayerPane player = PlayerPane.getInstance();
        player.displayPlayer();
        player.loadPlayerShips();

        universe.registerSubscriber(info);
        universe.registerSubscriber(universeMap);
        universe.registerSubscriber(systemMap);
        universe.registerSubscriber(baseMap);
        universe.registerSubscriber(controls);
        universe.startEngine();
    }
    
    public void saveUniverse() {
        boolean saved = UniverseManager.saveUniverse("sav\\galaconomy.sav");
            
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(saved ? "Saved" : "Failed");
        a.show();
    }
    
    public void loadUniverse() {
        boolean loaded = UniverseManager.loadUniverse("sav\\galaconomy.sav");
            
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setContentText(loaded ? "Loaded" : "Failed");
        a.show();

        if (loaded) {
            UniverseManager universe = UniverseManager.getInstance();

            List<Star> stars = new ArrayList<>(universe.getStars().values());
            List<Connector> gates = universe.getGates();
            universeMap.paintUniverseMap(stars, gates);
            universeMap.paintShipTravels(universe.getTravels());

            PlayerPane player = PlayerPane.getInstance();
            player.displayPlayer();
            player.loadPlayerShips();

            universe.registerSubscriber(info);
            universe.registerSubscriber(universeMap);
            universe.startEngine();
        }
    }
    
    public void switchToGalaxy() {
        DisplayPane.getInstance().setElementToDisplay(new VoidElement());
        
        universeMap.setActive(true);
        systemMap.setActive(false);
        baseMap.setActive(false);
        
        this.setCenter(universeMap);
    }
    
    public void switchToSystem(Star star) {
        DisplayPane.getInstance().setElementToDisplay(star);
        
        systemMap.paintSystemMap(star);
        
        universeMap.setActive(false);
        systemMap.setActive(true);
        baseMap.setActive(false);
        
        this.setCenter(systemMap);
    }
    
    public void switchToBase(Base base) {
        DisplayPane.getInstance().setElementToDisplay(base);
        
        baseMap.paintBaseMap(base);
        
        universeMap.setActive(false);
        systemMap.setActive(false);
        baseMap.setActive(true);
        
        this.setCenter(baseMap);
    }
    
}
