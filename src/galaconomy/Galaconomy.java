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
        
        HBox menu = new HBox();
        
        Button btn = new Button("Generate universe");
        btn.setOnAction((ActionEvent event) -> {
            UniverseManager universe = UniverseManager.getInstance();
            UniverseGenerator.generate(universe);
            
            map.paintUniverseMap(new ArrayList<>(universe.getStarSystems().values()));
            map.paintShipRoutes(universe.getShipRoutes());
            
            universe.registerSubscriber(info);
            universe.registerSubscriber(map);
            universe.startEngine();
        });
        menu.getChildren().add(btn);
        gameLayout.setTop(menu);
        gameLayout.setCenter(map);
        gameLayout.setRight(info);

        Scene scene = new Scene(gameLayout, SCREEN_X + SIDE_PANEL_X + 20, SCREEN_Y + 50);
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
