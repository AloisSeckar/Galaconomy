package galaconomy;

import galaconomy.constants.Constants;
import galaconomy.gui.*;
import galaconomy.universe.*;
import galaconomy.utils.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;
import org.slf4j.*;

public class Galaconomy extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(Galaconomy.class);
    
    private static String TITLE;

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        
        ConfigUtils.loadProperties();
        TITLE = ConfigUtils.getPropertyIfSet("app-title");

        LOG.info(TITLE + " - started");
        
        Galaconomy.primaryStage = primaryStage;
        setupStage();
        primaryStage.show();

        LOG.info(TITLE + " - prepared");
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void stop() {
        UniverseManager.getInstance().stopEngine();

        LOG.info(TITLE + " - stopped");
        LogUtils.archiveLastLog();
    }

    public static void main(String[] args) {
        launch(args);
    }

    ////////////////////////////////////////////////////////////////////////////

    private void setupStage() {
        DisplayUtils.init();

        BasicGameLayout gameLayout = BasicGameLayout.getInstance();

        Scene scene = new Scene(gameLayout, 0, 0);
        scene.getStylesheets().add(getClass().getResource(Constants.FOLDER_CSS + "galaconomy.css").toExternalForm());
        scene.getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        primaryStage.setResizable(false);

        primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Really quit?", ButtonType.YES, ButtonType.NO);
                alert.initOwner(primaryStage);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    primaryStage.close();
                }
            }
        });
    }

}
