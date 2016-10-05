/*
 * ManagerFactor.java
 *
 * Created on 9 de Junho de 2003, 21:45
 *
 * Esta classe eh responsavel por fazer a 
 * tomada de decisão a cerca da linguagem 
 * alvo para a geração de novos componentes.
 */

package br.ufpi.die.jflash.factor;
import  br.ufpi.die.jflash.dto.*;
import  br.ufpi.die.jflash.port.*;
import  setings.*;
import  java.io.*;
import  java.util.*;
import  java.text.*;
/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
public class ManagerFactor {    
    /* Esta classe faz a implementação de um analisador e gerador de código*/
        
    /** Determina o formato para a data de geração do componente*/
    private final String DATE_FORMAT = "dd MMMMMMMM yyyy, hh:mm";

    /** Esta tabela guarda as tags estruturais da Template Java de geração.*/
    private final String tag_table[] = { "<atribute_in>"               ,"<atribute_out>"               ,"<atribute_in_out>"                ,
                                         "</atribute_in>"              ,"</atribute_out>"              ,"</atribute_in_out>"               ,
                                         "<methods_in_pb>"             ,"<methods_out_pb>"              ,"<methods_in_out_pb>"             ,
                                         "</methods_in_pb>"            ,"</methods_out_pb>"             ,"</methods_in_out_pb>"             ,                                         
                                         "<methods_in_pv>"             ,"<methods_out_pv>"              ,"<methods_in_out_pv>"             ,
                                         "</methods_in_pv>"            ,"</methods_out_pv>"             ,"</methods_in_out_pv>"            ,                                                                                  
                                         "<_type_input_port_attribute>","<_type_output_port_attribute>","<_type_in_out_put_port_attribute>",
                                         "<_name_input_port_attribute>","<_name_output_port_attribute>","<_name_in_out_put_port_attribute>",                                        
                                         "<_name_input_port_method>"   ,"<_name_output_port_method>"   ,"<_name_in_out_put_port_method>"   ,
                                         "<_dimension_input_port>"     ,"<_dimension_output_port>"     ,"<_dimension_in_out_put_port>"     ,
                                         "<_component_name>"           ,"<_author>"                    ,"<_date_of_created>"           
                                        };
    
    /** Este atributo armazena totas as informações necessárias a criação do componente.*/
    private ColectorDTO c ;
    
    /** Este atributo guarda as informações sobre as portas do novo componente.*/
    private AllComunicationPort ports;
    
    /** Este atributo aponta para a tempalte padrão Java.*/
    private File template_f;
    
    /** Este atributo aponta para o novo componente a ser criado.*/
    private FileOutputStream component_f;
    
    /** Este atributo preenche o conteudo do novo arquivo: componente.*/
    private OutputStreamWriter component_wf; 
    
    /** Este atributo faz a leitura do template Java.*/
    private Reader in;
    
    /** Este atributo armazena as informações contidas no Template Java.*/
    private StringBuffer buff;
    
    /** Este atributo armazena as informações formatadas do componente afim de gera-lo.*/
    private String template_str = null;
    
    /** Este atributo armazena o nome da template de geração.*/
    private String template_name;
    
    /** Este atributo armazena a extensão do novo componente.*/
    private String extension_name;
    
    /** Cria uma nova instancia de ManagerFactor */
    public ManagerFactor() {
    }
    
    /**
      * Escolha da linguagem
      * alvo adotada para a geração do componente.
      * @param _language linguagem alvo java e c.
      * @param _c DTO com informações a cerca do novo componente.
      * @param _ports portas para o novo componente.
      */
    public void createComponent(String _language,ColectorDTO _c, AllComunicationPort _ports){
    
        int ch,i,j; 
        this.c =  new  ColectorDTO();
        this.c = _c;        
        this.ports = new AllComunicationPort();
        this.ports = _ports;        
        
        try{
          // Carrega as informações de configuraão.  
          ManagerSetings m_setings = new ManagerSetings();                    
          i=0;
          // Faz a verificação da linguagem alvo adotada.
          while(!_language.equals(m_setings.getLanguages()[i])){
              i++;
          }
          
          for(j=0; j < this.c.getNumPorts();j++){
              if(this.ports.getPort(j).getPort_type().equals("STRING")){
                // Seta o tipo alfanumérico para a respectiva linguagem alvo.  
                this.ports.getPort(j).setPort_type(m_setings.getTypesString()[i]); 
                
              }else if(this.ports.getPort(j).getPort_type().equals("INTEGER")){
                // Seta o tipo inteiro para a respectiva linguagem alvo.  
                this.ports.getPort(j).setPort_type(m_setings.getTypesInteger()[i]);  
                
              }else if(this.ports.getPort(j).getPort_type().equals("REAL")){
                // Seta o tipo real para a respectiva linguagem alvo.    
                this.ports.getPort(j).setPort_type(m_setings.getTypesReal()[i]);    
                
              }else if(this.ports.getPort(j).getPort_type().equals("BOOLEAN")){
                // Seta o tipo boleano para a respectiva linguagem alvo.    
                this.ports.getPort(j).setPort_type(m_setings.getTypesBoolean()[i]);    
                
              }else if(this.ports.getPort(j).getPort_type().equals("FLOAT")){
                // Seta o tipo flutuante para a respectiva linguagem alvo.    
                this.ports.getPort(j).setPort_type(m_setings.getTypesFloat()[i]);    
              }              
              
              if(this.ports.getPort(j).getPort_dimension().equals("1D")){
                  
                  // Seta a dimensão(1) da porta na sintaxe adlinguagem alvo.
                  this.ports.getPort(j).setPort_dimension( m_setings.get1D()[i]);                  
              }else if(this.ports.getPort(j).getPort_dimension().equals("2D")){
                  
                  // Seta a dimensão(2) da porta na sintaxe adlinguagem alvo.
                  this.ports.getPort(j).setPort_dimension( m_setings.get2D()[i]);
              }
          }
              
          // Faz a seleção de qual template sera utilizada.
          this.template_name = m_setings.getTemplates()[i];
          
          // Faz a seleção da extensão do componente.
          this.extension_name = m_setings.getExtensions()[i];
             
          // Carrega a template adequada.
          this.template_f =  new File(m_setings.getPath_Base()+File.separator+"template"+File.separator+this.template_name);  
          this.in = new FileReader(template_f);                
          
          this.buff = new StringBuffer();          
          // Faz a cópia do conteúdo da template para um Buffer.
          while ((ch = in.read()) != -1) {             
              buff.append((char) ch);	    
	  } 
          
          // Trasfere as informações contidas no Buffer para uma String de Edição.
          this.template_str = buff.toString();
          
          this.in.close();          
          this.factorComponent();
        }catch(IOException ioe){
            System.out.println(ioe.getMessage());
        }          
    }
    /** 
      * Cria o novo componente,
      * rotinas que editam o formato para o novo
      * componente.
      */
    private void factorComponent(){        
       int i,num_ports;
       boolean in,out,inout;
       in = out = inout = false;
       int index_in,index_out,index_inout;
       int[] in_ports,out_ports,inout_ports;
       index_in = index_out = index_inout = 0;       
       ComunicationPort port = null;
       
       this.writeComponentName(this.tag_table[30]);       
       
       this.writeAuthorName(this.tag_table[31],this.tag_table[32]);       
       
       num_ports = this.c.getNumPorts();
       in_ports = new int[num_ports];
       out_ports = new int[num_ports]; 
       inout_ports = new int[num_ports];
       
       // Verifica se existem portas
       if(num_ports != 0){
       
          // Percorre o vetor de portas 
          for(i=0;i<num_ports;i++){                   
              
             port = this.ports.getPort(i);               
             
             // verifica o tipo de cada porta se in,out ou in/out
             switch( port.getPort_format() ){
                 case 0: in = true;
                         in_ports[index_in++] = i;                         
                        break;
                 case 1: out = true;
                         out_ports[index_out ++] = i;                          
                        break;
                 case 2: inout = true;
                         inout_ports[index_inout++] = i;                         
                        break;
             }
          }
          // Faz a formatação da porta de entrada, caso deva ser criada.
          if(in){
              this.writeComponentPort(0,in_ports, index_in);               
          }else{
              this.removePort(0);
          }
          
          // Faz a formatação da porta de saida, caso deva ser criada.
          
          if(out){
              this.writeComponentPort(1,out_ports, index_out); 
          }else{
              this.removePort(1);
          }
          
          // Faz a formatação da porta de entrada/saida, caso deva ser criada.
          
          if(inout){
              this.writeComponentPort(2,inout_ports, index_inout); 
          }else{
              this.removePort(2);
          }          
          
       }else{
          // Faz a remoção de todas as tags de formatação. 
          this.removeAll(); 
       } 
       
       try{// Gera o componente no diretorio especificado                     
          this.component_f = new  FileOutputStream(this.c.getComponent_path() + File.separator + this.c.getComponent_name()+this.extension_name);          
          this.component_wf = new  OutputStreamWriter(this.component_f);                     
          this.component_wf.write(this.template_str);          
          this.component_wf.close();                 
       }catch(IOException ioe){           
          System.out.println(ioe.getMessage()); 
       }         
    }
    
    /**
      * Gera as portas do novo componente.
      * @param _type_ports tipo da(as) portas.
      * @param _posi_ports vetor que guarda as posições das portas no vetor de portas.
      * @param _count_ports quantidade de portas de determinado tipo.
      */    
    private void writeComponentPort(int _type_ports,int[] _posi_ports,int _count_ports){
        this.writePort_Atributes(_type_ports,_posi_ports,_count_ports);
        this.writePort_Methods(_type_ports,_posi_ports,_count_ports);
        
    }
    
    /**
      * Gera as portes do novo componente.
      * @param _type tipo da(as) portas.
      * @param _posi vetor que guarda as posições das portas no vetor de portas.
      * @param _count quantidade de portas de determinado tipo.
      */
    private void writePort_Atributes(int _type,int[] _posi,int _count){
         String format,aux,atributes; 
         int i;
         int[] index = {0,3,18,21,27};         
         atributes = "";
          switch(_type){              
              case 1: index[0]++;  
                      index[1]++;
                      index[2]++;
                      index[3]++;
                      index[4]++;
                      break;
              case 2: index[0] += 2;  
                      index[1] += 2;
                      index[2] += 2;
                      index[3] += 2;
                      index[4] += 2;
          }            
          format = getTamplateDef(this.template_str,tag_table[index[0]],tag_table[index[1]]);                      
          
          for(i=0;i<_count;i++){
              ComunicationPort port = this.ports.getPort(_posi[i]);                           
              aux = format;
              aux = this.replaceWord(aux,tag_table[index[2]],port.getPort_type());
              aux = this.replaceWord(aux,tag_table[index[3]],port.getPort_name());
              aux = this.replaceWord(aux,tag_table[index[4]],port.getPort_dimension());              
              atributes += aux;               
          }
          
          this.template_str = this.replacePort(this.template_str, tag_table[index[0]], tag_table[index[1]],atributes);          
          
          
          
    }   
    
    /**
      * Métodos relacinados a
      * determinadas portas especificadas em _type.
      * @param _type tipo da(as) portas.
      * @param _posi vetor que guarda as posições das portas no vetor de portas.
      * @param _count quantidade de portas de determinado tipo.
      */
    private void writePort_Methods(int _type,int[] _posi,int _count){
         String format,aux,methodspb,methodspv; 
         int i;
         int[] index_pb  = {6,9,18,21,24,27};
         int[] index_pv =  {12,15,18,21,24,27};         
         
         methodspb = methodspv = "";
         
          switch(_type){              
              case 1: index_pb[0]++;  
                      index_pb[1]++;
                      index_pb[2]++;
                      index_pb[3]++;
                      index_pb[4]++;
                      index_pb[5]++;
                      index_pv[0]++;  
                      index_pv[1]++;
                      index_pv[2]++;
                      index_pv[3]++;
                      index_pv[4]++;
                      index_pv[5]++;
                      
                      break;
              case 2: index_pb[0] += 2;  
                      index_pb[1] += 2;
                      index_pb[2] += 2;
                      index_pb[3] += 2;
                      index_pb[4] += 2;
                      index_pb[5] += 2;
                      index_pv[0] += 2;
                      index_pv[1] += 2;
                      index_pv[2] += 2;
                      index_pv[3] += 2;
                      index_pv[4] += 2;
                      index_pv[5] += 2;
          }            
          
          format = getTamplateDef(this.template_str,tag_table[index_pb[0]],tag_table[index_pb[1]]);                      
          
          for(i=0;i<_count;i++){              
              ComunicationPort port = this.ports.getPort(_posi[i]);                           
              aux = format;
              aux = this.replaceWord(aux,tag_table[index_pb[2]],port.getPort_type());
              aux = this.replaceWord(aux,tag_table[index_pb[3]],port.getPort_name());
              aux = this.replaceWord(aux,tag_table[index_pb[4]],port.getPort_name().replace(port.getPort_name().charAt(0),port.getPort_name().toUpperCase().charAt(0)));
              aux = this.replaceWord(aux,tag_table[index_pb[5]],port.getPort_dimension());
              methodspb += aux; 
          }
          
          this.template_str = this.replacePort(this.template_str, tag_table[index_pb[0]], tag_table[index_pb[1]],methodspb);
          
          if(_type != 2){
             format = getTamplateDef(this.template_str,tag_table[index_pv[0]],tag_table[index_pv[1]]);                      
          
             for(i=0;i<_count;i++){                 
                  ComunicationPort port = this.ports.getPort(_posi[i]);                           
                  aux = format;
                  aux = this.replaceWord(aux,tag_table[index_pv[2]],port.getPort_type());
                  aux = this.replaceWord(aux,tag_table[index_pv[3]],port.getPort_name());
                  aux = this.replaceWord(aux,tag_table[index_pv[4]],port.getPort_name().replace(port.getPort_name().charAt(0),port.getPort_name().toUpperCase().charAt(0)));
                  aux = this.replaceWord(aux,tag_table[index_pv[5]],port.getPort_dimension());
                  methodspv += aux; 
             }                     
             this.template_str = this.replacePort(this.template_str, tag_table[index_pv[0]], tag_table[index_pv[1]],methodspv);
          }
    }
    
    /**
      * Usuário criador do componente
      * e a data referente a sua criação.
      * @param _tag_author_name tag referente ao nome do autor na Template.
      * @param _tag_date_of_created tag referente a data de criação do componente.
      */
    private void writeAuthorName(String _tag_author_name,String _tag_date_of_created){ 
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT,Locale.getDefault());
        Date currentDate = new Date();        
        
        this.template_str = this.replaceWord(this.template_str,_tag_date_of_created,formatter.format(currentDate));
        
        this.template_str = this.replaceWord(this.template_str,_tag_author_name,this.c.getAuthor_name());                        
        
    }

    /**
      * Nome do componente no arquivo
      * que fará a geração do novo componente.
      * @param _tag_componet_name tag referente ao nome do componente na
      * Template.
      */
    private void writeComponentName(String _tag_componet_name){        
        
        this.template_str = this.replaceWord(this.template_str,_tag_componet_name,this.c.getComponent_name());
    }
    
    /**
      * Modelo padrão de disposição das tags de dentro da
      * template.
      * @param _template conteúdo da template.
      * @param _tag_open tag delimitadora de inicio.
      * @para _tag_close tag delimitadora de fim.
      */
    private String getTamplateDef(String _template,String _tag_open,String _tag_close){        
        int posi_begin,posi_end;
        posi_begin = _template.indexOf(_tag_open);
        posi_end = _template.indexOf(_tag_close);        
        String def = _template.substring(posi_begin+_tag_open.length(),posi_end);                
        return(def);
    }  
    
    /**
      * Substituição das tags unicas (que não tem delimitadores).
      * @param _text conteúdo da template.
      * @param _fromTag tag que será substituída.
      * @param _toValue valor real.
      */    
    private String replaceWord(String _text,String _fromTag, String _toValue){
        int posi = _text.indexOf(_fromTag);      
        int length = _fromTag.length();     
        
        while(posi != -1){
                _text =  _text.substring(0,posi) + _toValue + _text.substring(posi + length);
                posi = _text.indexOf(_fromTag);      
         }       
        
        return(_text);
    }
    
   /**
     * Substitui as tags pelos valores reais.
     * @param _text conteúdo da template.
     * @param _begin_tag tag de abertura.
     * @param _end_tag tag de finalisação.
     * @param _new_atb valor que substituir a tag.
     */
    private String replacePort(String _text,String _begin_tag,String _end_tag, String _new_atb){
        int posi_begin, posi_end;
        posi_begin = _text.indexOf(_begin_tag);
        posi_end   = _text.indexOf(_end_tag);
        _text = _text.substring(0,posi_begin) + _new_atb + _text.substring(posi_end + _end_tag.length());       
        return(_text);
    }
    
   /**
     * Este método remove todas as tags do arquivo.
     */
    private void removeAll(){
        this.removePort(0);
        this.removePort(1);
        this.removePort(2);
    }
    
    /**
      * Remove as tags que definem as portas e seus métodos. Uma vez
      * que elas não farão parte do código fonte do componente.
      * @param _type referencia a tipo da porta, se IN, OUT ou INOUT.
      */
    private void removePort(int _type){
        //Adota como default as tags para portas IN.
        int[] port_tags = {0,3,6,9,12,15};        
        
        //Seleciona o tipo da porta se IN ou OUT ou INOUT
        switch(_type){
            //Seleciona as tags especifias para as portas OUT.
            case 1: port_tags[0]++; 
                    port_tags[1]++; 
                    port_tags[2]++; 
                    port_tags[3]++; 
                    port_tags[4]++; 
                    port_tags[5]++;                     
                    break;
            //Seleciona as tags especificas para a portas INOUT.        
            case 2: port_tags[0] += 2; 
                    port_tags[1] += 2; 
                    port_tags[2] += 2; 
                    port_tags[3] += 2; 
                    port_tags[4] += 2; 
                    port_tags[5] += 2; 
        }
        
        
        this.template_str = this.replacePort(this.template_str, tag_table[port_tags[0]], tag_table[port_tags[1]],"");
        
        this.template_str = this.replacePort(this.template_str, tag_table[port_tags[2]], tag_table[port_tags[3]],"");
        
        if(_type !=2)
            this.template_str = this.replacePort(this.template_str, tag_table[port_tags[4]], tag_table[port_tags[5]],"");
                       
    }
    
}
