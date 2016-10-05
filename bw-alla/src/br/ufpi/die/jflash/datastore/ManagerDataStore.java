/*
 * ManagerDataStore.java
 *
 * Created on 8 de Junho de 2003, 01:50
 *
 * �sta classe faz o arquivamento dos dados coletados
 * em um arquivo XML, para possibilitar a sua poss�vel
 * replica��o.
 */

package br.ufpi.die.jflash.datastore;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Vector;
/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
public class ManagerDataStore {    
    
    /** Este atributo guarda o path para o arquivo data_store.xml. */
    private static String parh_data_store;      
    
    
    /**
      * Cria uma nova instancia de ManagerDataStore.
      * @param _path � o path para o arquivo XML de dados.
      */
    public ManagerDataStore(String _path){                
            ManagerDataStore.parh_data_store = _path;       
            
    }
    
    /**
     * Este m�todo carrega todo arquivo  XLM que �
     * a representa��o abstrata de todos os componentes.
     *
     * @return repository que � a representa��o de todos os
     *  componentes do reposit�rio.
     */
    @SuppressWarnings("unchecked")
	public Vector<Object> loadRepository(){
        Vector<Object> repository = new Vector<Object>();
        try{            
            FileInputStream xml  = new FileInputStream(ManagerDataStore.parh_data_store);
            BufferedInputStream buff =  new BufferedInputStream(xml);
            XMLDecoder d = new XMLDecoder(buff);
            repository = (Vector<Object>) d.readObject();
            d.close();
        }catch(FileNotFoundException fnfe){
        }
        
        return(repository);
    }
    
    /**
     * Este m�todo � encarregado de armazenar os dados referentes ao
     * componente no arquivo de data_store.xml.
     *
     * @param _repository s�o os componentes que ser�o inseridos no
     * arquivo de informa��es sobre os componentes.
     */
    public void storeComponent(Vector<?> _repository){
        try{          
            FileOutputStream xml = new FileOutputStream(ManagerDataStore.parh_data_store);
            BufferedOutputStream buff = new BufferedOutputStream(xml);
            XMLEncoder e = new XMLEncoder(buff);
            @SuppressWarnings("unused")
			int i;
            e.writeObject(_repository);
            e.close();
        }catch(FileNotFoundException fnfe){
        }
    }
}
