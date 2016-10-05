/*
 * ColectorDTO.java
 *
 * Created on 30 de Maio de 2003, 17:57
 * 
 * Esta classe � um Objeto de Transfer�ncia de Dados(DTO),
 * ela armazena as informa��es necess�rias a cria��o de
 * um novo componente, tais informa��es s�o fornecidas pe-
 * lo usu�rio do JFlash.
 *
 */

package br.ufpi.die.jflash.dto;
import  br.ufpi.die.jflash.port.*;
import  java.util.*;
/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
@SuppressWarnings("unused")
public class ColectorDTO {
    
    /** Este atributo armazena o nome do autor do componente a ser criado. */
    private String author_name;
    
    /** Este atributo armazena o o local ondeo componente ser� gerado. */
    private String component_path;    

    /** Este atributo armazena o nome do novo componente a ser criado. */
    private String component_name;    
    
    /** Este atributo armazena o numero de portas que o componente ter�. */
    private int num_ports;
    
    private String component_image;
    
    private int component_image_width;
    
    private int component_image_height;
    
    private String component_typeAppl;
    
    private int posX;
    private int posY;
    
    /** 
      * Cria uma nova instancia do ColectorDTO 
      * e inicialisa(prof pasquale: não) todos os atributos da classe.
      */
    public ColectorDTO() {
        this.component_name = this.component_path = this.author_name = null;        
        this.num_ports = 0;
    }
    
    /** Este m�todo � p�blico para permitir o acesso ao nome do criador
      * do componente.
      * @return author_name que � o nome do autor do componente
      */
    public String getAuthor_name(){
        return(this.author_name);
    }
    
    /** Este m�todo � p�blico para permitir o a acesso ao local onde deve
      * ser gerado o componente.
      * @return component_path que o local onde deve ser gerado o componente
      */
    public String getComponent_path(){
        return(this.component_path);
    }
    
    /** Este m�todo � p�blico para permitir o acesso ao nome do novo 
      * componente.
      * @return component_name que � o nome do novo componente.
      */
    public String getComponent_name(){
        return(this.component_name);
    }    
    
    /** Este m�todo � p�blico para permitir o acesso ao numeros
      * de portas que o novo componente ter�.      
      * @return numports que � o numero de portas.
      */
    public int getNumPorts(){
        return(this.num_ports); 
    }
    
    /** Este m�todo � p�blico para permitir o armazenamento do nome
      * do criador do componente.
      * @param _author_name � o nome do criador do componente
      */
    public void setAuthor_name(String _author_name){
        this.author_name = _author_name;
    }

    /** Este m�todo � p�blico para permitir o armazenamento do local
      * onde ser� gerado o novo componente.
      * @param _component_path � o local onde ser� gerado o componente
      */    
    public void setComponent_path(String _component_path){
        this.component_path = _component_path;
    }
    
    /** Este m�todo � p�blico para permitir o armazenamento do nome do novo 
      * componente.
      * @param _component_name � o nome do novo componente.
      */ 
    public void setComponent_name(String _component_name){
        this.component_name = _component_name;
    }

    /** Este m�todo � p�blico para permitir o armazenamento 
      * do numero de portas que o componente ter�.
      * @param _numports � a quantidade de portas do novo 
      * componente.
      */     
    public void setNumPorts(int _num_ports){
        this.num_ports = _num_ports;        
    }
    
    public void setComponent_image(String _component_image){
        this.component_image = _component_image;
    }
    
    public String getComponent_image(){
        return this.component_image;
    }
    
    public void setComponent_image_width(int _component_image_width){
        this.component_image_width = _component_image_width;
    }
    
    public int getComponent_image_width(){
        return this.component_image_width;
    }
    
    public void setComponent_image_height(int _component_image_height){
        this.component_image_height = _component_image_height;
    }
    
    public int getComponent_image_height(){
        return this.component_image_height;
    }
    
    public void setComponent_typeAppl(String _component_typeAppl){
        this.component_typeAppl = _component_typeAppl;
    }
    
    public String getComponent_typeAppl(){
        return this.component_typeAppl;
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
