package lockerbox.Models;

import lockerbox.Models.DatabaseModel;
import lockerbox.Models.CryptoModel;
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
        String localPass = CryptoModel.hashPass(password);
        String databasePass = fetchPasswordHash(username);
        
        System.out.println(localPass + "\n" + databasePass);
        
        return (localPass != null && databasePass != null) && localPass.equals(databasePass);
    }
    
    public static String fetchPasswordHash(String username){
        Connection conn = null;
        String passHash = null;
        
        try {
            conn = DatabaseModel.getConnection();
            String query = "SELECT password FROM users WHERE username = '" + username + "';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()){
                passHash = rs.getString("password");
            }
            
            conn.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        return passHash;
    }
}
