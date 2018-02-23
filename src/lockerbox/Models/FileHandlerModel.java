/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lockerbox.Models;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Cipher;

public class FileHandlerModel {
    private ArrayList<String> filenames;
    private Connection conn;
    private User currentUser;
    private FileHandlerCallback callback;
    
    private final String workingDir = "C:/LockerBox";
    
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
                for (File file : files) {
                    // Create new file instance within folder and generate correct key for aes
                    File newFile = new File(workingDir + "/" + file.getName());
                    String augmentedKey = CryptoModel.generateAESKey(currentUser.password);
                    
                    // Encrypt the file using the new directory and delete the old one
                    CryptoModel.encryptFileAndMove(Cipher.ENCRYPT_MODE, augmentedKey, file, newFile);
                    file.delete();
                    
                    // Add the recods to the database and to the local 
                    addFileRecordToDB(file);
                    filenames.add(newFile.getName());
                }
            }
        }).start();
    }
    
    public void fetchFilesFromDB(){
        // Get filenames and verify existance in folder
        try {
            conn = DatabaseModel.getConnection();
            String query = "SELECT filename, filehash FROM files WHERE user_id = " + currentUser.id + ";";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()){
                String filename = rs.getString("filename");
                String filehash = rs.getString("filehash");
                
                File fileToVerify = new File(workingDir + "/" + filename);
                if (fileToVerify.exists() && CryptoModel.getMD5(fileToVerify).equals(filehash)){
                    filenames.add(filename);
                }
                
            }
            
            conn.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void addFileRecordToDB(File file){
        try {
            System.out.println("Inserting into database");

            String query = "INSERT INTO files (filename, filehash, user_id) VALUES(?, ?, ?);";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, file.getName());
            st.setString(2, CryptoModel.getMD5(file));
            st.setInt(3, currentUser.id);

            st.execute();
        } catch (Exception e) {
            System.out.println("IN FILE " + file.getName() + " EXCEPTION " + e.toString());
        }
    }
    
    public static void initDirectory(){
        String PATH = "C:/LockerBox";

        File directory = new File(PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }

        try {
            String query = "SELECT username FROM users;";
            Statement st = DatabaseModel.getConnection().createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                String username = rs.getString("username");
                File subDir = new File(PATH + "/" + username);
                if (!subDir.exists()) {
                    subDir.mkdir();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public interface FileHandlerCallback{
        public void filesUpdated(String action);
    }
   
}
