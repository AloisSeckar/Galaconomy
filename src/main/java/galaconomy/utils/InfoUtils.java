package galaconomy.utils;

import javafx.scene.control.*;

public class InfoUtils {
    
    public static void showMessage(String message) {
            Alert a = new Alert(Alert.AlertType.NONE, message, ButtonType.OK); 
            a.setTitle("GLS Service");
            a.showAndWait();
    }
    
    public static String getErrorMessage(Exception ex) {
        return ex.getMessage() != null ? ex.getMessage() : ex.toString();
    }
    
}
