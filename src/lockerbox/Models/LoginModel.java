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
    public static User login(String username, String password){
        String localPass = CryptoModel.hashString(password);
        User user = fetchUser(username);
        
        if ((localPass != null && user.password != null) && localPass.equals(user.password)){
            return user;
        } else {
            return null;
        }
    }
    
    public static User fetchUser(String username){
        Connection conn = null;
        User user = null;
        
        try {
            conn = DatabaseModel.getConnection();
            String query = "SELECT id, password FROM users WHERE username = '" + username + "';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()){
                user = new User();
                user.id = rs.getInt("id");
                user.password = rs.getString("password");
            }
            
            conn.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        return user;
    }
}
