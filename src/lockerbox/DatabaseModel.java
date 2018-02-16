package lockerbox;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseModel {
    public static Connection getConnection() throws Exception{
        String driverName = "org.gjt.mm.mysql.Driver";
        Class.forName(driverName);
        
        String serverName = "localhost";
        String databaseName = "jozi";
        String url = "jdbc:mysql://" + serverName + "/" + databaseName + "?autoReconnect=true&useSSL=false";
        
        String dbUser = "root";
        String dbPass = "";
        
        return DriverManager.getConnection(url, dbUser, dbPass);
    }
}
