import student.TestCase;

/**
 * @author {Your Name Here}
 * @version {Put Something Here}
 */
public class RecordstoreTest extends TestCase {
    /**
     * Sets up the tests that follow. In general, used for initialization
     */
    public void setUp() {
        // Nothing Here
    }


    /**
     * Get code coverage of the class declaration.
     */
    public void testRInit() {
        Recordstore recstore = new Recordstore();
        assertNotNull(recstore);
        Recordstore.main(null);
    }


    /**
     * Test condition when there are too few arguments
     */
    public void testLessArgs() {
        String[] args = new String[2];
        args[0] = "Two";
        args[1] = "Args";
        Recordstore.main(args);
        assertFalse(systemOut().getHistory().contains("add"));
        assertFalse(systemOut().getHistory().contains("print"));
    }


    /**
     * Test condition when there are the correct
     * number of arguments
     */
    public void testRightArgs() {
        String[] args = new String[3];
        args[0] = "10";
        args[1] = "10";
        args[2] = "recordTest.txt";
        Recordstore.main(args);
        assertTrue(systemOut().getHistory().contains(
            "Invalid arguments for add"));
        assertTrue(systemOut().getHistory().contains(
            "Invalid arguments for print"));
    }


    /**
     * Test for erroneous print commands
     */
    public void testWrongPrint() {
        String[] args = new String[3];
        args[0] = "10";
        args[1] = "10";
        args[2] = "wrongPrint.txt";
        Recordstore.main(args);
        assertTrue(systemOut().getHistory().contains(
            "Invalid arguments for print"));
    }
    
    

}
