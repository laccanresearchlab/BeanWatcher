/*
 * ManagerSetings.java
 *
 * Created on 8 de Junho de 2003, 13:44
 *
 * �sta classe � repons�vel por carregar
 * as informa��es relativas ao path base 
 * da ferramenta, as linguagens alvo, quais
 * as templates e tipos primitivos respectivo
 * �s linguagens alvo.
 */

package setings;
import java.util.*;
import util.*;
/**
 *
 * @author  Lincoln Souza Rocha
 */
public class ManagerSetings{
    
    private StringUtil operator = new StringUtil();
    
    /** Este atributo faz a carga das informa��es de configura��es.*/    
    private ResourceBundle resources;    
    
    /** Este atributo armazena o separador das String de configura��o.*/
    private String separator = null;
    
    /** Crria uma nova instancia de  ManagerSetings */
    public ManagerSetings() {
        loadSetings();
    }
    
    /**
      * Este m�todo carrega as informa��es de configura��o.
      */
    private void loadSetings(){           
           
        try{
            resources = ResourceBundle.getBundle("setings.Setings",Locale.getDefault(),this.getClass().getClassLoader());
            this.separator = this.getSeparator();
        }catch(MissingResourceException mre) {
            System.err.println("Setings.properties");
            System.exit(1);
         }
        
     }
    
    /**
      * Este m�todo separa uma String de entrada em n Strings
      * 
      * @param _separator � separador das Strings de configura��o.
      *  Valor default "&" .
      * @param _text � a String carregada do arquivo de configura��o.
      * @return array que s�o as Strings separadas.
      *
      */
    private String[] getStrArray(String _separator,String _text){
        int i,num;
        num = this.operator.getNunSubstring(_separator , _text);
        String array[] = new String[num];               
        // Faz a subdivis�o de _text em n String armazenando em array
        for(i=0;i<num;i++){
            array[i] = this.operator.getFirstWord(_separator, _text);
            _text = this.operator.dropWord(array[i], _text);
            _text = this.operator.dropWord(_separator, _text);
            
        }
        return(array);
    }
    
    /**
      * Este m�thodo faz o carga do path base do JFlash.
      * @return JFLASH_HOME que � o diret�rio base de JFlash.
      */
    public String getPath_Base(){
        
        return(this.resources.getString("JFLASH_HOME"));
    }
    
    /**
      * Este m�thodo faz o carga do separador de Strings do arquivo de setings.
      * @return SEPARATOR que � o separador.
      */    
    private String getSeparator(){
       return(this.resources.getString("SEPARATOR")); 
    }
    
    /**
      * Este m�thodo faz o carga das linguagen alvo do JFlash.
      * @return LANGUAGES que s�o as linguagesn alvo do JFlash.
      */       
    public String[] getLanguages(){        
        String languages = this.resources.getString("LANGUAGES");                        
        return(this.getStrArray(this.separator,languages)); 
    }

    /**
      * Este m�thodo faz o carga das exten��es para os componentes.
      * @return EXTENSIONS que s�o as exten��es das linguagens alvo.
      */        
    public String[] getExtensions(){                       
        String extensions = this.resources.getString("EXTENSIONS");        
        return(this.getStrArray(this.separator, extensions)); 
    }
    
    /**
      * Este m�thodo faz a carga das templates de gera��o.
      * @return TEMPLATES que s�o as templates de gera��o.
      */        
    public String[] getTemplates(){            
        String templates =this.resources.getString("TEMPLATES");
        return(this.getStrArray(this.separator,templates)); 
      
    }

    /**
      * Este m�thodo faz a carga do tipo primitivo Alfanum�rico
      * das respectivas linguagens alvo.
      * @return STRING que s�o os tipos alfanumericos primitivos.
      */            
    public String[] getTypesString(){
        String str = this.resources.getString("STRING");
        return(this.getStrArray(this.separator,str));
    }
    
    /**
      * Este m�thodo faz a carga do tipo primitivo Inteiro
      * das respectivas lingugens alvo. 
      * @return INTEGER que os tipos inteiros primitivos.
      */            
    public String[] getTypesInteger(){
        String integer = this.resources.getString("INTEGER");
        return(this.getStrArray(this.separator,integer));
    }
    
    /**
      * Este m�thodo faz a carga do tipo primitivo Boleano
      * das respectivas lingugens alvo. 
      * @return INTEGER que os tipos boleanos primitivos.
      */            
    public String[] getTypesBoolean(){
        String bool = this.resources.getString("BOOLEAN");
        return(this.getStrArray(this.separator, bool));
    }
    
    /**
      * Este m�thodo faz a carga do tipo primitivo Real
      * das respectivas lingugens alvo. 
      * @return INTEGER que os tipos reais primitivos.
      */                        
    public String[] getTypesReal(){
        String real = this.resources.getString("REAL");
        return(this.getStrArray(this.separator, real));
    }

    /**
      * Este m�thodo faz a carga do tipo primitivo Flutuante
      * das respectivas lingugens alvo. 
      * @return INTEGER que os tipos flutuantes primitivos.
      */             
    public String[] getTypesFloat(){
        String flt = this.resources.getString("FLOAT");
        return(this.getStrArray(this.separator, flt));
    }
    
    /**
      * Este m�thodo faz a carga da sintax  unidimencional
      * das respectivas lingugens alvo. 
      * @return 1D.
      */                 
    public String[] get1D(){
        String d1 = this.resources.getString("1D");
        return(this.getStrArray(this.separator, d1));
    }
    
    /**
      * Este m�thodo faz a carga da sintax  bidimencional
      * das respectivas lingugens alvo. 
      * @return 2D.
      */                 
    public String[] get2D(){
        String d2 = this.resources.getString("2D");
        return(this.getStrArray(this.separator, d2));
        
        
    }
}
