public  interface FileSystem
{
    boolean allocate(V_File f);
    void DeAllocate(V_File f) ;
    int allocatedBlocks();
    int emptyBlocks();
    String TotalBlocks();
}


