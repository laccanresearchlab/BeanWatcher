/*
 * Port.java
 *
 * Created on September 24, 2003, 1:39 PM
 */

package componentmodel;

/**
 *
 * @author  alla
 */
public class Port {
    public static final String IN_PORT = "in_port";
    public static final String OUT_PORT = "out_port";
    public static final String INT = "int";
    public static final String BYTE = "byte";
    public static final String SINGLE = "";
    public static final String ONED = "[]";
    public static final String TWOD = "[][]";
    public static final String THREED = "[][][]";
    public static final String FOURD = "[][][][]";
    
    /** Variable for name of port*/
    private String name;
    /** Variable for type port, in or out*/
    private String type;
    
    /** Creates a new instance of Port */
    public Port(String _name, String _type, String _dimension) {
        name = _name;
        type = _type + _dimension;
    }
    
    /*
     * This method is to set name of port
     * @param _name the new value for name port
     *
    private void setName(String _name){
        name = _name;
    }
    
    /**
     * This method is to set type of port
     * @param _type the new value for type port
     *
    private void setType(String _type, String _dimension){
        type = _type;
    }
    */
    /**
     * This method is to get name of port
     * @return the value of name port
     */
    public String getName(){
        return name;
    }
    
    /**
     * This method is to get type of port
     * @return the value of type port
     */
    public String getType(){
        return type;
    }
}
