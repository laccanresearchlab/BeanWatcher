/*
 * ComponentFactory.java
 *
 * Created on October 7, 2003, 4:58 PM
 */

package processing;

import util.*;

/**
 *
 * @author  alla
 */
public class ComponentFactory {
    String componentPath;
    
    /** Creates a new instance of ComponentFactory */
    public ComponentFactory(String _compType) {
        componentPath = Config.getRepositoryPath() + _compType;
    }
    
    public String loadComponent(){
        return FileFactor.readFile(componentPath);
    }
    
    public void saveComponent(String _newCode){
        FileFactor.writeFile(componentPath, _newCode);
    }
}
