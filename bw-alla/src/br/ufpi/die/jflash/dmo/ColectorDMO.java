/*
 * ColectorDMO.java
 *
 * Created on 2 de Junho de 2003, 16:22
 *
 * �sta classe faz a modelagem dos dados coletados
 * para que possa ser criado o novo componente.
 */

package br.ufpi.die.jflash.dmo;
import  br.ufpi.die.jflash.dto.*;
import  br.ufpi.die.jflash.port.*;
import  br.ufpi.die.jflash.factor.*;
import  br.ufpi.die.jflash.datastore.*;
import java.util.*;
/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
public class ColectorDMO{
    
    /** Este objeto armazena a descri��o do novo componente. */
    private ColectorDTO description_component;    
    
    /** Este atributo armazena todo reposit�rio de componentes. */
    private Vector<Object> repository;
    
    /** Este atributo armazena todas as portas para efeito de gera��o deo componente.*/
    private AllComunicationPort allports;
    
    /** Este objeto faz a gera��o de fato do componente.*/
    private ManagerFactor factor;    
    
    /** Este objeto � respons�vel por fazer a ger�ncia do arquivo de dados.*/
    private ManagerDataStore manager_ds;
    
    /** Cria uma nova inst�ncia de ColectorDMO */
    public ColectorDMO() {
        this.description_component = new ColectorDTO();
        this.allports = new AllComunicationPort();        
        this.repository = new Vector<Object>();                
        this.factor = new ManagerFactor();
    }
    
    /** 
      * Este m�todo permite adicionar portas no componente.
      *
      * @param _port_format � o formato da porta.
      * @param _port_type � o tipo da porta.
      * @param _port_name � o nome da porta.
      * @param _port_dimension � a dimen��o da porta.
      */
    public void addPort(int _port_format,String _port_type,String _port_name,String _port_dimension){
        ComunicationPort cp = new ComunicationPort();
        cp.setPort_name(_port_name);
        cp.setPort_type(_port_type);
        cp.setPort_format(_port_format);
        cp.setPort_dimension(_port_dimension);
        this.allports.addPort(cp);        
    }
    
    /** 
      * Este m�todo solicita a gera��o do  novo componente.
      *
      * @param _language � a linguagem alvo para a gera��o do componente. "j2me" ou "c" ou ..
      * @param _author_name � o nome do criador do componente.
      * @param _component_name � o nome do novo componente.
      * @param _component_path � o path donde deve ser gerado o novo componente.
      * @param _data_store � o path para o arquivo de arquivamento de dados.
      */
    public void newComponent(String _language,String _author_name,String _component_name,String _component_path,String _data_store){
        
        this.manager_ds = new ManagerDataStore(_data_store);
        
        this.description_component.setAuthor_name(_author_name);        
        this.description_component.setComponent_name(_component_name);
        this.description_component.setComponent_path(_component_path);
        this.description_component.setNumPorts(this.allports.numPorts());       
        
        
        this.repository = this.manager_ds.loadRepository();
        this.repository.addElement(this.description_component);
        // Adiciona o novo componente ao vetor do reposit�rio
        for(int i=0; i < this.allports.numPorts(); i++){
            this.repository.addElement(this.allports.getPort(i));
        }        
        
        // Faz o arquivamento dos dados sobre o componente.
        this.manager_ds.storeComponent(this.repository);

        // Faz a gera��o do novo componente.
        this.factor.createComponent(_language,this.description_component,this.allports);                
        
        
    }    
    
}
