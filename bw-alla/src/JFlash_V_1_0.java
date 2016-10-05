/**
 * Copyright (c) 2003. All rights reserved.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the MIT License (MIT) only.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

import gui.JFlashWinColector;
import gui.JFlashWinReply;
/**
 * @author  Lincoln Souza Rocha
 * @author Randy Quindai
 */
public class JFlash_V_1_0 extends JFrame{
    /**
	 * JFlash_V_1_0.java
	 * Created on 27 de Abril de 2003, 15:45
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
    private JFlashWinColector  win_clto;
    private JFlashWinReply win_reply;        
    
    private JPanel buttonPane;
    private JPanel statusPane;
    private JPanel tab1Pane;
    private JPanel tab2Pane;
    
    private JToolBar jTB = new JToolBar();
    
    private JMenuBar menuBar;    
        
    private JButton exitButton;
    private JButton aboutButton;
    
    
    private JMenu fileMenu;
    private JMenu helpMenu;        

    private JMenuItem exitMenuItem;
    private JMenuItem aboutMenuItem;
    
    private Toolkit tk;
    
    private Image img;
    
    /** Cria uma nova instancia para a classe JFlash_V_1_0 */
    public JFlash_V_1_0() {
        this.setTitle("JFlash 1.0 Alpha");        
        tk = Toolkit.getDefaultToolkit();        
        img = tk.getImage(getClass().getResource("/img/logojf.jpg"));        
        this.setIconImage(img);
        
        initComponents();
        
        Dimension d = tk.getScreenSize();
        int w_Screen = d.width;
        int h_Screen = d.height;
        this.setSize((w_Screen / 4)*3, (h_Screen / 4)*3);
        this.setLocation((w_Screen / 6), (h_Screen / 8));
        
    }
    
    /** Instancia os componentes do Form JFlash*/
    private void initComponents() {
        tabbedPane = new JTabbedPane();         
        buttonPane = new JPanel();        
        statusPane = new JPanel();                
        tab1Pane = new JPanel(new BorderLayout());
        tab2Pane = new JPanel(new BorderLayout());
        aboutButton = new JButton();
        exitButton =  new JButton();
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        helpMenu = new JMenu("Help");        
        exitMenuItem = new JMenuItem("Exit");
        aboutMenuItem = new JMenuItem("About");
        
        win_clto = new JFlashWinColector();
        tab1Pane.add(win_clto,BorderLayout.WEST);
        tabbedPane.addTab("New Component",new ImageIcon(getClass().getResource("/img/newcpt.gif")),tab1Pane);
        win_reply = new JFlashWinReply();
        tab2Pane.add(win_reply,BorderLayout.WEST);
        tabbedPane.setBorder(new EtchedBorder());
        tabbedPane.addTab("Repository Replication",new ImageIcon(getClass().getResource("/img/reply.gif")),tab2Pane);        

        
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });        
        
        exitButton.setIcon(new ImageIcon(getClass().getResource("/img/exit.gif")));
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitMenuItemActionPerformed(evt); 
            }
        });
        aboutButton.setIcon(new ImageIcon(getClass().getResource("/img/help.gif")));
        buttonPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        jTB.setPreferredSize(new java.awt.Dimension(10, 30));
        jTB.setOrientation(JToolBar.HORIZONTAL);
        buttonPane.add(jTB);        
        buttonPane.add(exitButton);
        buttonPane.add(aboutButton);
        
        buttonPane.setBorder(new EtchedBorder());        

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                exitMenuItemActionPerformed(evt); 
            }
        });
        
        fileMenu.add(exitMenuItem);
        
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);
        
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        
        
        statusPane.setBorder(new EtchedBorder());
        
        this.setJMenuBar(menuBar);
        this.getContentPane().add(buttonPane,BorderLayout.NORTH);
        this.getContentPane().add(statusPane,BorderLayout.SOUTH);                
        this.getContentPane().add(tabbedPane,BorderLayout.CENTER);        
        
        
        this.pack();
    }
    
    
    private void exitMenuItemActionPerformed(ActionEvent evt) {        
        System.exit(0);
    }
    
    private void aboutMenuItemActionPerformed(ActionEvent evt) {
        
    }
    
    
    private void exitForm(WindowEvent evt) {        
        System.exit(0);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new JFlash_V_1_0().setVisible(true);
    }
    
}
