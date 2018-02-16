package lockerbox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

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
    
    public static void initDatabase() throws Exception {
        String driverName = "org.gjt.mm.mysql.Driver";
        Class.forName(driverName);
        
        String serverName = "localhost";
        String databaseName = "jozi";
        
        String url = "jdbc:mysql://" + serverName + "/?autoReconnect=true&useSSL=false&user=root&password=";
        Connection conn = DriverManager.getConnection(url);
        
        String[] tableStatements = {
            "users (id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, username TEXT, password TEXT);"
        };
        
        Statement s = conn.createStatement();
        s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName);
        
        conn = getConnection();
        s = conn.createStatement();
        
        for(String tableSt : tableStatements){
            s.executeUpdate("CREATE TABLE IF NOT EXISTS " + tableSt);
        }

    }
}
