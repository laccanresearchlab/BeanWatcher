/*
 * HtmlParser.java
 *
 * Created on March 31, 2000, 3:55 PM
 */

package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Hashtable;

/**
 * @author  Alla
 * @version 0.0.0.1
 */

public class Parser {

    /* Replaces fields for values in TEXT file. Each field will
    must be specified between flags.
    Example: For represent  field 'phone' with flag '%%', use: %%phone%%
    Atention: only one field must be specified for line.  */

    /**
     * Flag which delimits name of field
     */
    protected String FLAG = "%%";

    /**
     * Store fields and their values
     */
    protected Hashtable<String, String> fields;

    /**
     * Indicates next line of insertion
     */
    protected int numberLine;

    /**
     * Create a new ParserFile
     */
    public Parser() {
        fields = new Hashtable<String, String>();
    }

    /** Clear all registers of  the ParserFile */
    public void reset() {
        numberLine = 0;
    }

    /** Adds a register (field, value) */
    public void addRegister(String field, String value) {
	fields.put(field, value);
    }

    /** Replace fields for values on file specified */
    public StringBuffer parseFile(String fileName) throws IOException, FileNotFoundException {
        boolean      eof;
        File         file;
        String       line;
        String       fieldName;
        StringBuffer buffer;
        FileReader   pointerFile;
        BufferedReader bufferFile;

        buffer = new StringBuffer("");

        file = new File(fileName);
        if (!file.exists()) {
            throw new FileNotFoundException("Can't open file "+fileName+". File not found.");
        }

        pointerFile = new FileReader(fileName);
        bufferFile = new BufferedReader(pointerFile);
        
        int startColumn; // Location of first flag found
        int endColumn;   // Location of second flag found
        
        eof = false;
        while (!eof) {
            line = bufferFile.readLine();

            if (line == null) {
                eof = true;
                continue;
            } else {
                /*  Looking for the first occurrence of FLAG  */
                startColumn = line.indexOf(FLAG);

                if (startColumn < 0) {      /* if FLAG doesn't found */
                    buffer.append(line);
                }
                else {                      /* if FLAG found then... */
                    buffer.append(line.substring(0,startColumn));
                    endColumn = line.indexOf(FLAG, startColumn+FLAG.length());

                    /* looking for the second (and last) occurrence of FLAG in the line  */
                    if (endColumn >= 0) {
                        /* if we found two occurrences of FLAG, then extract
                        name of field and get its respective values */
                        fieldName = line.substring(startColumn+FLAG.length(), endColumn);

                        if (fields.get(fieldName) == null) {
                            buffer.append(FLAG+fieldName+FLAG);
                            // throw new NullPointerException("Get null for '"+fieldName+"' field.");
                        } else {
                            buffer.append(fields.get(fieldName));
                        }
                    }

                    /*  Append remainig portion of line  */
                    buffer.append(line.substring(endColumn+FLAG.length()));
                }

                /*  Append specials caracters for mark end of line  */
                buffer.append("\r\n");
            }
        }
        bufferFile.close();

        return buffer;
    }
}