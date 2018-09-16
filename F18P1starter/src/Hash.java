
/**
 * Stub for hash table class
 *
 * @author CS3114 staff
 * @version August 2018
 */

public class Hash {

    /**
     * size of the hash table
     */
    private int size;

    /**
     * number of entries in hash table
     */
    private int totalEntries;

    /**
     * Hash Table of record handles
     */
    private Handle[] handles;

    /**
     * The Memory Manager
     */
    private MemoryManager mem;


    /**
     * Create a new Hash object.
     * 
     * @param hashSize
     *            The requested size of the hash table
     * @param memSize
     *            The requested size of the memory pool
     * 
     */
    public Hash(int hashSize, int memSize) {
        handles = new Handle[hashSize];
        totalEntries = 0;
        size = hashSize;
        mem = new MemoryManager(memSize);
    }


    /**
     * Insert e into hash table HT
     * 
     * @param name
     *            The value to be added
     */
    public void add(String name) {
        int home = h(name, size); // Home position for name
        int pos = home; // Init probe sequence
        int tomb = -1; // First tomb stone found
        // remains -1 if not found

        for (int i = 0; handles[pos] != null && i < size * 5; i++) {
            pos = (int)((home + Math.pow(i, 2)) % size); // probe
            if (handles[pos] == null) { // end of array
                continue;
            }
            if (name.compareTo(handles[pos].getHandleName()) == 0) { 
                // duplicates
                System.out.println("|" + name + "|"
                    + " duplicates a record already in the Name database.");
                return;
            }
            else if (tomb == -1 && handles[pos].getHandleName().compareTo(
                "[del]") == 0) {
                // tomb stone
                tomb = pos;
            }
        }

        if (tomb == -1 && handles[pos] != null) {
            return;
        }
        else if (tomb != -1) {
            pos = tomb;
        }
        handles[pos] = new Handle(size(name.length()), mem.insert(name
            .getBytes(), name.length()), name, name.length());
        totalEntries++;

        // Check Hash Table size, and update if necessary
        if (totalEntries > size / 2) {
            size *= 2;
            newSize(size);
        }
        System.out.println("|" + name + "|"
            + " has been added to the Name database.");

    }


    /**
     * Calculate the space needed in the memory manager
     * 
     * @param length
     *            of handle
     * @return space needed
     */
    public int size(int length) {
        int powTwo = 1;
        while (powTwo < length) {
            powTwo *= 2;
        }
        return powTwo;
    }


    /**
     * Update the size of the hash table
     * 
     * @param goalSize
     *            Size to update to
     */
    private void newSize(int goalSize) {

        // int[] newKeys = new int[goalSize]; //Make new arrays
        Handle[] newhandles = new Handle[goalSize];
        // Copy keys and handles to new size
        for (int i = 0; i < goalSize / 2; i++) {
            // newKeys[i] = keys[i];
            if (handles[i] != null) {
                // Init probe sequence
                int pos = h(handles[i].getHandleName(), size);
                for (int j = 0; newhandles[pos] != null; j++) {
                    pos = (int)((pos + Math.pow(j, 2)) % size);
                }
                newhandles[pos] = new Handle(handles[i].getHandleSize(),
                    handles[i].getHandlelocation(), handles[i].getHandleName(),
                    handles[i].getHandlelength());
            }
        }
        handles = newhandles;
        System.out.println("Name hash table size doubled to " + goalSize
            + " slots.");
    }


    /**
     * Print commands
     * 
     * @param cmd
     *            The command
     */
    public void print(String cmd) {
        if (cmd.compareTo("blocks") == 0) {
            mem.print();
        }
        else if (cmd.compareTo("hashtable") == 0) {
            for (int i = 0; i < size; i++) {
                if (handles[i] != null && handles[i].getHandleName().compareTo(
                    "[del]") != 0) {
                    System.out.println("|" + handles[i].getHandleName() + "| "
                        + i);
                }
            }
            System.out.println("Total records: " + totalEntries);
        }
    }


    /**
     * Delete an entry
     * 
     * @param name
     *            The name of the target
     */
    public void delete(String name) {
        int pos = search(name);
        if (pos != -1) {
            mem.remove(handles[pos]);
            handles[pos] = new Handle(-1, -1, "[del]", -1);
            totalEntries--;
            System.out.println("|" + name + "|"
                + " has been deleted from the Name database.");
        }
        else {
            System.out.println("|" + name + "|"
                + " not deleted because it does not "
                + "exist in the Name database.");
        }
    }


    /**
     * Search for an entry
     * 
     * @param name
     *            The target
     * @return the position
     */
    private int search(String name) {
        int home = h(name, size); // Home position for name
        int pos = home; // Init probe sequence

        for (int i = 0; handles[pos] != null && i < size * 5; i++) {
            pos = (int)((home + Math.pow(i, 2)) % size); // probe
            if (handles[pos] == null) { // end of search
                continue;
            }
            if (name.compareTo(handles[pos].getHandleName()) == 0) { 
                // duplicates
                return pos;
            }
        }
        return -1;
    }


    /**
     * Update a record
     * 
     * @param type
     *            add or remove?
     * @param name
     *            name of the record
     * @param field
     *            field title
     * @param data
     *            information to update
     */
    public void update(String type, String name, String field, String data) {

        int loc = search(name); // location of record in hash table
        if (loc == -1) {
            System.out.println("|" + name + "|"
                + " not updated because it does "
                + "not exist in the Name database.");
            return;
        }

        // search for record, remove if present
        if (!findData(loc, field) && type.equals("delete")) {
            System.out.println("|" + name + "|"
                + " not updated because the field |" + field
                + "| does not exist");
        }
        else if (type.equals("delete")) {
            System.out.println("Updated Record: |" + mem.get(handles[loc])
                + "|");
        }

        // add updated record back in if add
        if (type.equals("add")) {
            String str = mem.get(handles[loc]) + "<SEP>" + field + "<SEP>"
                + data;
            mem.remove(handles[loc]);
            handles[loc].setHandleLength(str.length());
            handles[loc].setHandleSize(size(str.length()));
            handles[loc].setHandleLoc(mem.insert(str.getBytes(), handles[loc]
                .getHandleSize()));
            System.out.println("Updated Record: |" + mem.get(handles[loc])
                + "|");
        }

    }


    /**
     * Search for a record's information
     * 
     * @param pos
     *            the record entry number
     * @return whether it was found
     */
    private boolean findData(int pos, String field) {
        String str = mem.get(handles[pos]);
        int loc = str.indexOf("<SEP>" + field + "<SEP>");
        if (loc == -1) {
            return false;
        }
        int dataL = 10 + field.length();
        String[] split = str.split("<SEP>");
        for (int z = 0; z < split.length; z++) {
            if (split[z].equals(field)) {
                dataL += split[z + 1].length();
            }
        }
        str = str.substring(0, loc) + str.substring(loc + dataL);
        mem.remove(handles[pos]);
        handles[pos].setHandleLength(str.length());
        handles[pos].setHandleSize(size(str.length()));
        handles[pos].setHandleLoc(mem.insert(str.getBytes(), size(str
            .length())));
        return true;
    }


    /**
     * Compute the hash function. Uses the "sfold" method from the OpenDSA
     * module on hash functions
     *
     * @param s
     *            The string that we are hashing
     * @param m
     *            The size of the hash table
     * @return The home slot for that string
     */
    // You can make this private in your project.
    // This is public for distributing the hash function in a way
    // that will pass milestone 1 without change.
    public int h(String s, int m) {
        int intLength = s.length() / 4;
        long sum = 0;
        for (int j = 0; j < intLength; j++) {
            char[] c = s.substring(j * 4, (j * 4) + 4).toCharArray();
            long mult = 1;
            for (int k = 0; k < c.length; k++) {
                sum += c[k] * mult;
                mult *= 256;
            }
        }

        char[] c = s.substring(intLength * 4).toCharArray();
        long mult = 1;
        for (int k = 0; k < c.length; k++) {
            sum += c[k] * mult;
            mult *= 256;
        }

        return (int)(Math.abs(sum) % m);
    }
}
