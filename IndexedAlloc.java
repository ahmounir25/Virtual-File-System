import java.util.ArrayList;

public class IndexedAlloc implements FileSystem {
    private final ArrayList<Boolean> listOfBlocks;
    private final int s;

    public IndexedAlloc(int s) {
        this.s= s;
        listOfBlocks = new ArrayList<>();
        for (int i = 0; i < s; i++) {
            listOfBlocks.add(false);
        }
    }

    @Override
    public boolean allocate(V_File file) {
        int size = file.getSize() + 1;
        ArrayList<Integer> tmp = new ArrayList<>();
        int count = 0;
        for (int i = 0 ; i < s && count < size; i++) {
            if (!listOfBlocks.get(i)) {
                tmp.add(i);
                listOfBlocks.set(i, true);
                count++;
            }
        }
        if (count < size)
        {
            for (int i=0;i< tmp.size();i++)
            {
                listOfBlocks.set(tmp.get(i), false);
            }
            return false;
        }
        file.AlgoType("Indexed");
        file.setData(tmp);
        return true;
    }

    @Override
    public void DeAllocate(V_File file)
    {
        for (int i=0; i<file.getData().size() ;i++)
        {
            listOfBlocks.set(file.getData().get(i), false);
        }
    }
    @Override
    public int emptyBlocks() {
        int cntr = 0;
        for (int i=0;i< listOfBlocks.size();i++) {
            if (!listOfBlocks.get(i)){
                cntr++;
            }
        }
        return cntr;
    }

    @Override
    public int allocatedBlocks()
    {
        return s - emptyBlocks();
    }

    @Override
    public String TotalBlocks() {
        ArrayList<String> disk = new ArrayList<>();
        for (int i=0;i<listOfBlocks.size();i++ ) {
            if (listOfBlocks.get(i)) {
                disk.add("1");
            }
            else
                disk.add("0");
        }
        return disk.toString();
    }
}
