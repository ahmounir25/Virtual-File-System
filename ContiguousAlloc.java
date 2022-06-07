import java.util.ArrayList;

public class ContiguousAlloc implements FileSystem {
    int size;
    ArrayList<Boolean> listOfBlocks;
    ArrayList<Integer> empty ;

    public ContiguousAlloc( int size)
    {
        this.size=size;
        listOfBlocks=new ArrayList<>();
        empty =new ArrayList<>();
        empty.add(0);
        for (int i=0;i<size;i++){
            listOfBlocks.add(false);
        }
    }
    @Override
    public boolean allocate(V_File f) {
        int mx = 0;
        int mxIdx = -1;
        int cur = 1;
        boolean oneTime = true;
        for (int i = 0; i < size; i++) {

            if (!listOfBlocks.get(i))
            {
                int j;
                for ( j = i + 1; j < size; j++) {
                    if (!listOfBlocks.get(j))
                    {
                        cur++;
                    }
                    else
                        break;
                }

                if (cur >= f.getSize() && (cur < mx || oneTime))
                {
                    mx = cur;
                    mxIdx = i;
                    cur = 1;
                    oneTime = false;
                }

                i = j;
            }
        }
            for (int i = mxIdx; i < mxIdx + f.getSize(); i++) {
                listOfBlocks.set(i, true);
            }

            f.AlgoType("contiguous");
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(mxIdx);
            temp.add(f.getSize());
            f.setData(temp);
        return true;
    }

    @Override
    public void DeAllocate(V_File f) {
        int idx = f.getData().get(0);
        int size = f.getSize();
        for (int i = idx; i < idx + size; i++) {
            listOfBlocks.set(i, false);
        }
    }

    @Override
    public int emptyBlocks() {
        int emptySpace = 0;
        for (int i=0;i< listOfBlocks.size();i++) {
            if (!listOfBlocks.get(i)) {
                emptySpace++;
            }
        }
        return emptySpace;
    }

    @Override
    public int allocatedBlocks() {
        return size - emptyBlocks();
    }

    @Override
    public String TotalBlocks() {
        ArrayList<String> disk = new ArrayList<>();
        for (int i=0;i<listOfBlocks.size();i++) {
            if (listOfBlocks.get(i))
            {
                disk.add("1 ");
            }
            else
                disk.add("0 ");
        }
        return disk.toString();
    }


}
