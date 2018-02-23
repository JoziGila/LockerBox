/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lockerbox.Models;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class FileHandlerModel {
    private ArrayList<String> filenames;
    private Connection conn;
    private User currentUser;
    private FileHandlerCallback callback;
    
    public FileHandlerModel(User user){
        try {
            conn = DatabaseModel.getConnection();
        } catch (Exception e){
            System.out.println(e.toString());
        }
        
        this.currentUser = user;
    }
    
    
    public void addFiles(List<File> files){
        new Thread(new Runnable(){
            public void run(){
                // Add files to database records
                System.out.println("Processing files");
                for (File f : files) {
                    try {
                        System.out.println("Inserting into database");
                        
                        String query = "INSERT INTO files (filename, filehash, user_id) VALUES(?, ?, ?);";
                        PreparedStatement st = conn.prepareStatement(query);
                        st.setString(1, f.getName());
                        st.setString(2, CryptoModel.getMD5(f));
                        st.setInt(3, currentUser.id);

                        st.execute();
                    } catch (Exception e) {
                        System.out.println("IN FILE " + f.getName() + " EXCEPTION " + e.toString());
                    }
                }
            }
        }).start();
    }
    
    public interface FileHandlerCallback{
        public void filesUpdated(String action);
    }
   
}
