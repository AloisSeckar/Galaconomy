package galaconomy.gui.window.pane;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.map.Base;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ShipDispatchPane extends GridPane {
    
    private final Label locationLabel;
    private final ComboBox<ObservableList<Base>> destinationCB;
    
    public ShipDispatchPane(String currentLocation) {
        super.setAlignment(Pos.CENTER);
        super.setHgap(10);
        super.setVgap(10);
        super.setPadding(new Insets(5, 5, 5, 5));
        
        super.add(new Label("Location:"), 0, 0);
        
        locationLabel = new Label(currentLocation);
        super.add(locationLabel, 1, 0);
        
        super.add(new Label("Select destination:"), 0, 1);
        
        ObservableList<Base> availableBases = 
            FXCollections.observableArrayList(
                    UniverseManager.getInstance().getAvailableBases()
            );
        destinationCB = new ComboBox(availableBases);
        super.add(destinationCB, 1, 1);
    }
    
    public Base getDestination() {
        return (Base) destinationCB.getValue();
    }

}
