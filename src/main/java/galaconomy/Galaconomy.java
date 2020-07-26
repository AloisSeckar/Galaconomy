package galaconomy;

import galaconomy.constants.Constants;
import galaconomy.gui.*;
import galaconomy.universe.*;
import galaconomy.utils.LogUtils;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import org.slf4j.*;

public class Galaconomy extends Application {
    
    private static final Logger LOG = LoggerFactory.getLogger(Galaconomy.class);
    
    private static final String TITLE = Constants.PROGRAM_NAME;
    
    @Override
    public void start(Stage primaryStage) {
        
        LOG.info(TITLE + " started");
        
        BasicGameLayout gameLayout = BasicGameLayout.getInstance();

        Scene scene = new Scene(gameLayout, Constants.SCREEN_X + Constants.SIDE_PANEL_X + 20, Constants.SCREEN_Y + 50 + Constants.BOTTOM_PANEL_Y);
        scene.getStylesheets().add(getClass().getResource(Constants.FOLDER_CSS + "galaconomy.css").toExternalForm());
        
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        LOG.info(TITLE + " prepared");
    }
    
    
    @Override
    public void stop() {
        UniverseManager.getInstance().stopEngine();
        
        LOG.info(TITLE + " stopped");
        LogUtils.archiveLastLog();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
