package galaconomy.db;

import galaconomy.constants.Constants;
import java.sql.*;
import java.util.*;

public class DBManager {
    
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return ret;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    private Connection connect() {
        Connection conn = null;
        
        try {
            conn = DriverManager.getConnection(Constants.SQLITE_DB);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
}
