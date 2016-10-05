/*
 * ComunicationPort.java
 *
 * Created on 30 de Maio de 2003, 18:23
 *
 * Esta class armazena as informações sobre as portas
 * de comunicação do novo componente a ser criado.
 */

package br.ufpi.die.jflash.port;

/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
public class ComunicationPort {
    
    /** Este atributo armazena o nome da porta.*/
    private String port_name;
    
    /** Este atributo armazena o formato da porta. 0-InPut 1-OutPut 2- InOutPut */
    private int port_format;
    
    /** Este atributo armazena o tipo da porta. STRING,INTEGER,REAL,BOOLEAN,FLOAT.*/
    private String port_type;
    
    /** Este atributo armazena qual a dimensãoo da porta. Se é vetor ou matriz*/
    private String port_dimension;        
    
    /** 
      * Cria uma nova instancia de ComunicationPort 
      * e inicializa todos os atributos.
      */
    public ComunicationPort() {
        this.port_format = -1;
        this.port_dimension = this.port_name = this.port_type = null;
    }
    
    /** Este método é publico para permitir o
      * acesso ao nome da porta 
      * @return port_name que é o nome da porta
      */
    public String getPort_name(){
        return(this.port_name);
    }

    /** Este método é publico para permitir o
      * acesso ao formato da porta, se for 0-InPut,
      * 1-OutPut ou 2-InOutPut 
      * @return port_format formato da porta
      */    
    public int getPort_format(){
        return(this.port_format);
    }

    /** 
      * acesso ao tipo da porta, int,
      * STRING,INTEGER,REAL,BOOLEAN,FLOAT
      * @return port_type tipo da porta
      */        
    public String getPort_type(){
        return(this.port_type);
    }
    
    /** 
      * acesso a dimensão da porta, uni ou 
      * bidimencional      
      * @return port_dimension dimensão da porta
      */        
    public String getPort_dimension(){
        return(this.port_dimension);
    }
    
    /** 
      * o armazenamento do nome da porta
      * @param _port_name nome da porta
      */
    public void setPort_name(String _port_name){
        this.port_name = _port_name;
    }
    
    /** 
      * o armazenamento do formato da porta. 
      * Valores possíveis: 0-InPut, 1-OutPut ou 2-InOutPut 
      * @param _port_format formato da porta
      */    
    public void setPort_format(int _port_format){
        this.port_format = _port_format;
    }
    
    /** 
      * o armazenamento do tipo da porta. 
      * Valores possíveis: STRING,INTEGER,REAL,BOOLEAN,FLOAT.
      * @param _port_type tipo da porta
      */        
    public void setPort_type(String _port_type){
        this.port_type = _port_type;
    }

    /** 
      * o armazenamento da dimensão da porta. 
      * Valores possíveis: vetor, matriz.
      * @param _port_dimension dimensão da porta
      */            
    public void setPort_dimension(String _port_dimension){
        this.port_dimension = _port_dimension;
    }
    
}
