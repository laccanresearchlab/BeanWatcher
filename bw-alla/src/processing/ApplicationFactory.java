/*
 * ComposerApplication.java
 *
 * Created on September 19, 2003, 4:39 PM
 */

package processing;

import componentmodel.Component;
import util.Config;
import util.FileFactor;

/**
 *
 * @author  alla
 */
public class ApplicationFactory {
    private Component applComponents[];
    
    private Component allComponents[];
    
    private String path;
    
    private String code = "";
    
    /** Creates a new instance of ComposerApplication */
    public ApplicationFactory(Component[] _components) {
        allComponents = _components;
        this.createApplication();
    }
    
    /**
     *
     *
     */
    private void setPath(){
        path = Config.getProjectPath();
        FileFactor.makeDir(Config.getProjectPath());
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
     */
    private void setDependenceCode(){
        int size;
        String depCode;
        
        size = this.applComponents.length;
        
        for(int i = 0; i < size; i++){
            depCode = this.writeDependenceCode(this.applComponents[i].getDependence());
            this.applComponents[i].setDependenceCode(depCode);
        }
    }
    
    /**
     *
     */
    private String writeDependenceCode(Component _component){
        CodeFactory composer;
        Component   compDep;
        
        if(_component.getType().equals("Filter") || _component.getType().equals("Comunicator")){
            return "";
        }
        compDep = _component.getDependence();
        
        composer = new CodeFactory();
        composer.setTemplate(Config.getTemplatePath() + "dependenceCodeAppl.vm");
        composer.putSimpleValue("name", _component.getName());

        if (compDep.getType().equals("Filter")){
            composer.putSimpleValue("dependence", "filter");
        } else {
            composer.putSimpleValue("dependence", compDep.getName());
        }
        
        code = this.writeDependenceCode(compDep) + composer.toString();
        return  code;
    }
    
    /**
     *
     */
    private void createComposer(){
        try {
            int numberAppls;
            CodeFactory composer = new CodeFactory();
            
            numberAppls = applComponents.length;
            
            composer.setTemplate(Config.getTemplatePath() + "Composer.vm");
            composer.putSimpleValue("numberAppls", numberAppls);
            composer.putArrayObject("listAllComponent", allComponents);
            composer.putArrayObject("listApplComponent", applComponents);
            
            composer.writeFileComp(path + "/Composer.java");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     *
     */
    private void createFilter(){
        try{
            CodeFactory composer = new CodeFactory();
            
            composer.setTemplate(Config.getTemplatePath() + "Filter.vm");
            composer.putArrayObject("listApplComponent", applComponents);
            composer.writeFileComp(path + "/Filter.java");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    private void copyFiles(){
        int size;
        String compType;
        
        
        size = allComponents.length;
        for(int i = 0; i < size; i++){
            compType = allComponents[i].getType();
            FileFactor.copyDir(Config.getFatherPath() + compType+"/",
            path + compType+"/");
            //FileFactor.copyFile(Config.getFatherPath() + compType,
            //                    Config.getProjectPath() + compType + Config.getExtension());
        }
        FileFactor.copyDir(Config.getFatherPath() + "util/",
        path + "util/");
        FileFactor.copyFile(Config.getFatherPath() + "Comunicator",
        path + "Comunicator" + Config.getExtension());
        FileFactor.copyFile(Config.getFatherPath() + "ObjectCanvas",
        path + "ObjectCanvas" + Config.getExtension());
        FileFactor.copyFile(Config.getFatherPath() + "BeanWatcher",
        path + "BeanWatcher" + Config.getExtension());
    }
    
    /**
     *
     */
    private void createApplication(){
        this.setPath();
        this.setApplComponent();
        this.setDependenceCode();
        this.createComposer();
        this.createFilter();
        this.copyFiles();
    }
}
