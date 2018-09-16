
import java.io.File;
import java.util.Scanner;

/**
 * The class containing the I/O functions for hash table.
 *
 * @author Jake Mokuvos, Clay Cunningham
 * @version 1
 */
public class HashParser {

    /**
     * 
     * @param filename
     *            the name of the file to parse
     * @param table
     *            the hashtable
     * @return true if done with out error,
     * 
     *         Based on code in open dsa 2.07
     */
    public static boolean parseHash(String filename, Hash table) {

        try {
            Scanner sc = new Scanner(new File(filename));
            Scanner scf; // Declare two scanners one to read the file and one to
                         // read the text pulled from the file
            String title = "";
            while (sc.hasNextLine()) { // While we have text to read
                title = "";
                String line = sc.nextLine(); // Get our next line
                if (line.length() == 0) // handle skipped lines in input
                                        // commands
                {
                    continue;
                }
                scf = new Scanner(line); // Create a scanner from this line
                if (!scf.hasNext()) // Check for no arguments
                {
                    continue;
                }
                String cmd = scf.next(); // Grab the command
                if (cmd.equals("add")) {
                    if (!scf.hasNext()) // Check for no arguments after command
                    {
                        System.out.println("Invalid arguments for add");
                        continue;
                    }
                    title += scf.next();
                    while (scf.hasNext()) // Loop to piece title together
                    {
                        title += " ";
                        title += scf.next();
                    }
                    table.add(title);
                    // code for add case
                }

                else if (cmd.equals("delete")) {
                    if (!scf.hasNext())// Check for no arguments after command
                    {
                        System.out.println("Invalid arguments for delete");
                        continue;
                    }
                    title += scf.next();
                    while (scf.hasNext()) // Loop to piece title together
                    {
                        title += " ";
                        title += scf.next();
                    }
                    table.delete(title);
                }
                else if (cmd.equals("update")) {
                    if (!scf.hasNext())// Check for no arguments after command
                    {
                        System.out.println("Invalid arguments for update");
                        continue;
                    }
                    cmd = scf.next();
                    scf.useDelimiter("<SEP>");
                    if (cmd.equals("add") || cmd.equals("delete")) {
                        String temp;
                        temp = scf.next();
                        title = temp.replaceAll("\\s+", " ");
                        title = title.trim();
                        temp = scf.next();
                        String field = temp.replaceAll("\\s+", " ");
                        field = field.trim();
                        String val = "";
                        if (cmd.equals("add")) {
                            temp = scf.next();
                            val = temp.replaceAll("\\s+", " ");
                            val = val.trim();
                        }

                        table.update(cmd, title, field, val);
                    }
                    else {
                        System.out.println("Invalid arguments for update");
                        continue;
                    }
                }
                else if (cmd.equals("print")) {

                    if (!scf.hasNext())// Check for no arguments after command
                    {
                        System.out.println("Invalid arguments for print");
                        continue;
                    }
                    title += scf.next();
                    if (title.equals("hashtable")) {
                        table.print("hashtable");
                    } 
                    else if (title.equals("blocks")) {
                        table.print("blocks");
                    }
                }
                scf.close();
            }
            sc.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}