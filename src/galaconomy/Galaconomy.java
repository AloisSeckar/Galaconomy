package galaconomy;

import galaconomy.constants.Constants;
import galaconomy.gui.*;
import galaconomy.universe.*;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Galaconomy extends Application {
    
    public static final int SCREEN_X = (Constants.MAX_X + 2) * 8;
    public static final int SCREEN_Y = (Constants.MAX_Y + 2) * 8;
    public static final int SIDE_PANEL_X = 250;
    
    @Override
    public void start(Stage primaryStage) {
        
        BorderPane gameLayout = new BorderPane();
        
        InfoPane info = new InfoPane(SIDE_PANEL_X);
        MapPane map = new MapPane(SCREEN_X, SCREEN_Y, info);
        PlayerPane player = new PlayerPane(info);
        
        HBox menu = new HBox();
        
        Button newUniverseBtn = new Button("Generate universe");
        newUniverseBtn.setOnAction((ActionEvent event) -> {
            UniverseManager universe = UniverseManager.getInstance();
            UniverseGenerator.generate(universe);
            
            map.paintUniverseMap(new ArrayList<>(universe.getStars().values()));
            map.paintShipRoutes(universe.getRoutes());
            
            player.displayPlayer();
            
            universe.registerSubscriber(info);
            universe.registerSubscriber(map);
            universe.startEngine();
        });
        menu.getChildren().add(newUniverseBtn);
        
        
        Button saveUniverseBtn = new Button("Save universe");
        saveUniverseBtn.setOnAction((ActionEvent event) -> {
            boolean saved = UniverseManager.saveUniverse("galaconomy.sav");
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(saved ? "Saved" : "Failed");
            a.show();
        });
        menu.getChildren().add(saveUniverseBtn);
        
        Button loadUniverseBtn = new Button("Load universe");
        loadUniverseBtn.setOnAction((ActionEvent event) -> {
            boolean loaded = UniverseManager.loadUniverse("galaconomy.sav");
            
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText(loaded ? "Loaded" : "Failed");
            a.show();
            
            if (loaded) {
                UniverseManager universe = UniverseManager.getInstance();
                map.paintUniverseMap(new ArrayList<>(universe.getStars().values()));
                map.paintShipRoutes(universe.getRoutes());

                player.displayPlayer();

                universe.registerSubscriber(info);
                universe.registerSubscriber(map);
                universe.startEngine();
            }
        });
        menu.getChildren().add(loadUniverseBtn);
        
        gameLayout.setTop(menu);
        gameLayout.setCenter(map);
        gameLayout.setRight(info);
        gameLayout.setBottom(player);

        Scene scene = new Scene(gameLayout, SCREEN_X + SIDE_PANEL_X + 20, SCREEN_Y + 50 + 135);
        scene.getStylesheets().add(getClass().getResource(Constants.FOLDER_CSS + "galaconomy.css").toExternalForm());
        
        primaryStage.setTitle("Galaconomy 0.1");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    
    @Override
    public void stop() {
        UniverseManager.getInstance().stopEngine();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
