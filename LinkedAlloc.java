import java.util.ArrayList;

class Node {
    public int cur = -1;
    public boolean allocated = false;
    public Node next = null;

    public Node() {
    }
}

public class LinkedAlloc implements FileSystem {
    private final ArrayList<Node> listOfBlocks;
    private final int n;

    public LinkedAlloc(int n) {
        this.n = n;
        listOfBlocks = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            listOfBlocks.add(new Node());
        }
    }

    @Override
    public boolean allocate(V_File f) {
        int size = f.getSize();
        int cur = 0, previous = -1;
        ArrayList<Integer> temp = new ArrayList<>();
        int i;
        for (i = 0; i < n; i++) {
            if (cur >= size)
            {
                temp.add(previous);
                break;
            }
            if (!listOfBlocks.get(i).allocated) {
                if (previous != -1)
                {
                    listOfBlocks.get(previous).next = listOfBlocks.get(i);
                }
                listOfBlocks.get(i).allocated = true;
                if (cur == 0)
                {
                    temp.add(i);
                }
                listOfBlocks.get(i).cur = i;
                cur=cur+1;
                previous = i;
            }
        }
        if (i == n && cur >= size)
        {

            temp.add(previous);
        }
        if (cur < size)
        {
            if (!temp.isEmpty())
            {
                Node start = listOfBlocks.get(temp.get(0));
                while (start != null) {
                    Node next = start.next;
                    start.next = null;
                    start.allocated = false;
                    start = next;
                }
            }
            return false;
        }
        Node start = listOfBlocks.get(temp.get(0));
        while (start != null) {
            temp.add(start.cur);
            start = start.next;
        }
        f.AlgoType("linked");
        f.setData(temp);
        return true;
    }

    @Override
    public void DeAllocate(V_File file)
    {
        Node start = listOfBlocks.get(file.getData().get(0));
        while (start != null)
        {
            Node next = start.next;
            start.next = null;
            start.allocated = false;
            start = next;
        }
    }

    @Override
    public int emptyBlocks() {
        int empty = 0;
        for (int i=0; i<listOfBlocks.size();i++) {
            if (!listOfBlocks.get(i).allocated){
                empty++;
            }
        }
        return empty;
    }

    @Override
    public int allocatedBlocks()
    {
        return n - emptyBlocks();
    }

    @Override
    public String TotalBlocks() {
        ArrayList<String> disk = new ArrayList<>();
        for (int i=0;i<listOfBlocks.size();i++ ) {
            if (listOfBlocks.get(i).allocated) {
                disk.add("1");
            }
            else
                disk.add("0");
        }
        return disk.toString();
    }
}

