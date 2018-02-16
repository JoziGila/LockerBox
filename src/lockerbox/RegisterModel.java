/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lockerbox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Jozi
 */
public class RegisterModel {
    public static boolean isUsernameTaken(String username){
        Connection conn = null;
        Boolean hasUsername = false;
        try {
            conn = DatabaseModel.getConnection();
            String query = "SELECT username FROM users WHERE username = '" + username + "';";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()){
                hasUsername = true;
            }
            
            conn.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        return hasUsername;
    }
    
    public static boolean register(String username, String password){
        Connection conn;
        Boolean registered = false;
        try {
            conn = DatabaseModel.getConnection();
            String query = "INSERT INTO users (username, password) VALUES(?, ?);";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, username);
            st.setString(2, CryptoModel.hashPass(password));
            
            st.execute();
            registered = true;
            
            conn.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
        
        return registered;
    }
}
