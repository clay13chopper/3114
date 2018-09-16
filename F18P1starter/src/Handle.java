
/**
 * The class containing handles.
 *
 * @author Jake Mokuvos, Clay Cunningham
 * @version 1
 */

public class Handle {

    /**
     * Size of the block needed 
     */
    private int size;
    /**
     * Location in memory 
     */
    private int loc;
    /**
     * Name of the record 
     */
    private String name;
    /**
     * Actual length of the record
     */
    private int len;
    
    /**
     * Constructor
     * @param handleSize size of the handle
     * @param location   location of allocation
     * @param n      name of record
     * @param length actual length of the record 
     */
    public Handle(int handleSize, int location, String n, int length) {
        size = handleSize;
        loc = location;
        name = n;
        len = length;
    }
    
    /**
     * 
     * @return The handle size 
     */
    public int getHandleSize() {
        return size;
    } 
    
    /**
     * 
     * @return The handle location 
     */
    public int getHandlelocation() {
        return loc;
    } 
    
    /**
     * 
     * @return The handle name
     */
    public String getHandleName() {
        return name;
    } 
    
    /**
     * 
     * @return The handle length 
     */
    public int getHandlelength() {
        return len;
    } 
    
    /**
     * 
     * @param sz the new size for the handle 
     */
    public void setHandleSize(int sz) {
        size = sz; 
    }
    
    /**
     * 
     * @param l the new location for the handle 
     */
    public void setHandleLoc(int l) {
        loc = l; 
    }
    
    /**
     * 
     * @param length the new length for the handle 
     */
    public void setHandleLength(int length) {
        len = length; 
    }
    
}
