/*
 * ReplyRepository.java
 *
 * Created on 2 de Junho de 2003, 18:14
 *
 * �sta classe faz a replica��o de todo o
 * reposit�rio de componentes, para uma
 * linguagem alvo.
 */

package br.ufpi.die.jflash.reply;
import  br.ufpi.die.jflash.dto.*;
import  br.ufpi.die.jflash.port.*;
import  br.ufpi.die.jflash.factor.*;
import  br.ufpi.die.jflash.datastore.*;
import  setings.*;
import  java.util.*;
import  java.io.*;
/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
public class ReplyRepository {
    
    /** Este atributo armazena todo o reposit�rio de componentes.*/
    private Vector<?> repository;
    
    /** Este objeto faz a gera��o do componente.*/
    private ManagerFactor factor;
    
    /** Este objeto � respons�vel por fazer a ger�ncia do arquivo de dados.*/
    private ManagerDataStore maneger_ds;
    
    /***/
    @SuppressWarnings("unused")
	private ManagerSetings manager_st;
    
    private String component_path =File.separator+"repository"+File.separator;
    
    /**
     * Cria uma nova instancia de ReplyRepository.
     * @param _path_store � path para o arquivo que
     * cont�m as informa��es necess�rias a replica��a do 
     * reposit�rio.
     */
    public ReplyRepository(String _path_store) {
        this.repository = new Vector<Object>();
        this.maneger_ds = new ManagerDataStore(_path_store);
        this.manager_st = new ManagerSetings();        
        this.factor = new ManagerFactor();
        
    }
    /**
     * Este m�todo faz a replica��o de todo o reposit�rio
     *
     * @param _language � a linguagem alvo da replica��o.
     * @param _component_path � o path para o diret�rio de gera��o
     * do novo componente.
     */
    public void reply(String _language,String _component_path){
        
        this.component_path = _component_path;
        this.repository = this.maneger_ds.loadRepository();
        ColectorDTO description_component;
        int i=0;
        
        // Este loop duplo faz a replica��o de todo o reposit�rio de componentes JFlash
        while((i < this.repository.size()) && (this.repository.get(i) instanceof ColectorDTO)){
            
            /* Esta atribui��o faz a c�pia do objeto ColectorDTO que est� no vetor repository
             * cada ColectorDTO possui ref�rencias  a ceca de cada componente gerado.
             */
            description_component = (ColectorDTO) this.repository.get(i++);
            description_component.setComponent_path(this.component_path);
            AllComunicationPort allports = new AllComunicationPort();
            // Neste loop s�o copiados todas as portas referentes ao objeto descrito em ClectorDTO
            while((i < this.repository.size()) && (repository.get(i) instanceof ComunicationPort)){
                
                /* Todas as portas referentes ao componente descrito em ColectorDTO s�o armazenadas no
                 * objeto allports
                 */
                allports.addPort((ComunicationPort) this.repository.get(i++));
                
            }
            
            // Aqui � gerado cada componente do reposit�rio replicado.
            this.factor.createComponent(_language,description_component,allports);
        }
    }
    
}
