package lockerbox;

import java.security.MessageDigest;


public class CryptoModel {
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
}
