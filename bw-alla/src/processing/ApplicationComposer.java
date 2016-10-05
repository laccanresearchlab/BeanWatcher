/*
 * ApplicationComposer.java
 *
 * Created on October 7, 2003, 3:17 PM
 */

package processing;

import componentmodel.Connector;
import componentmodel.Component;

/**
 *
 * @author  alla
 */
public class ApplicationComposer {
    /***/
    private Component component[];
    /***/
    private Connector connector[];
    
    /** Creates a new instance of ApplicationComposer */
    public ApplicationComposer(Component _component[], Connector _connector[]) {
        component = _component;
        connector = _connector;
        setDependence();
        @SuppressWarnings("unused")
		ApplicationFactory applicationFactory = new ApplicationFactory(_component);
    }
    
    private void setDependence(){
        int size;
        int sizej;
        
        Component componentIN;
        
        size  = connector.length;
        sizej = component.length;
        for(int i = 0; i < size; i++){
            componentIN = connector[i].getComponentIN();
            for(int j = 0; j < sizej; j++){
                if(!component[j].equals(componentIN)){
                    continue;
                }
                component[j].setDependence(connector[i].getComponentOUT());
                break;
            }
        }
    }
}
