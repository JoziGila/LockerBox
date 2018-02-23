package lockerbox.Models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.MessageDigest;


public class CryptoModel {
    public static String hashString(String password) {
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
    
    public static String getMD5(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(fis);
            fis.close();
            return md5;
        } catch (Exception e){
            System.out.println("ERROR WHILE HASHING FILE " + file.getName() + " EX: " + e.toString());
        }
        return null;
    }
}
