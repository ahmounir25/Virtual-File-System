import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
public class main {
    private static Directory root;
    private static final Scanner input = new Scanner(System.in);
    private static final ArrayList<User> users = new ArrayList<>();
    private static final Admin admin = new Admin("admin", "admin", users);
    private static Commands order = admin;
    public static void main(String[] arg) {
        File f = new File("user.txt");
        File f1 = new File("Capabilities.txt");
        System.out.println("Enter a number that is refers to an Allocation Algorithm :- ");
        System.out.println("1- Contiguous Allocation. " + "2- Linked Allocation. " + "3- Indexed Allocation.  ");
        int choice = input.nextInt();
        System.out.println("Enter Disk Size in KB :- ");
        int size = input.nextInt();
        if (choice == 1) {
            root = new Directory("root", new ContiguousAlloc(size));
        }
        else if (choice == 2) {
            root = new Directory("root", new LinkedAlloc(size));
        }
        else {
            root = new Directory("root", new IndexedAlloc(size));
        }
        input.skip("\n");
        while (true) {
            root.grant("admin",true, true );
            System.out.print("Enter Command :- ");
            String command = input.nextLine();
            String[] arr = command.split(" ");

            if (command.equalsIgnoreCase("Exit"))
                break;
            else if (arr[0].equalsIgnoreCase("CreateFile"))
                order.createFile(arr, root);
            else if (arr[0].equalsIgnoreCase("CreateFolder"))
                order.createFolder(arr, root);
            else if (arr[0].equalsIgnoreCase("DeleteFile"))
                order.deleteFile(arr, root);
            else if (arr[0].equalsIgnoreCase("DeleteFolder"))
                order.deleteFolder(arr, root);
            else if (arr[0].equalsIgnoreCase("DisplayDiskStatus"))
                order.displayDiskStatus(arr, root);
            else if (arr[0].equalsIgnoreCase("DisplayDiskStructure"))
                order.displayDiskStructure(arr, root);
            else if (arr[0].equalsIgnoreCase("TellUser"))
                order.tellUser(arr);
            else if (arr[0].equalsIgnoreCase("Grant")){
                 grant(arr);
                 admin.WriteCapabilites(f1);
            }
            else if (arr[0].equalsIgnoreCase("CUser"))
            {
                Cuser(arr);
                admin.WriteUsers(new File(f.getName()));
            }
            else if (arr[0].equalsIgnoreCase("Login"))
                Login(arr);
            else{
                System.out.println(" Invalid Command .... ");
            }
        }
        }

    private static void Cuser(String[] arr) {
        if (!order.getUsername().equalsIgnoreCase("admin")) {
            System.out.println(" Can't create User ");
            return;
        }
        else {
           admin.Cuser(arr);
        }
    }

    private static void grant(String[] arr) {
        if (!order.getUsername().equalsIgnoreCase("admin")) {
            System.out.println("Can't grant access");
            return;
        }
        admin.grant(arr, root);
    }

    private static void Login(String[] arr) {
        if (arr.length == 3) {
            String user = arr[1];
            String pass = arr[2];
            if (user.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("admin")) {
                order = admin;
                return;
            }
            else {
                for (int i=0;i<admin.users.size();i++)
                {
                    if (admin.users.get(i).getUsername().equalsIgnoreCase(user)
                            &&admin.users.get(i).getPassword().equalsIgnoreCase(pass))
                    {
                        order = admin.users.get(i);
                        return;
                    }
                }
            }
            System.out.println(" Username , password Or both are not Correct  ");
        }
        else {
            System.out.println("Enter the Username and Password ...");
        }
    }

    }

