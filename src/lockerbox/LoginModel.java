package lockerbox;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class LoginModel {
    public static boolean login(String username, String password){
        String localPass = hashPass(password).toUpperCase();
        String databasePass = fetchPasswordHash(username);
        
        System.out.println(localPass + "\n" + databasePass);
        
        return (localPass != null && databasePass != null) && localPass.equals(databasePass);
    }
    
    public static String hashPass(String password) {
        String generatedHash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuffer hashCodeBuffer = new StringBuffer();
            for (int i = 0; i < byteData.length; i++) {
                hashCodeBuffer.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            
            generatedHash = hashCodeBuffer.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return generatedHash;
    }
    
    public static String fetchPasswordHash(String username){
        Connection conn = null;
        String passHash = null;
        
        try {
            conn = getConnection();
            String query = "SELECT password FROM users WHERE username = '" + username + "';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()){
                passHash = rs.getString("password");
            }
          
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        return passHash;
    }
    
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
