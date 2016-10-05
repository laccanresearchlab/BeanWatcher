/*
 * JFlashWinReply.java
 *
 * Created on 16 de Junho de 2003, 22:57
 */

package gui;
import br.ufpi.die.jflash.reply.*;
import setings.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
/**
 *
 * @author  Lincoln Souza Rocha
 * @version 1.0
 */
@SuppressWarnings("unused")
public class JFlashWinReply extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ManagerSetings ms;
    
    private JPanel mainPane;
    private JPanel infoPane;
    private JPanel languagePane;    
    private JPanel load_cptPane;
    private JPanel arqPane;
    private JPanel buttonsPane;
    
    private JButton replyButton;    
    private JButton loadButton;
    private JButton load_cptButton;
    
    private JLabel languageTxt;
    private JLabel loadTxt;
    private JLabel load_cptTxt;
    
    private JTextField loadField;
    private JTextField load_cptField;
    
    private JComboBox<String> languageCBox;
    private ExampleFileFilter xml;
    private JFileChooser file_ch;
    private JFileChooser file_cpt;
    private Toolkit tk;
    private Image img;
    
    private String data_store;
    private String cpt_store;
    /** Creates a new instance of JFlashWinReply */
    public JFlashWinReply(){ 
        initComponents();        
        tk = Toolkit.getDefaultToolkit();
        Dimension d =  tk.getScreenSize();
        tk = Toolkit.getDefaultToolkit();        
        img =  tk.getImage(getClass().getResource("/img/logojf.jpg"));                
        int w_Screen = d.width;
        int h_Screen = d.height;
        this.setSize((w_Screen / 2),(h_Screen / 2) - (w_Screen / 7));
        this.setLocation((w_Screen / 4),(h_Screen / 4));        
    }
    
    private void initComponents() {       
       int i;
       ms = new ManagerSetings(); 
       mainPane = new JPanel(new BorderLayout());
       infoPane = new JPanel(new GridLayout(3,1));
       languagePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
       arqPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
       load_cptPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
       loadTxt = new JLabel("Repository File");    
       
       loadField = new JTextField(20);
       
       load_cptField = new JTextField(20);


       buttonsPane = new JPanel();
       
       file_ch = new JFileChooser();
       xml = new ExampleFileFilter("xml","XML Files ");
       this.file_ch.setCurrentDirectory(new File(ms.getPath_Base()+File.separator+"data_store"));       
       
       file_cpt = new JFileChooser();
       this.file_cpt.setCurrentDirectory(new File(ms.getPath_Base()+File.separator+"repository"));              
       mainPane.setBorder(new EtchedBorder());
       infoPane.setBorder(new EtchedBorder());
       buttonsPane.setBorder(new EtchedBorder());
       
       replyButton = new JButton("Replicate");       
       replyButton.setIcon(new ImageIcon(getClass().getResource("../img/replybutton.gif")));
       loadButton  = new JButton();
       loadButton.setIcon(new ImageIcon(getClass().getResource("../img/open.gif")));
       
       load_cptButton = new JButton();
       load_cptButton.setIcon(new ImageIcon(getClass().getResource("../img/open.gif")));
       
       
       replyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                replyButton(evt);
            }
        });
        
        buttonsPane.add(replyButton);        
       
       languageTxt = new JLabel("Target Language: ");
       load_cptTxt = new JLabel("Directory for new coponents: ");
       
       languageCBox = new JComboBox<String>();
       languageCBox.setEditable(false);
       languageCBox.setEnabled(true);
       languageCBox.setPreferredSize(new Dimension(100,22));              
       
       for(i=0;i<ms.getLanguages().length;i++){
          languageCBox.addItem(ms.getLanguages()[i]); 
       }              
       languagePane.add(languageTxt);
       languagePane.add(languageCBox);      
       
       loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                loadButton(evt);
            }
        });
        
        load_cptButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                load_cptButton(evt);
            }
        });
       arqPane.add(loadTxt);
       loadField.setEditable(false);
       data_store = ms.getPath_Base()+ File.separator + "data_store" + File.separator + "data_store.xml";     

       loadField.setText(data_store);
       arqPane.add(loadField); 
       arqPane.add(loadButton); 
       
       load_cptPane.add(load_cptTxt);
       
       load_cptField.setEditable(false);       
       cpt_store = ms.getPath_Base()+File.separator+"repository"+File.separator+"j2me";
       load_cptField.setText(cpt_store);
       load_cptPane.add(load_cptField);       
       
       load_cptPane.add(load_cptButton);
       
       infoPane.add(languagePane);      
       infoPane.add(arqPane);      
       infoPane.add(load_cptPane);
       mainPane.add(infoPane,BorderLayout.CENTER);
       mainPane.add(buttonsPane,BorderLayout.SOUTH);
       
       this.add(mainPane,BorderLayout.CENTER);       
    }
    
   private void loadButton(ActionEvent evt){       
       JFrame frame = new JFrame();
       frame.setIconImage(img);
       this.file_ch.setFileFilter(xml);
       this.file_ch.setFileSelectionMode(JFileChooser.FILES_ONLY);
       int retval = this.file_ch.showDialog(frame,"Load Arquive from Reply");
       if(retval != JFileChooser.CANCEL_OPTION)                  
           loadField.setText(file_ch.getSelectedFile().getAbsolutePath());       
       
   }
   
   private void load_cptButton(ActionEvent evt){       
       JFrame frame = new JFrame();
       frame.setIconImage(img);
       //this.file_cpt
       this.file_cpt.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
       int retval = this.file_cpt.showDialog(frame,"Select Component Directory");       
       if(retval != JFileChooser.CANCEL_OPTION)                  
            load_cptField.setText(file_cpt.getSelectedFile().getAbsolutePath());       
       
       
   }
   private void replyButton(ActionEvent evt){
       boolean def_data,def_cpt;
        ReplyRepository  reply = new ReplyRepository(loadField.getText());
        reply.reply((String)languageCBox.getSelectedItem(),load_cptField.getText());        
        JOptionPane.showMessageDialog(new JFrame(),"The reply component repository, successful!","Confirmation",JOptionPane.INFORMATION_MESSAGE);        
    }
    
}
