/*
 * StringUtil.java
 *
 * Created on 11 de Junho de 2003, 19:11
 *
 * Esta classe funciona como uma modelador
 * de opera��es sobre um objeto da classe String.
 */

package util;

/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
public final class StringUtil {
    
    /** Creates a new instance of StringUtil */
    public StringUtil() {
    }

    /**
      * Separa uma String de entrada em n subStrings
      * 
      * @param _separator separador das Strings.
      *  Valor default " " .
      * @param _text a String ea string a ser subdividida.
      * @return array Strings separadas.
      *
      */
    public String[] getStrArray(String _separator,String _text){
        int i,num;
        num = this.getNunSubstring(_separator,_text);
        String array[] =  new String[num];               
        // Faz a subdivis�o de _text em n String armazenando em array
        for(i=0;i<num;i++){
            array[i] =  this.getFirstWord(_separator, _text);
            _text =  this.dropWord(array[i], _text);
            _text =  this.dropWord(_separator, _text);
            
        }
        return(array);
    }
    
    /**
      * Strings subdivididas
      * por um separador em uma String base.
      *
      * @param _separatorseparador de Strings.
      * @param _teststring base para avaliação.
      * @return count quantidade de substrings.
      */
    public int getNunSubstring(String _separator,String _text){        
        int count=0;       
        
        while(_text.indexOf(_separator)!=-1){
            count++;     
            _text = this.dropWord(_separator, _text);
        }
        return(count);
    }
    
    /**
      * 
      * @param _word palavra que se deseja remover.
      * @param _text String base para a remoção.
      * @return _text String base sem a primeira palavra _word. 
      */
    public String dropWord(String _word,String _text){
        int posi = _text.indexOf(_word);      
        int length = _word.length();        
     
        if(posi != -1)
                _text =  _text.substring(0,posi) +  _text.substring(posi + length);                         
        return(_text);        
    }
    
    /**
      * Remove todas as ocorrencias de _word em _text
      * @param _word palavra que se deseja remover.
      * @param _text String base para a remoção.
      * @return _text String base sem a palavra _word. 
      */
    public String dropAllWord(String _word, String _text){
        int posi = _text.indexOf(_word);      
        int length = _word.length();        
     
        while(posi != -1){
                _text =  _text.substring(0,posi) +  _text.substring(posi + length);
                posi = _text.indexOf(_word);      
         }       
        return(_text);
    }
    
    /**
      * Retorna a primeira String delimitada pelo separador.
      *
      * @param _separator separador de String.
      * @param _text String base de avaliação.
      * @return String primenira String de _text.
      */
    public String getFirstWord(String _separator,String _text){
        return(_text.substring(0,_text.indexOf(_separator)));
    }    
    
    
}
