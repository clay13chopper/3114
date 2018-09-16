
/**
 * The class containing the memory manager tests.
 *
 * @author Jake Mokuvos, Clay Cunningham
 * @version 1
 */

public class MemoryManagerTest extends student.TestCase {

    /**
     * Set up for tests
     * Not used
     */
    public void setUp() {
        // Nothing here
    }


    /**
     * tests breaking up free blocks
     */
    public void testBreak() {
        MemoryManager mem = new MemoryManager(128);
        String str = "asdfghjklzxcvbnm";
        mem.insert(str.getBytes(), 16);
        str = "asdfghjklzxcvbnmasdf";
        mem.insert(str.getBytes(), 32);
        mem.print();
        assertTrue(systemOut().getHistory().contains("16: 16\n" + "64: 64"));
    }


    /**
     * tests merging free blocks
     */
    public void testMerge() {
        MemoryManager mem = new MemoryManager(128);
        String str = "Does this work";
        mem.insert(str.getBytes(), 16);
        mem.print();
        Handle hand = new Handle(16, 0, str, 16);
        mem.remove(hand);
        mem.print();
        assertTrue(systemOut().getHistory().contains("16: 16\n" + "32: 32\n"
            + "64: 64\n" + "128: 0"));
    }

}
