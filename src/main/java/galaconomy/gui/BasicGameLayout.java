package galaconomy.gui;

import galaconomy.gui.pane.PlayerPane;
import galaconomy.universe.*;
import galaconomy.universe.map.*;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class BasicGameLayout extends BorderPane {
    
    private static BasicGameLayout INSTANCE;
    
    private UniverseMapFrame universeMap;
    private SystemMapFrame systemMap;
    private BaseMapFrame baseMap;
    
    private BasicGameLayout() {
        
        InfoFrame info = InfoFrame.getInstance();
        universeMap = UniverseMapFrame.getInstance();
        systemMap = SystemMapFrame.getInstance();
        baseMap = BaseMapFrame.getInstance();
        ControlsFrame controls = ControlsFrame.getInstance();
        
        // TODO change this to avoid exposing class to outer world...
        PlayerPane player = controls.getPlayerPane();
        
        HBox menu = new HBox();
        
        Button newUniverseBtn = new Button("Generate universe");
        newUniverseBtn.setOnAction((ActionEvent event) -> {
            UniverseGenerator.generate();
            UniverseManager universe = UniverseManager.getInstance();
            
            List<Star> stars = new ArrayList<>(universe.getStars().values());
            List<Connector> gates = universe.getGates();
            universeMap.paintUniverseMap(stars, gates);
            universeMap.paintShipTravels(universe.getTravels());
            
            player.displayPlayer();
            player.loadPlayerShips();
            
            universe.registerSubscriber(info);
            universe.registerSubscriber(universeMap);
            universe.registerSubscriber(systemMap);
            universe.registerSubscriber(baseMap);
            universe.registerSubscriber(controls);
            universe.startEngine();
        });
        menu.getChildren().add(newUniverseBtn);
        
        
        Button saveUniverseBtn = new Button("Save universe");
        saveUniverseBtn.setOnAction((ActionEvent event) -> {
            boolean saved = UniverseManager.saveUniverse("sav\\galaconomy.sav");
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(saved ? "Saved" : "Failed");
            a.show();
        });
        menu.getChildren().add(saveUniverseBtn);
        
        Button loadUniverseBtn = new Button("Load universe");
        loadUniverseBtn.setOnAction((ActionEvent event) -> {
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

                player.displayPlayer();
                player.loadPlayerShips();

                universe.registerSubscriber(info);
                universe.registerSubscriber(universeMap);
                universe.startEngine();
            }
        });
        menu.getChildren().add(loadUniverseBtn);
        
        this.setTop(menu);
        this.setCenter(universeMap);
        this.setRight(info);
        this.setBottom(controls);
    }
    
    public static BasicGameLayout getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BasicGameLayout();
        }
        return INSTANCE;
    }
    
    public void switchToGalaxy() {
        universeMap.setActive(true);
        systemMap.setActive(false);
        baseMap.setActive(false);
        
        this.setCenter(universeMap);
    }
    
    public void switchToSystem(Star star) {
        systemMap.paintSystemMap(star);
        
        universeMap.setActive(false);
        systemMap.setActive(true);
        baseMap.setActive(false);
        
        this.setCenter(systemMap);
    }
    
    public void switchToBase(Base base) {
        baseMap.paintBaseMap(base);
        
        universeMap.setActive(false);
        systemMap.setActive(false);
        baseMap.setActive(true);
        
        this.setCenter(baseMap);
    }
    
}
