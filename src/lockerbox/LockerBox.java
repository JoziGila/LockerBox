package lockerbox;

import lockerbox.Models.DatabaseModel;

public class LockerBox {

    public static void main(String[] args) {
        try {
            DatabaseModel.initDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        new LoginForm().setVisible(true);
    }
    
}
