package galaconomy.gui.pane;

import galaconomy.universe.UniverseManager;
import galaconomy.universe.map.Star;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ShipDispatchPane extends GridPane {
    
    private final Label locationLabel;
    private final ComboBox<ObservableList<Star>> destinationCB;
    
    public ShipDispatchPane(String currentLocation) {
        super.setAlignment(Pos.CENTER);
        super.setHgap(10);
        super.setVgap(10);
        super.setPadding(new Insets(5, 5, 5, 5));
        
        super.add(new Label("Location:"), 0, 0);
        
        locationLabel = new Label(currentLocation);
        super.add(locationLabel, 1, 0);
        
        super.add(new Label("Select destination:"), 0, 1);
        
        ObservableList<Star> availableStars = 
            FXCollections.observableArrayList(
                    UniverseManager.getInstance().getStars().values()
            );
        destinationCB = new ComboBox(availableStars);
        super.add(destinationCB, 1, 1);
    }
    
    public Star getDestination() {
        return (Star) destinationCB.getValue();
    }

}
