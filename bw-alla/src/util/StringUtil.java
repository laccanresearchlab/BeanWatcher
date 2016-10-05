/*
 * StringUtil.java
 *
 * Created on 11 de Junho de 2003, 19:11
 *
 * Esta classe funciona como uma modelador
 * de operações sobre um objeto da classe String.
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
      * Este mátodo separa uma String de entrada em n subStrings
      * 
      * @param _separator é separador das Strings.
      *  Valor default " " .
      * @param _text é a String ea string a ser subdividida.
      * @return array que são as Strings separadas.
      *
      */
    public String[] getStrArray(String _separator,String _text){
        int i,num;
        num = this.getNunSubstring(_separator,_text);
        String array[] =  new String[num];               
        // Faz a subdivisão de _text em n String armazenando em array
        for(i=0;i<num;i++){
            array[i] =  this.getFirstWord(_separator, _text);
            _text =  this.dropWord(array[i], _text);
            _text =  this.dropWord(_separator, _text);
            
        }
        return(array);
    }
    
    /**
      * Este método retorna quantas Strings existem subdivididas
      * por um separador em uma String base.
      *
      * @param _separator é o separador de Strings.
      * @param _test é a string base para avaliação.
      * @return count que é a quantidade de substrings.
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
      * Este método remove a primeira ocorrência _word em _text.
      * 
      * @param _word é a palavra que se deseja remover.
      * @param _text é a String base para a remoção.
      * @return _text é a String base sem a primeira palavra _word. 
      */
    public String dropWord(String _word,String _text){
        int posi = _text.indexOf(_word);      
        int length = _word.length();        
     
        if(posi != -1)
                _text =  _text.substring(0,posi) +  _text.substring(posi + length);                         
        return(_text);        
    }
    
    /**
      * Este método remove todas as ocorrencias de _word em _text
      * @param _word é a palavra que se deseja remover.
      * @param _text é a String base para a remoção.
      * @return _text é a String base sem a palavra _word. 
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
      * Este método retorna a primeira String delimitada pelo separador.
      *
      * @param _separator é o separador de String.
      * @param _text é a String base de avaliação.
      * @return String que é a primenira String de _text.
      */
    public String getFirstWord(String _separator,String _text){
        return(_text.substring(0,_text.indexOf(_separator)));
    }    
    
    
}
