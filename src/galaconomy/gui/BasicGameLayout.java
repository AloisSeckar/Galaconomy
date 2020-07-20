package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.universe.UniverseGenerator;
import galaconomy.universe.UniverseManager;
import galaconomy.universe.map.Base;
import galaconomy.universe.map.Star;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class BasicGameLayout extends BorderPane {
    
    private static BasicGameLayout INSTANCE;
    
    private UniverseMapFrame universeMap;
    private SystemMapFrame systemMap;
    
    private BasicGameLayout() {
        
        InfoFrame info = new InfoFrame(Constants.SIDE_PANEL_X);
        universeMap = new UniverseMapFrame(Constants.SCREEN_X, Constants.SCREEN_Y, info.getInfoPane());
        systemMap = new SystemMapFrame(Constants.SCREEN_X, Constants.SCREEN_Y, info.getInfoPane());
        PlayerFrame player = new PlayerFrame(info.getInfoPane());
        
        HBox menu = new HBox();
        
        Button newUniverseBtn = new Button("Generate universe");
        newUniverseBtn.setOnAction((ActionEvent event) -> {
            UniverseManager universe = UniverseManager.getInstance();
            UniverseGenerator.generate(universe);
            
            universeMap.paintUniverseMap(new ArrayList<>(universe.getStars().values()));
            universeMap.paintShipRoutes(universe.getRoutes());
            
            player.displayPlayer();
            player.loadPlayerShips();
            
            universe.registerSubscriber(info);
            universe.registerSubscriber(universeMap);
            universe.registerSubscriber(systemMap);
            universe.registerSubscriber(player);
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
                universeMap.paintUniverseMap(new ArrayList<>(universe.getStars().values()));
                universeMap.paintShipRoutes(universe.getRoutes());

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
        this.setBottom(player);
    }
    
    
    public static BasicGameLayout getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BasicGameLayout();
        }
        return INSTANCE;
    }
    
    public void switchToGalaxy() {
        this.setCenter(universeMap);
        universeMap.setActive(true);
        systemMap.setActive(false);
    }
    
    public void switchToSystem(Star star) {
        this.setCenter(systemMap);
        systemMap.setActive(true);
        universeMap.setActive(false);
    }
    
    public void switchToBase(Base base) {
        // class required
    }
    
}