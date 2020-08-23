package galaconomy.gui;

import galaconomy.constants.Constants;
import galaconomy.gui.view.*;
import galaconomy.utils.DisplayUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class MenuFrame extends MenuBar {
    
    private static MenuFrame INSTANCE;
    
    public static MenuFrame getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MenuFrame();
        }
        return INSTANCE;
    }
    
    private MenuFrame() {
        
        MenuItem generate = new MenuItem("Generate universe", getIcon("galaxy"));
        generate.setOnAction((ActionEvent t) -> {
            BasicGameLayout.getInstance().newUniverse();
        });
        
        MenuItem save = new MenuItem("Save universe", getIcon("save"));
        save.setOnAction((ActionEvent t) -> {
            BasicGameLayout.getInstance().saveUniverse();
        });
        
        MenuItem load = new MenuItem("Load universe", getIcon("load"));
        load.setOnAction((ActionEvent t) -> {
            BasicGameLayout.getInstance().loadUniverse();
        });
        
        Menu menuFile = new Menu("Universe");
        menuFile.getItems().addAll(generate, save, load);
        
        MenuItem showSystems = new MenuItem("Show systems", getIcon("stars"));
        showSystems.setOnAction((ActionEvent t) -> {
            SystemListView view = new SystemListView();
            view.show();
        });
        
        MenuItem showBases = new MenuItem("Show bases", getIcon("bases"));
        showBases.setOnAction((ActionEvent t) -> {
            BaseListView view = new BaseListView();
            view.show();
        });
        
        MenuItem showShips = new MenuItem("Show ships", getIcon("ships"));
        showShips.setOnAction((ActionEvent t) -> {
            ShipListView view = new ShipListView();
            view.show();
        });
        
        MenuItem showPlayers = new MenuItem("Show players", getIcon("players"));
        showPlayers.setOnAction((ActionEvent t) -> {
            PlayerListView view = new PlayerListView();
            view.show();
        });
        
        Menu menuShow = new Menu("Show");
        menuShow.getItems().addAll(showSystems, showBases, showShips, showPlayers);
        
        super.getMenus().addAll(menuFile, menuShow);
        
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private ImageView getIcon(String icon) {
        String source = Constants.ICONS_FOLDER + icon + ".png";
        return DisplayUtils.getImageView(source, 24);
    }
    
}
