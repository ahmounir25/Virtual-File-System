import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Admin extends Commands {
    public ArrayList<String>folders=new ArrayList<>();
    public ArrayList<User> users;
    public  Directory D;
    Admin(String name, String pass, ArrayList<User>users) {
        super(name, pass);
        this.users=users;
    }
    public void Cuser(String[] arr)  {
        if (arr.length == 3) {
            String name=arr[1];
            String pass=arr[2];
              for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(name)) {
                System.out.println("User Already Exist ...");
                return;
            }
        }
            User u = new User(name, pass);
            users.add(u);
        }
         else {
            System.out.println("Enter Valid input ... ");
        }
    }
    public void WriteUsers(File f){
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(f.getName());

            myWriter.write("admin" + " , " +"admin"+"\n");
           for (int i=0;i< users.size();i++) {

               myWriter.write(users.get(i).getUsername() + " , " + users.get(i).getPassword() + "\n");

           }
            myWriter.close();
         }
           catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void WriteCapabilites(File f){

        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(f.getName());

            for(int j=0;j<folders.size();j++){
                myWriter.write(folders.get(j)+" , ");
                for (int i=0;i< users.size();i++) {
                    for (int k=0;k<users.get(i).userFolder.size();k++){
                        if(folders.get(j).equalsIgnoreCase(users.get(i).userFolder.get(k))){
                            myWriter.write(  users.get(i).getUsername() + " , " );
                            myWriter.write( users.get(i).capabilites.get(k)+" " );
                        }
                    }
                 }
                myWriter.write("\n");
            }
            myWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void grant(String[] arr, Directory d) {
        if (arr.length == 4) {
            String[] path = arr[2].split("/");
            String username=arr[1];
            String rights= arr[arr.length-1];
            Directory cur = d;

            boolean flag=true;
            for(int i=0;i< folders.size();i++) {
                if(folders.get(i).equalsIgnoreCase(arr[2]))
                {
                    flag=false;
                }
            }
            if(flag){
                folders.add(arr[2]);}

            if (!path[0].equalsIgnoreCase("root")) {
                System.out.println("Path Not found");
                return;
            }
            for (int i = 0; i < path.length - 1; i++)
            {
                if (path[i].equalsIgnoreCase(cur.getName())) {
                    if (cur.searchSubDirectory(path[i + 1]) != null)
                    {
                        cur = cur.searchSubDirectory(path[i + 1]);
                    }
                    else {
                        System.out.println("No such a Directory");
                        return;
                    }
                }
            }
            for (int j = 0; j < users.size(); j++) {
                if (users.get(j).getUsername().equalsIgnoreCase(username)) {
                    users.get(j).userFolder.add(arr[2]);
                    if (rights.equalsIgnoreCase("00")) {
                        d.grant(username, false, false);
                        users.get(j).capabilites.add("00");
                    }
                    else if (rights.equalsIgnoreCase("01")) {
                        d.grant(username,false, true);
                        users.get(j).capabilites.add("01");
                    } else if (rights.equalsIgnoreCase("10")) {
                        d.grant(username, true, false);
                        users.get(j).capabilites.add("10");
                    } else {
                        d.grant(username, true, true);
                        users.get(j).capabilites.add("11");
                    }
                }
            }
        }
        else
            System.out.println("Enter the Path Correctly .. ");

    }

}
