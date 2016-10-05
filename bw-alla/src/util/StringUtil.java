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
      * Este m�todo separa uma String de entrada em n subStrings
      * 
      * @param _separator � separador das Strings.
      *  Valor default " " .
      * @param _text � a String ea string a ser subdividida.
      * @return array que s�o as Strings separadas.
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
      * Este m�todo retorna quantas Strings existem subdivididas
      * por um separador em uma String base.
      *
      * @param _separator � o separador de Strings.
      * @param _test � a string base para avalia��o.
      * @return count que � a quantidade de substrings.
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
      * Este m�todo remove a primeira ocorr�ncia _word em _text.
      * 
      * @param _word � a palavra que se deseja remover.
      * @param _text � a String base para a remo��o.
      * @return _text � a String base sem a primeira palavra _word. 
      */
    public String dropWord(String _word,String _text){
        int posi = _text.indexOf(_word);      
        int length = _word.length();        
     
        if(posi != -1)
                _text =  _text.substring(0,posi) +  _text.substring(posi + length);                         
        return(_text);        
    }
    
    /**
      * Este m�todo remove todas as ocorrencias de _word em _text
      * @param _word � a palavra que se deseja remover.
      * @param _text � a String base para a remo��o.
      * @return _text � a String base sem a palavra _word. 
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
      * Este m�todo retorna a primeira String delimitada pelo separador.
      *
      * @param _separator � o separador de String.
      * @param _text � a String base de avalia��o.
      * @return String que � a primenira String de _text.
      */
    public String getFirstWord(String _separator,String _text){
        return(_text.substring(0,_text.indexOf(_separator)));
    }    
    
    
}
