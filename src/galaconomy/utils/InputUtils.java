package galaconomy.utils;

public class InputUtils {
    
    public static String trimToNull(String input) {
        if (input != null) {
            return input.trim();
        } else {
            return null;
        }
    }
    
}
