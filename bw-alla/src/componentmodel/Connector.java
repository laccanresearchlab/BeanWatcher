/*
 * Connector.java
 *
 * Created on September 24, 2003, 2:14 PM
 */

package componentmodel;

import bwexception.BeanWatcherException;

/**
 *
 * @author  alla
 */
@SuppressWarnings("unused")
public class Connector {
    /** Represent the component for in port of connector*/
    private Component componentIN;
    /** Represent the component for out port of connector*/
    private Component componentOUT;
    
    /** Creates a new instance of Connector */
    public Connector(Component _componentIN, Component _componentOUT){
        componentIN  = _componentIN;
        componentOUT = _componentOUT;
    }
    
    /**
     * This method is to get in component
     * @return the value for in component
     */
    public Component getComponentIN(){
        return componentIN;
    }
    
    /**
     * This method is to get out component
     * @return the value for out component
     */
    public Component getComponentOUT(){
        return componentOUT;
    }

    /**
     *
     */
    public boolean hasComponent(Component _checkComp){
        if((_checkComp.equals(componentOUT)) || (_checkComp.equals(componentIN))) {
            return true;
        } else {
            return false;
        }
    }
}
