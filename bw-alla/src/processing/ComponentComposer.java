/*
 * ApplicationComposer.java
 *
 * Created on October 7, 2003, 3:17 PM
 */

package processing;

import componentmodel.Connector;
import componentmodel.Component;
import br.ufpi.die.jflash.datastore.ManagerDataStore;
import br.ufpi.die.jflash.dto.ColectorDTO;
import br.ufpi.die.jflash.property.Properties;
import java.util.Vector;
import util.Config;

/**
 * @author  alla
 * @versio 0.0.0.2
 */
public class ComponentComposer {
    /** Variable for component name with to was created*/
    @SuppressWarnings("unused")
	private String componentName;
    /** List of subcomponents used for created component*/
    private Component component[];
    /** List of connector used for created component*/
    private Connector connector[];
    /** List of components order for importance code*/
    private Component order[];
    /** List of components for out interactive*/
    private Component outComps[];
    /** Count order components*/
    private int count = 0;
    
    /** Creates a new instance of ApplicationComposer */
    public ComponentComposer(String _componentName, Component[] _component, Connector[] _connector) {
        // Set default vaules
        componentName = _componentName;
        component     = _component;
        connector     = _connector;
        order = new Component[component.length];
        // Set dependence between components
        setDependence();
        //
        setProperties();
        // order the components for to be generate code
        order();
        // Generate component call
        @SuppressWarnings("unused")
		ComponentFatherFactory componentFactory = new ComponentFatherFactory(_componentName, order);
    }
    
    public void setProperties(){
        ManagerDataStore mds;
        ColectorDTO      colector;
        
        Vector<?> allCompDescription;
        Vector<Properties> properVector = new Vector<Properties>();
        
        String name;
        Vector<String> componentName = new Vector<String>();
        int sizeDesc;
        @SuppressWarnings("unused")
		int posx, posy;
        
        Properties prt[];
        
        mds = new ManagerDataStore(Config.getChildConfigFile());

        for (int i = 0; i < component.length; i++){
            componentName.add(component[i].getType());
        }
        
        allCompDescription = mds.loadRepository();
        sizeDesc = allCompDescription.size();
        int i = 0;
        while((i < sizeDesc)){
            if(allCompDescription.get(i) instanceof ColectorDTO){
                colector = (ColectorDTO)allCompDescription.get(i++);
                name = colector.getComponent_name();
                if(!componentName.contains(name)){
                    continue;
                }else {
                    posx = colector.getPosX();
                    properVector = new Vector<Properties>();
                    while((i < sizeDesc) && (allCompDescription.get(i) instanceof Properties)){
                        properVector.add((Properties)allCompDescription.get(i++));
                    }
                    prt = new Properties[properVector.size()];
                    for(int k = 0; k < properVector.size(); k++){
                       prt[k] = (Properties)properVector.get(k);
                    }
                    int index = componentName.indexOf(name);
                    this.component[index].setPropertiesComponent(prt);
                    this.component[index].setColectorDTO(colector);
                }
            } else {
                i++;
            }
        }
    }
    
    
    /**
     * This method is to set dependence between components, this method
     * analyse the connector useds in workplace
     */
    private void setDependence(){
        int sizeConn;
        int sizeComp;
        
        Component componentIN;
        
        sizeConn  = connector.length;
        sizeComp = component.length;
        // Set dependence only
        for(int i = 0; i < sizeConn; i++){  // Check all connector
            componentIN = connector[i].getComponentIN();
            for(int j = 0; j < sizeComp; j++){  // Check all components
                if(!component[j].equals(componentIN)){
                    continue;
                }
                component[j].setDependence(connector[i].getComponentOUT());
                break;
            }
        }
        
        // Set dependece null for external components (outs)
        int countAux = 0;
        for(int i = 0; i < sizeComp; i++){
            if (!component[i].hasDependence()){
                component[i].setDependence(null);
                countAux++;
            }
        }
        // Inicialize components outs
        outComps = new Component[countAux];
    }
    
    /**
     * This method is to order all components, from of outs components
     */
    public void order(){
        int sizeOrder;
        int i;
        
        sizeOrder = outComps.length;
        // list all components out
        checkOut();
        for(i = 0; i < sizeOrder; i++){
            order[count] = outComps[i];
            count++;
            orderOutComponent(outComps[i]);
        }
    }
    
    /**
     * This method is to check whats components out in workplace
     */
    public void checkOut(){
        @SuppressWarnings("unused")
		int sizeConn;
        int sizeComp;
        
        int j = 0;
        sizeComp = component.length;
        for(int i = 0; i < sizeComp; i++){
            if(!component[i].hasDependence()){
                outComps[j] = component[i];
                j++;
            }
        }
    }
    
    /**
     * Reursive method for order components from only one out component
     * @param _compOut is the base component
     */
    public void orderOutComponent(Component _compOut){
        int sizeConn;
        Component aux;
        Component out;
        
        // Find from all connectors in workplace
        sizeConn = connector.length;
        for(int i = 0; i < sizeConn; i++){
            aux = connector[i].getComponentOUT();
            if(_compOut.equals(aux)){ // find next component
                order[count] = connector[i].getComponentIN();
                count++;
                
                // Set next out component
                out = connector[i].getComponentIN();
                // recursive call
                orderOutComponent(out);
            }
        }
        return;
    }
    
    public void setPosComp(){
        
    }
}
