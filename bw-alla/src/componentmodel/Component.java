package componentmodel;

/*
 * Variable.java
 *
 * Created on September 19, 2003, 3:24 PM
 */
import java.util.Hashtable;
import java.util.Vector;
import bwexception.BeanWatcherException;
import br.ufpi.die.jflash.property.Properties;
import br.ufpi.die.jflash.dto.ColectorDTO;
/**
 *
 * @author  alla
 * @version 2.0.0.1
 */
@SuppressWarnings("unused")
public class Component {
    /** Constant for indicate functional component*/
    public static String FUNCTIONAL = "FUNCTI_COMP";
    /** Constant for indicate visual component*/
    public static String VISUAL = "VISUAL_COMP";
    
    /** Variable for block commentary of variable*/
    private String description = "/***/";
    /** Variable for name of variable*/
    private String name;
    /** Variable for port this component*
     * private Hashtable ports;
     * /***/
    private Port portIN;
    /***/
    private Port portOUT;
    /** Variable for dependence this component*/
    private Component dependence = null;
    /** Variable for define component type, FUNCITONAL or APPEARANCE*/
    private String typeAppl;
    /** Variable for define component type*/
    private String type;
    /** Variable for manipulate dependence code for this component*/
    private String dependenceCode;
    /***/
    private String configCode;
    /***/
    private Properties prtComp[];
    /**/
    private ColectorDTO cdto;
    /***/
    private int posX = 0;
    private int posY = 0;
    
    public Component(){
    }
    
    public Component(String _type, String _name, String _typeAppl) {
        name     = _name;
        type     = _type;
        typeAppl = _typeAppl;
    }
    
    /** Creates a new instance of Variable */
    public Component(String _type, String _name, String _typeAppl, Port _portIN, Port _portOUT) {
        name     = _name;
        type     = _type;
        typeAppl = _typeAppl;
        portIN = _portIN;
        portOUT = _portOUT;
    }
    
    /** Creates a new instance of Variable */
    public Component(String _type, String _name, String _typeAppl, Port _port) throws BeanWatcherException{
        name     = _name;
        type     = _type;
        typeAppl = _typeAppl;
        
        if(_port.getType().equals(Port.IN_PORT)){
            portIN = _port;
        } else if(_port.getType().equals(Port.IN_PORT)){
            portOUT = _port;
        } else {
            throw new BeanWatcherException("Erro, Wrong value for port passed.");
        }
    }
    
    /**
     * This method is to set value for  description of component
     * @param _description is new value for description
     */
    public void setDescription(String _description){
        description = "/**" + _description + "*/";
    }
    
    /**
     * This method is to get value for description of component
     * @return the value for description
     */
    public String getDescription(){
        return description;
    }
    
    /**
     * This method is to get value for name variable
     * @return the value for name
     */
    public String getName(){
        return name;
    }
    
    /**
     * This method is to get value for type variable
     * @return the value for type of this component
     */
    public String getType(){
        return type;
    }
    
    /**
     * This method is to get value for type of application
     * @return the value for type of this component
     */
    public String getTypeAppl(){
        return typeAppl;
    }
    
    /**
     * This method set one port for this component
     * @param _port the port setted for this component
     *
     * public void setPort(String _namePort, String _typePort){
     * ports.put(_namePort, new Port(_namePort, _typePort));
     * }
     *
     * /**
     * This method set one port for this component
     * @param _port the port setted for this component
     *
     * public void setPort(Port _port){
     * ports.put(_port.getName(), _port);
     * }
     *
     * /**
     * This method get one port for this component
     * @param _name the name of port to be get
     * @return the port for this name
     */
    public Port getPort(String _portType) throws BeanWatcherException{
        if(_portType.equals(Port.IN_PORT)){
            return portIN;
        } else if(_portType.equals(Port.IN_PORT)){
            return portOUT;
        } else {
            //throw new BeanWatcherException("Erro, Wrong value for type port.");
            return null;
        }
    }
    
    /**
     * This method is to set dependence for this object
     * @param _connector is the dependence for this component
     */
    public void setDependence(Component _component){
        dependence = _component;
    }
    
    /**
     * This method is to get dependence for this object
     * @return the dependence
     */
    public Component getDependence(){
        return dependence;
    }
    
    /**
     *
     */
    public boolean hasDependence(){
        if(this.dependence == null){
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * This method is to set value for dependence code
     * @param _dependenceCode is new value for dependence code
     */
    public void setDependenceCode(String _dependenceCode){
        dependenceCode = _dependenceCode;
    }
    
    /**
     * This method is to get dependence code
     * @return the value for dependence code
     */
    public String getDependenceCode(){
        return dependenceCode;
    }
    
    /**
     * This method is to set value for dependence code
     * @param _dependenceCode is new value for dependence code
     */
    public void setConfigCode(String _configCode){
        configCode = _configCode;
    }
    
    /**
     * This method is to get dependence code
     * @return the value for dependence code
     */
    public String getConfigCode(){
        return configCode;
    }
    
    public Properties[] getPropertiesComponent(){
        return this.prtComp;
    }
    
    public void setPropertiesComponent(Properties _prtComp[]){
        this.prtComp = _prtComp;
    }
    
    public ColectorDTO getColectorDTO(){
        return cdto;
    }
    
    public void setColectorDTO(ColectorDTO _cdto){
        cdto = _cdto;
    }
    
    public int getPosX(){
        return posX;
    }
    
    public int getPosY(){
        return posY;
    }
    
    public void setPosX(int _posX){
        posX = _posX;
    }
    
    public void setPosY(int _posY){
        posY = _posY;
    }
}