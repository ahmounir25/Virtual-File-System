import java.util.ArrayList;

public class User extends Commands{
public ArrayList<String>userFolder=new ArrayList<>();
    public ArrayList<String>capabilites=new ArrayList<>();
    User(String name, String pass) {
        super(name, pass);
    }

}
