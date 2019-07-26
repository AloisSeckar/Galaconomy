package galaconomy.utils;

import java.io.IOException;
import java.nio.file.*;
import org.slf4j.*;

public class LogUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(LogUtils.class);
    
    public static void archiveLastLog() {
        try {
            Path originalLogFile = Paths.get("log", "galaconomy.log");
            if (Files.exists(originalLogFile)) {
                Path archiveLogFile = Paths.get("log", "galaconomy" + System.currentTimeMillis() + ".log");
                Files.copy(originalLogFile, archiveLogFile, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            LOG.warn("LogUtils.archiveLastLog failed. Log file will be overwritten upon next application start.");
        }
    }

}
