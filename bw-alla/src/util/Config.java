/*
 * Config.java
 *
 * Created on September 26, 2002, 7:50 PM
 */

package util;

/**
 *
 * @author  Alla
 * @version 0.0.0.0.1
 */

public class Config extends Object {
    static private String rootPath        = "/Users/rlopes/Documents/workspace/bw-alla/src/";
    static private String repository      = "/repository/j2me/";
    static private String extension       = ".java";
    static private String projectPath     = "/Users/rlopes/Documents/Alla-code/applj2me/";
    static private String configFile      = "data_store.xml";
    static private String child = "/child/";
    static private String father = "/father/";
    
    /**
     * @return
     */
    static public String getRootPath(){
        return rootPath;
    }
    
    /**
     * @return
     */
    static public String getRepositoryPath(){
        return getRootPath() + repository;
    }
    
    /**
     * @return
     */
    static public String getTemplatePath(){
        return getRepositoryPath() + "/templates/";
    }
    
    /**
     * @return
     */
    static public String getExtension(){
        return extension;
    }
    
    /**
     * @return
     */
    static public String getProjectPath(){
        return projectPath+"appl/";
    }
    
    /**
     * @return
     */
    static public String getConfigFile(){
        return configFile;
    }
    
    /**
     *
     */
    static public String getChildConfigFile(){
        return getRepositoryPath() + child + configFile;
    }
    
    /**
     *
     */
    static public String getFatherConfigFile(){
        return getRepositoryPath() + father + configFile;
    }
    
    /**
     *
     */
    static public String getFatherPath(){
        return getRepositoryPath() + father;
    }
    
    /**
     *
     */
    static public String getChildPath(){
        return getRepositoryPath() + child;
    }
}