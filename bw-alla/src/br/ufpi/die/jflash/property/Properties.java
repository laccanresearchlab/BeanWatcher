/*
 * Properties.java
 *
 * Created on November 17, 2003, 4:17 PM
 */

package br.ufpi.die.jflash.property;

/**
 *
 * @author  alla
 */
public class Properties {
    private String name;
    
    private String type;
    
    private String value;
    
    /** Creates a new instance of Properties */
    public Properties() {
    }
    
    public void setName(String _name){
        name = _name;
    }
    
    public void setType(String _type){
        type = _type;
    }
    
    public void setValue(String _value){
        value = _value;
    }
    
    public String getName(){
        return name;
    }
    
    public String getType(){
        return type;
    }
    
    public String getValue(){
        return value;
    }
}
