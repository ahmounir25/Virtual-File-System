
import java.util.ArrayList;

public class Directory {
    private final String Name;
    private final ArrayList<Directory> sub;
    private final ArrayList<V_File> files;
    private final FileSystem algo;
    private final ArrayList<access> accesses;

    public Directory(String Name, FileSystem algo) {
        files = new ArrayList<>();
        sub = new ArrayList<>();
        accesses=new ArrayList<>();
        this.Name = Name;
        this.algo = algo;
    }

    public void createFile(String name , int size) {
        V_File newF = new V_File(name, size);
        for (int i=0;i<files.size();i++) {
            if (files.get(i).getFileName().equalsIgnoreCase(newF.getFileName()))
            {
                System.out.println("File already exists ");
                return;
            }
        }
        if (!algo.allocate(newF))
        {
            System.out.println("There isn't Enough Space");
            return;
        }
        files.add(newF);
        System.out.println("File created Successfully");
    }

    public void createFolder(String name) {

        Directory subDirectory = new Directory(name, algo);
        for (int i=0;i<sub.size();i++){
            if (sub.get(i).getName().equalsIgnoreCase(subDirectory.getName()))
            {
                System.out.println("Folder already exists");
                return;
            }
        }

        sub.add(subDirectory);
        System.out.println("Folder Created Successfully");
    }

    public void deleteFile(String fileName) {
        for (int i=0;i<files.size();i++) {
            if (files.get(i).getFileName().equalsIgnoreCase(fileName)) {
                algo.DeAllocate(files.get(i));
                files.remove(files.get(i));
                System.out.println("File Removed Successfully");
                return;
            }
        }
        System.out.println("File not found");
    }

    public void deleteFolder(String name) {
        for (int i=0;i<sub.size();i++){
            if (sub.get(i).getName().equalsIgnoreCase(name)) {
                sub.get(i).deleteAll();
                sub.remove(sub.get(i));
                System.out.println("Folder Removed Successfully");
                return;
            }
        }
        System.out.println("Folder Not Found");
    }

    public void deleteAll()
    {
        for (int i=0;i<files.size();i++) {
            algo.DeAllocate(files.get(i));
        }
        files.clear();
        for (int i=0;i<sub.size();i++){
            sub.get(i).deleteAll();
        }
    }

    public void displayDiskStatus() {
        for (int i=0; i<files.size();i++)
        {
            files.get(i).displayAllocatedBlocks();
        }

        for (int i=0; i<sub.size();i++) {
            sub.get(i).displayDiskStatus();
        }
    }

    public void displayDiskStructure(int l) {

        System.out.print( Name+ " / " );
        for (int i=0;i< files.size();i++)
        {
            System.out.println( " "+"//"+files.get(i).getFileName());
        }

        for (int i=0;i<sub.size();i++) {
            sub.get(i).displayDiskStructure(l++);
        }
    }

    public Directory searchSubDirectory(String name) {
        for (int i=0;i<sub.size();i++){
            if (sub.get(i).getName().equalsIgnoreCase(name))
                return sub.get(i);
        }
        return null;
    }

    public String getName() {
        return Name;
    }

    public FileSystem getAlgo() {
        return algo;
    }

    public void grant( String n,boolean create , boolean delete ){
        for (int i=0;i<accesses.size();i++){
            if(accesses.get(i).userName.equalsIgnoreCase(n)){
                accesses.get(i).create=create;
                accesses.get(i).delete=delete;
                for (int j=0;j<sub.size();j++){
                    sub.get(j).grant(n,create,delete);
                }
                return;
            }
        }
        access a=new access(n,create,delete);
        accesses.add(a);
        for (int j=0;j<sub.size();j++)
        {
            sub.get(j).grant(n,create,delete);
        }

    }

    public boolean makeAccess(String name) {
        for (int i=0 ;i<accesses.size(); i++) {
            if (accesses.get(i).userName.equalsIgnoreCase(name) && accesses.get(i).create){
                return true;
            }
        }
        return false;
    }

    public boolean DeleteAccess(String name) {
        for (int i=0;i< accesses.size();i++) {
            if (accesses.get(i).userName.equalsIgnoreCase(name) && accesses.get(i).delete){
                return true;
            }
        }
        return false;
    }

}