public abstract class Commands {
    private final String Username;
    private final String pass;

    Commands(String name,String pass) {
        this.Username = name;
        this.pass = pass;
    }

    public void createFolder(String[] arr, Directory d) {

        if (arr.length == 2)
        {
            String[] path = arr[1].split("/");
            Directory directory = getDirectory(path, d);
           /* for(int i=0;i<path.length;i++){
                System.out.println(path[i]);
            }*/
            if (directory == null)
            {
                System.out.println("Path not Found");
                return;
            }
            if (RightforCreate(directory)) {
                directory.createFolder(path[path.length - 1]);
            }
            else {
                System.out.println("This User Can't Create Folder ");

            }

        }

        else {
            System.out.println(" Enter the Path Correctly ..  ");
        }
    }

    public void createFile(String[] arr, Directory d)
    {
        if (arr.length == 3) {
            String[] path = arr[1].split("/");
            Directory directory = getDirectory(path, d);
            if (directory == null) {
                System.out.println("Path not Found");
                return;
            }
            String size = arr[arr.length - 1];
            if (RightforCreate(directory))
            {
                directory.createFile(path[(path.length - 1)], Integer.parseInt(size));
            }
            else {
                System.out.println("This User Can't Create the File ");
            }
        }
            else {
                System.out.println("Enter the Path Correctly .. ");
            }
    }

    public void deleteFile(String[] arr, Directory d)
    {
        if (arr.length == 2) {
            String[] path = arr[1].split("/");
            Directory directory = getDirectory(path, d);
            if (directory == null) {
                System.out.println("Path not Found");
                return;
            }
            if (RightforDelete(directory)) {
                directory.deleteFile(path[path.length - 1]);
            }
            else {
                System.out.println("This User Can't Remove the File ");
            }
        }
        else {
            System.out.println("Enter the Path Correctly .. ");
        }
    }

    public void deleteFolder(String[] arr, Directory d) {
        if (arr.length == 2) {
            String[] path = arr[1].split("/");
            Directory directory = getDirectory(path, d);
            if (directory == null) {
                System.out.println("Path not Found");
                return;
            }
            if (RightforDelete(directory)){
                directory.deleteFolder(path[path.length - 1]);
            }
            else {
                System.out.println("This User Can't Remove the Folder ");
            }
        }
        else {
            System.out.println("Enter the Path Correctly .. ");
        }
    }

    private Directory getDirectory(String[] path, Directory d) {
        Directory curDirectory = d;
        if ( !path[0].equalsIgnoreCase("root") ) {
            return null;
        }
        for (int i = 0; i < path.length - 2; i++) {
            if (path[i].equalsIgnoreCase(curDirectory.getName()))
            {
                if (curDirectory.searchSubDirectory( path[i + 1] )  != null )
                {
                    curDirectory = curDirectory.searchSubDirectory(path[i + 1]);
                }
                else {return null;}
            }
            else {return null;}
        }
        return curDirectory;
    }

    public void displayDiskStatus(String[] arr, Directory d) {
        if (arr.length == 1)
        {
            System.out.println(" Empty Space = " + d.getAlgo().emptyBlocks());
            System.out.println(" Allocated Space = " + d.getAlgo().allocatedBlocks());
            System.out.println(" All Blocks in the Disk :- " + d.getAlgo().TotalBlocks());
            System.out.println(" Allocated Blocks for Each File :- ");
            d.displayDiskStatus();
        }
        else {
            System.out.println("Invalid ..");}
    }

    public void displayDiskStructure(String[] arr, Directory d) {
        if (arr.length == 1)
        {
            d.displayDiskStructure(0);
        }
        else {
            System.out.println("Invalid .. ");
        }
    }

    public void tellUser(String[] arr) {
        if (arr.length == 1) {
            System.out.print("Current User is :- ");
            System.out.println(Username);
        }

    }
    private boolean RightforCreate(Directory directory) {
        return directory.makeAccess(Username);
    }

    private boolean RightforDelete(Directory directory) {
        return directory.DeleteAccess(Username);
    }
    public String getUsername() {
        return Username;}

    public String getPassword(){
        return pass;
    }
}