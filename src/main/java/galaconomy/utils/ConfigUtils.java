package galaconomy.utils;

import galaconomy.Galaconomy;
import galaconomy.constants.Constants;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.*;

public class ConfigUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(ConfigUtils.class);

    private static Properties properties;
    
    private ConfigUtils() {
    }
    
    public static void loadProperties() {
        if (properties == null) {
            try (InputStream input = Galaconomy.class.getClassLoader().getResourceAsStream("galaconomy.properties")) {

                properties = new Properties();

                if (input != null) {
                    properties.load(input);
                } else {
                    setDefaultProperties();
                }

                LOG.info(properties.getProperty("app-title", Constants.PROGRAM_NAME) + " - configuration set up");

                if (isEnhancedLogging()) {
                    LOG.info("Config values:");
                    properties.forEach((key, value) -> LOG.info("\t" + key + ": " + value));
                }

            } catch (Exception ex) {
                LOG.error("Galaconomy:loadProperties", ex);
                setDefaultProperties();
            }
        }
    }
    
    public static boolean isEnhancedLogging() {
        if (properties == null) {
            loadProperties();
        }
        return Constants.VALUE_YES.equalsIgnoreCase(properties.getProperty("enhanced-logger", Constants.VALUE_NO));
    }
    
    public static String getPropertyIfSet(String key) {
        if (properties == null) {
            loadProperties();
        }
        return properties.getProperty(key, "");
    }
    
    ////////////////////////////////////////////////////////////////////////////

    private static void setDefaultProperties() {
        LOG.warn("Galaconomy:loadProperties - configuration file not found, using build-in defaults");
        properties.setProperty("app-title", Constants.PROGRAM_NAME);
        properties.setProperty("fork-join", "yes");
        properties.setProperty("enhanced-logger", "no");
    }
    
}
