import student.TestCase;

/**
 * The class containing the I/O functions for hash table.
 *
 * @author Jake Mokuvos, Clay Cunningham
 * @version 1
 */

public class HashParserTest extends TestCase {

    private Hash hash;


    /**
     * Sets up the conditions
     */
    public void setUp() {
        hash = new Hash(3, 64);
    }


    /**
     * Tests a sample input
     */
    public void testHashAdd() {
        HashParser parser = new HashParser();
        parser.getClass();
        HashParser.parseHash("parseTest.txt", hash);
        assertTrue(systemOut().getHistory().contains(
            "|new thing| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains("Total records: 2"));
        assertTrue(systemOut().getHistory().contains("|new thing| 3"));
        assertTrue(systemOut().getHistory().contains("|new thing| 10"));
        assertTrue(systemOut().getHistory().contains(
            " duplicates a record already in the Name database."));
    }


    /**
     * Tests another sample input
     */
    public void testHashAddTwo() {
        HashParser.parseHash("P1sampleInput.txt", hash);
        assertTrue(systemOut().getHistory().contains(
            "|Fullmetal Alchemist| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains("|Death Note| 0\n"
            + "|Spirited Away| 1\n" + "|Can You Handle?| 4\n"
            + "|death note| 5\n" + "|Fullmetal Alchemist| 7\n"
            + "|Castle in the Sky| 11\n" + "Total records: 6"));
    }


    /**
     * Tests another sample input
     */
    public void testHashDelete() {
        HashParser.parseHash("deleteTest.txt", hash);
        assertTrue(systemOut().getHistory().contains(
            "|one| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|one| has been deleted from the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|nothing| not deleted because it does not "
                + "exist in the Name database."));
    }


    /**
     * Tests another sample input
     */
    public void testLoop() {
        Hash hashLoop = new Hash(4, 64);
        HashParser.parseHash("loop.txt", hashLoop);
        assertTrue(systemOut().getHistory().contains(
            "|test| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|new thing| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains("|test| 0\n"
            + "|new thing| 1\n" + "Total records: 2"));
    }


    /**
     * Tests another sample input
     */
    public void testDeleteLoop() {
        Hash hashLoop = new Hash(4, 64);
        HashParser.parseHash("loopDel.txt", hashLoop);
        assertTrue(systemOut().getHistory().contains(
            "|test| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|new thing| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "|double| not deleted because it does not "
                + "exist in the Name database."));
    }


    /**
     * Tests update commands
     */
    public void testUpdate() {
        HashParser.parseHash("tester.txt", hash);
        assertTrue(systemOut().getHistory().contains(
            "|Death Note| not updated because the field "
                + "|Doesn't Exist| does not exist"));
        assertTrue(systemOut().getHistory().contains(
            "Updated Record: |Death Note<SEP>Genre<SEP>Anime|"));
        assertTrue(systemOut().getHistory().contains(
            "Updated Record: |Death Note|"));
    }

    /**
     * test a large single record name 
     */
    public void testBigString() {
        HashParser.parseHash("bigString.txt", hash);
        assertTrue(systemOut().getHistory().contains(
            "|the best we can do to make you resize your memory after"
            + " all we are just trying| has been added to the Name database."));
        assertTrue(systemOut().getHistory().contains(
            "No free blocks are available."));
    }

    /**
     * test multiple cases for merging blocks 
     */
    public void testMerge() {
        HashParser.parseHash("merge.txt", hash);
        assertTrue(systemOut().getHistory().contains("16: 48\n" + "32: 64"));
        assertTrue(systemOut().getHistory().contains(
            "Updated Record: |xyz movie|"));
    }

    
    /**
     * Test memory adding freeing and merging 
     */
    public void testSimple() {
        HashParser.parseHash("simple.txt", hash);
        assertTrue(systemOut().getHistory().contains(
            "Updated Record: |Spiderman<SEP>Genre<SEP>movie|"));
        assertTrue(systemOut().getHistory().contains("16: 16"));
    }

}
