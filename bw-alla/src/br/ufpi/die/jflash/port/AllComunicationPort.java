/*
 * AllComunicationPort.java
 *
 * Created on 30 de Maio de 2003, 19:07
 *
 * �sta classe faz o armazenamento das informa��es
 * sobre as portas para um novo componente.  
 * 
 */

package br.ufpi.die.jflash.port;
import  java.util.*;
/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
public class AllComunicationPort {
    
    /** Este atributo � o vetor de portas que armazena todas as portas que o componente possuir�. */
    private Vector<ComunicationPort> ports;   
    
    /** Este atributo especifica a quantidade de portas que o componente possuir�. */ 
    private int numports;
    
    /** Cria uma nova instancia de AllComunicationPort 
      * e inicialisa o vetor de portas.
      */
    public AllComunicationPort() {        
        this.ports = new Vector<ComunicationPort>();
        
        
    }
    
    /**
      * Este m�todo adiciona uma porta ao vetor de portas.
      * @param _port � uma porta.
      */
    public void addPort(ComunicationPort _port){
        ports.addElement(_port);        
        this.numports = this.ports.size();
    }
    
    /**
      * Este m�todo resgata uma porta espec�fica
      * do vetor de portas.
      * @param _index � o indice referente a porta desejada.
      * @return ports.get(_index) que � a porta especificada.
      */
    public ComunicationPort getPort(int _index){
        if(ports.size() > _index)       
           return( (ComunicationPort) this.ports.get(_index));           
        else
           return(null); 
    }
    
    /** Este m�todo informa qual a quantidade de portas existents
      * no vetor de portas.
      * @return numports � quantidade de portas existentes no
      * vetor de portas.
      */
    public int numPorts(){        
        return(this.numports);
        
    }
    
}
