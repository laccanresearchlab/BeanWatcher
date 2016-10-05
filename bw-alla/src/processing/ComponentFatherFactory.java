/*
 * ComposerApplication.java
 *
 * Created on September 19, 2003, 4:39 PM
 */

package processing;

import java.io.File;
import java.util.Vector;
import componentmodel.Component;
import br.ufpi.die.jflash.property.Properties;
import br.ufpi.die.jflash.datastore.ManagerDataStore;
import br.ufpi.die.jflash.dto.ColectorDTO;
import br.ufpi.die.jflash.port.ComunicationPort;
import br.ufpi.die.jflash.port.AllComunicationPort;
import util.Config;
import util.FileFactor;

/**
 *
 * @author  alla
 */
@SuppressWarnings("unused")
public class ComponentFatherFactory {
    private String componentName;
    
    private String path;
    
    private Component allComponents[];
    private Component applComponents[];
    
    private String code = "";
    
    /** Creates a new instance of ComposerApplication */
    public ComponentFatherFactory(String _componentName, Component[] _components) {
        allComponents = _components;
        componentName = _componentName;
        this.createApplication();
    }

    /** */
    private void setApplComponent(){
        int size;
        Component   componentArray[];
        
        size           = this.allComponents.length;
        componentArray = new Component[size];
        
        int j = 0;
        for(int i = 0; i < size; i++){
            if(this.allComponents[i].getTypeAppl().equals(Component.VISUAL)){
                componentArray[j] = this.allComponents[i];
                j++;
            }
        }
        applComponents = new Component[j];
        System.arraycopy(componentArray, 0, this.applComponents, 0, j);
    }
    
    /**
     *
     *
     */
    private void setPath(){
        path = Config.getFatherPath() + componentName;
        FileFactor.makeDir(path);
    }
    
    /**
     *
     */
    private void setConfigCode(){
        Component comp;
        CodeFactory composer;
        Properties prtComp[];
        int size;
        String depCode;
        
        size = this.allComponents.length;
        
        for(int i = 0; i < size; i++){
            comp = this.allComponents[i];
            
            composer = new CodeFactory();
            composer.setTemplate(Config.getTemplatePath() + "configCode.vm");
            composer.putSimpleValue("name", comp.getName());
            
            prtComp = comp.getPropertiesComponent();
            String comma;
            String config = "";
            for(int j = 0; j < prtComp.length; j++) {
                if( (j + 1) == prtComp.length){
                    comma = "";
                }else {
                    comma = ", ";
                }
                config = config+prtComp[j].getValue()+comma;
            }
            composer.putSimpleValue("config", config);
            comp.setConfigCode(composer.toString());
            this.allComponents[i] = comp;
        }
        
    }
    
    /**
     *
     */
    private void setDependenceCode(){
        Component comp;
        CodeFactory composer;
        int size;
        String depCode;
        
        size = this.allComponents.length;
        
        for(int i = 0; i < size; i++){
            comp = this.allComponents[i];
            
            composer = new CodeFactory();
            composer.setTemplate(Config.getTemplatePath() + "dependenceCode.vm");
            composer.putSimpleValue("name", comp.getName());
            
            if(!comp.hasDependence()){
                composer.putSimpleValue("dependence", "(Integer)inPort.get(\""+comp.getType()+"\")");
            } else {
                composer.putSimpleValue("dependence", comp.getDependence().getName()+".getOutPort()");
            }
            comp.setDependenceCode(composer.toString());
            this.allComponents[i] = comp;
        }
        
    }
    
    /**
     *
     */
    private void createComposer(){
        CodeFactory composer = new CodeFactory();
        composer.setTemplate(Config.getTemplatePath() + "ComposerComp.vm");
        composer.putSimpleValue("applName", componentName);
        composer.putArrayObject("listAllComponent", allComponents);
        composer.putArrayObject("listApplComponent", applComponents);
        
        composer.writeFileComp(this.path + "/Composer");
    }
    
    /**
     *
     */
    private void createComponent(){
        CodeFactory component = new CodeFactory();
        
        component.setTemplate(Config.getTemplatePath() + "component.vm");
        component.putSimpleValue("name", componentName);
        component.writeFileComp(this.path + "/"+componentName);
    }
    
    /**/
    private void copyFiles(){
        CodeFactory composer;
        int size;
        String compType;
        
        size = allComponents.length;
        for(int i = 0; i < size; i++){
            compType = allComponents[i].getType();
            composer = new CodeFactory();
            composer.setTemplate(Config.getChildPath() + compType);
            composer.putSimpleValue("dir", componentName);
            composer.writeFileComp(this.path + "/"+compType);
        }
        composer = new CodeFactory();
        composer.setTemplate(Config.getChildPath() + "ObjectCanvas");
        composer.putSimpleValue("dir", componentName);
        composer.writeFileComp(this.path + "/ObjectCanvas");
    }
    

	private void writePrtFile(){
        Vector<Object> repository;
        ManagerDataStore mds;
        Component comp = this.allComponents[0];
        Component compDep = this.allComponents[0];
        ColectorDTO description_component;
        ComunicationPort port;
        AllComunicationPort allPort = new AllComunicationPort();
        int size;
        size = this.allComponents.length;
        mds = new ManagerDataStore(Config.getFatherConfigFile());
        for(int i = 0; i < size; i++){
            comp = this.allComponents[i];
            if(!comp.hasDependence()){
                compDep = comp;
                //for(int i=0; i < this.allports.numPorts(); i++){
                port = new ComunicationPort();
                port.setPort_dimension("1");
                port.setPort_format(0);
                port.setPort_name("inPort");
                //port.setPort_type(comp.getType());
                port.setPort_type(componentName);
                allPort.addPort(port);
            }
        }
        description_component = compDep.getColectorDTO();
        description_component.setComponent_name(componentName);
        description_component.setComponent_path(path);
        description_component.setNumPorts(allPort.numPorts());
        repository = mds.loadRepository();
        repository.addElement(description_component);
        for(int i=0; i < allPort.numPorts(); i++){
            repository.addElement(allPort.getPort(i));
        }
        mds.storeComponent(repository);
    }
    
    /**
     *
     */
    private void createApplication(){
        this.setPath();
        this.setApplComponent();
        this.setDependenceCode();
        this.setConfigCode();
        this.createComposer();
        this.createComponent();
        this.copyFiles();
        this.writePrtFile();
    }
}
