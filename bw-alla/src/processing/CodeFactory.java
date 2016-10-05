/*
 * ApplicationFactor.java
 *
 * Created on September 11, 2003, 2:35 PM
 */

package processing;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.Template;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import java.io.*;
//import java.util.ArrayList;
import util.Config;
import util.FileFactor;

/**
 *
 * @author  alla
 */
public class CodeFactory {
    /** Variable for template object*/
    private Template template;
    /** Variable for context of velocity*/
    private VelocityContext context;
    
    /**
     * Creates a new instance of CodeFactory
     */
    public CodeFactory() {
        try {
            // Init velocity properties
            Velocity.init(Config.getRootPath() + "/processing/codeFactory.properties");
            context = new VelocityContext();
        } catch( Exception e ) {
            System.out.println(e);
        }
    }
    
    /**
     * Method for set template
     * @param _templatePath is the value for template
     */
    public void setTemplate(String _templatePath){
        try{
            try {
                template = Velocity.getTemplate(_templatePath);
            } catch( ResourceNotFoundException rnfe ) {
                System.out.println("Example : error : cannot find template " + _templatePath);
            } catch( ParseErrorException pee ) {
                System.out.println("Example : Syntax error in template " + _templatePath + ":" + pee );
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     * Method for put element in context
     * @param _velocityVar variable in velocity template
     * @param _value value to passed for template
     */
    public void putSimpleValue(String _velocityVar, String _value){
        context.put(_velocityVar, _value);
    }
    
    /**
     * Method for put element in context
     * @param _velocityVar variable in velocity template
     * @param _value value to passed for template
     */
    public void putSimpleValue(String _velocityVar, int _value){
        context.put(_velocityVar, ""+_value);
    }
    
    /**
     * Method for put element in context
     * @param _velocityVar variable in velocity template
     * @param _objectArray objects to passed for template
     */
    public void putArrayObject(String _velocityVar, Object[] _objectArray){
        context.put(_velocityVar, _objectArray);
    }
    
    /**
     * Method for write template merged in console
     */
    public void writeConsole(){
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
            if ( template != null)
                template.merge(context, writer);
            writer.flush();
            writer.close();
        } catch( Exception e ) {
            System.out.println(e);
        }
    }
    
    /**
     * Method for write template merged in file
     */
    public void writeFile(String _fileName){
        try {
            File f = FileFactor.creatFile(Config.getProjectPath() + _fileName);
            FileWriter writer = new FileWriter(f);
            if ( template != null)
                template.merge(context, writer);
            writer.flush();
            writer.close();
        } catch( Exception e ) {
            System.out.println(e);
        }
    }
    
    /**
     * Method for write template merged in file
     */
    public void writeFileComp(String _filePath){
        try {
            File f = FileFactor.creatFile(_filePath);
            FileWriter writer = new FileWriter(f);
            if ( template != null)
                template.merge(context, writer);
            writer.flush();
            writer.close();
        } catch( Exception e ) {
            System.out.println(e);
        }
    }
    
    /**
     * Method for write template merged in console
     * //Debug terminar implementa�ao
     */
    public String toString(){
        String result = "";
        try {
            StringWriter writer = new StringWriter();
            if ( template != null)
                template.merge(context, writer);
            
            result = writer.toString();
        } catch( Exception e ) {
            System.out.println(e);
        }
        return result;
    }
}
