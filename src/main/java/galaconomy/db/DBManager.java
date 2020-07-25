package galaconomy.db;

import galaconomy.constants.Constants;
import java.sql.*;
import java.util.*;
import org.slf4j.*;

public class DBManager {
    
    private static final Logger LOG = LoggerFactory.getLogger(DBManager.class);
    
    private static DBManager INSTANCE;
    
    private DBManager() {
    }
    
    public static DBManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DBManager();
        }
        return INSTANCE;
    }
    
    ////////////////////////////////////////////////////////////////////////////
   
    public List<String> getAvailableStarNames(){
        List<String> ret = new ArrayList<>();
        
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery("SELECT name FROM random_star_names")){
            
            while (rs.next()) {
                ret.add(rs.getString("name"));
            }
        } catch (SQLException ex) {
            LOG.error("DBManager.getAvailableStarNames", ex);
        }
        
        return ret;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private Connection connect() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(Constants.SQLITE_DB);
            if (conn.isValid(5)) {
                LOG.info("DB connection successful");
            } else {
                LOG.warn("DB connection failed");
            }
        } catch (SQLException ex) {
            LOG.error("DBManager.connect:", ex);
        }
        
        return conn;
    }
}
