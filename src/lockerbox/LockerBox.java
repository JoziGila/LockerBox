package lockerbox;

import lockerbox.Models.CryptoModel;
import lockerbox.Models.DatabaseModel;
import lockerbox.Models.FileHandlerModel;

public class LockerBox {

    public static void main(String[] args) {
        try {
            DatabaseModel.initDatabase();
            FileHandlerModel.initDirectory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        new LoginForm().setVisible(true);
    }
    
}
