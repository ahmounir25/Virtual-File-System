import java.util.ArrayList;

public class V_File
{
    private final String Name;
    private final int size;
    private ArrayList<Integer> data;
    private String type;
    public V_File(String name, int size)
    {
        Name = name;
        this.size = size;
    }

    public void displayAllocatedBlocks()
    {
        System.out.print(Name + "\t");
        if (type.equalsIgnoreCase("contiguous")) {
            System.out.println("Start at : "+data.get(0) + " "+" Length : " + data.get(1));
        }
        else if (type.equalsIgnoreCase("linked")) {
            System.out.println("Start at : "+data.get(0) + " "+ "End at : " + data.get(1));
            for (int j = 2; j < data.size() - 1; j++) {
                System.out.println(data.get(j) + " " + data.get(j + 1));
            }
        }
        else {
            System.out.println("Index Block :- "+data.get(0));
            System.out.print( data.get(0)+ " -> ");
            for (int i = 1; i < data.size(); i++) {
                System.out.print(data.get(i)+" ");
            }
            System.out.println();
        }
    }

    public String getFileName()
    {
        return Name;
    }

    public int getSize()
    {
        return size;
    }
    public ArrayList<Integer> getData() {
        return data;
    }

    public void AlgoType(String type) {
        this.type = type;
    }

    public void setData(ArrayList<Integer> data)
    {
        this.data = data;
    }
}