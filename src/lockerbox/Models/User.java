package lockerbox.Models;

/**
 *
 * @author Jozi
 */
public class User {
    public int id;
    public String password;
    
    public User(int id, String password){
        this.id = id;
        this.password = password;
    }
    
    public User(){
        
    }
}
