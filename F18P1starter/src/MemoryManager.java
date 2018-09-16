
/**
 * The class containing the memory manager.
 *
 * @author Jake Mokuvos, Clay Cunningham
 * @version 1
 */

public class MemoryManager {

    /**
     * Byte array for storing records
     */
    private byte[] info;

    /**
     * List for keeping track of free blocks
     */
    private DLList<Block> blocks;


    /**
     * Constructor for memory manager
     * 
     * @param poolsize
     *            size of the pool being used *must be power of 2
     */
    public MemoryManager(int poolsize) {
        info = new byte[poolsize];
        blocks = new DLList<Block>();
        blocks.add(0, new Block(poolsize, 0));
    }


    /**
     * Insert a record into the memory manager
     * 
     * @param space
     *            the bytes to be inserted
     * @param size
     *            the size of the power of two block that is needed
     * @return returns the location placed in the byte array
     */
    public int insert(byte[] space, int size) {

        int loc = -1; // record the of placment within byte array
        int currSize = info.length + 1; // Size of the next free block in the
                                        // list that is
        // closest to our block size
        int index = blocks.size(); // index within the free block list

        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getSize() == size) {
                index = blocks.get(i).getLoc();
                for (int z = 0; z < space.length; z++) {
                    info[z + index] = space[z];
                }
                blocks.remove(i);
                return index;
            }
            if (blocks.get(i).getSize() > size && currSize > blocks.get(i)
                .getSize()) {
                loc = blocks.get(i).getLoc();
                currSize = blocks.get(i).getSize();
                index = i;
            }
        }

        if (loc == -1) {
            byte[] newInfo = new byte[info.length * 2];
            for (int i = 0; i < info.length; i++) {
                newInfo[i] = info[i];
            }
            if (!blocks.isEmpty() && blocks.get(0).getSize() == info.length) {
                mergeBlocks(blocks.size(), info.length, info.length);
            }
            else {
                blocks.add(new Block(info.length, info.length));
            }
            info = newInfo;
            System.out.println("Memory pool expanded to be " + info.length
                + " bytes.");
            return insert(space, size);
        }

        for (int z = 0; z < space.length; z++) {
            info[z + loc] = space[z];
        }

        Block newBlock; // New block to insert into free list

        blocks.remove(index);
        currSize /= 2;
        while (currSize >= size) {
            newBlock = new Block(currSize, loc + currSize);
            int newSpot = findSpot(currSize, loc);
            blocks.add(newSpot, newBlock);
            currSize /= 2;
        }

        return loc;
    }


    /**
     * Find Location for new block in free list
     * 
     * @param goalSize
     *            size of block being inserted
     * @param goalLoc
     *            location in memory of block being inserted
     * @return location in the list where inserted
     */
    private int findSpot(int goalSize, int goalLoc) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i).getSize() > goalSize || (blocks.get(i)
                .getSize() == goalSize && goalLoc < blocks.get(i).getLoc())) {

                return mergeBlocks(i, goalSize, goalLoc);
            }
            if (i == blocks.size() - 1 && blocks.get(i).getSize() == goalSize) {
                i++;
                return mergeBlocks(i, goalSize, goalLoc);
            }
        }
        return blocks.size();
    }


    /**
     * Checks to see if inserted memory block can merge with adjacent blocks
     * 
     * @param index
     *            location to be inserted in list
     * @param sz
     *            size of the block
     * @param loc
     *            location in memory
     * @return location of block in free list -1 if block are merging
     */
    private int mergeBlocks(int index, int sz, int loc) {
        if (loc % (sz * 2) != 0 && index > 0 && sz == blocks.get(index - 1)
            .getSize() && loc == blocks.get(index - 1).getLoc() + sz) {
            blocks.remove(index - 1);
            int newSpot = findSpot(sz * 2, loc - sz);
            if (newSpot != -1) {
                blocks.add(newSpot, new Block(sz * 2, loc - sz));
            }
            return -1;
        }
        else if (loc % (sz * 2) == 0 && index < blocks.size() && sz == blocks
            .get(index).getSize() && loc + sz == blocks.get(index).getLoc()) {
            blocks.remove(index);
            int newSpot = findSpot(sz * 2, loc);
            if (newSpot != -1) {
                blocks.add(newSpot, new Block(sz * 2, loc));
            }
            return -1;
        }

        return index;
    }


    /**
     * Removing block from memory and adding it back
     * to the free list
     * 
     * @param theHandle
     *            the handle for the block being removed
     */
    public void remove(Handle theHandle) {

        int newSpot = findSpot(theHandle.getHandleSize(), theHandle
            .getHandlelocation());
        if (newSpot != -1) {
            blocks.add(newSpot, new Block(theHandle.getHandleSize(), theHandle
                .getHandlelocation()));
        }
    }


    /**
     * Retrieves record from memory
     * 
     * @param theHandle
     *            record to be retrieved
     * @return record decoded into string form
     */
    public String get(Handle theHandle) {
        String ret = new String(info); // string to return
        return ret.substring(theHandle.getHandlelocation(), theHandle
            .getHandlelocation() + theHandle.getHandlelength());
    }


    /**
     * print free block list
     * prints no free blocks if list is empty
     */
    public void print() {

        if (blocks.isEmpty()) {
            System.out.println("No free blocks are available.");
            return;
        }

        int currSize = blocks.get(0).getSize(); // current size of blocks
        System.out.print(blocks.get(0).getSize() + ":");
        for (int i = 0; i < blocks.size(); i++) {
            if (currSize != blocks.get(i).getSize()) {
                currSize = blocks.get(i).getSize();
                System.out.print("\n" + blocks.get(i).getSize() + ":");
            }
            System.out.print(" " + blocks.get(i).getLoc());
        }
        System.out.print("\n");
    }


    /**
     * 
     * @author Clayton Cunningham and Jake Mokuvos
     * 
     *         Class for representing blocks of memory
     *
     */
    private class Block {

        /**
         * Size of block
         */
        private int size;
        /**
         * location in memory
         */
        private int loc;


        /**
         * 
         * @param s
         *            size of block
         * @param l
         *            location in memory
         */
        public Block(int s, int l) {
            size = s;
            loc = l;
        }


        /**
         * 
         * @return size of memory block
         */
        public int getSize() {
            return size;
        }


        /**
         * 
         * @return location in memory of the block
         */
        public int getLoc() {
            return loc;
        }
    }
}
