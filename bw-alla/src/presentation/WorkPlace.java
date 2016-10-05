/*
 * WorkPlaceFrame2.java
 *
 * Created on February 21, 2003, 2:17 PM
 */
package presentation;

import br.ufpi.die.jflash.datastore.ManagerDataStore;
import br.ufpi.die.jflash.dto.ColectorDTO;
import br.ufpi.die.jflash.property.Properties;
import componentmodel.Component;
import componentmodel.Connector;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.Border;
import util.*;

/** This classes is to interface from BeanWatcher
 * @author  alla
 * @version 2.0.0.1
 */
public class WorkPlace extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** State defined for add component*/
    private static final int s_StateAddInstr = 0;
    /** State defined for remove component*/
    //@SuppressWarnings("unused")
	private static final int s_StateRemInstr = 1;
    /** State defined for add connector component*/
    private static final int s_StateAddConnect = 2;
    /** State defined for edit (move) component*/
    private static final int s_StateEdit = 3;
    /** State defined for set properties of component*/
    private static final int s_StateProperties = 4;
    /** State defined for bring front component in workplace*/
    //@SuppressWarnings("unused")
	private static final int s_StateBrngFrnt = 5;
    /** State defined for send back component in workplace*/
    //@SuppressWarnings("unused")
	private static final int s_StateSendBack = 6;
    /** Default colors used when select objects*/
    private static final Color xorColor = new Color(20, 20, 255);
    /** Default first colors used when select objects*/
    private static final Color firstXorColor = new Color(20, 255, 20);
    
    /** Represent the state actual from workplace*/
    private int m_Status;
    /** Represent the number of component in workplace*/
    //@SuppressWarnings("unused")
	private int compNumber = 0;
    /** Used for controll pevious points clicked*/
    private Point m_PrevPt;
    /** Used for controll current points clicked*/
    private Point m_ClickedPt;
    /** Rectangle for many intrument*/
    private Rectangle[] m_RectObjs;
    /** Variable for selected objects*/
    private Vector<ImagePanel> m_SelectedObjects;
    /** Variable for check if component is pressed*/
    private boolean pressedInsideInstrument;
    
    /** Variable for name of component selected*/
    //@SuppressWarnings("unused") 
	private String componentSelected = "bwPanel";
    /** Variable for all objects interfaces in workplace*/
    private Hashtable<String, ImagePanel> m_ImagePanel = new Hashtable<String, ImagePanel>();
    /** Variable for all components in workplace*/
    private Hashtable<String, Component> m_ComponentWork = new Hashtable<String, Component>();
    /** Variable for all components collectors in repository*/
    private Hashtable<String, ColectorDTO> allComponentsCollector = new Hashtable<String, ColectorDTO>();
    /** Array of components to composer in processing layer*/
    private componentmodel.Component componentToComposer[];
    /** Array of connectors to composer in processing layer*/
    private componentmodel.Connector connectorToComposer[];
    /** Frame for set connectors in workplace*/
    //private ConnectorFrame connectorFrame;
    /** Frame for create the component of visualize*/
    //private WizardFrame wizardFrame;
    private Vector<Connector> connectorVector = new Vector<Connector>();
    @SuppressWarnings("unused")
	private Vector<Object> lineConnectorVect = new Vector<Object>();
    private MouseEvent evtBuffer[] = new MouseEvent[2];
    private boolean firstClick = false;
    @SuppressWarnings("unused")
	private boolean secondClick = false;
    
    private boolean applChoose;
    
    private boolean codeArea = false;
    private String currentEdit = "";
    
    /**
     * Creates new form WorkPlaceFrame
     */
    public WorkPlace() {
        // Init components in workplace
        initComponents();
        // Init variables this classes
        initVariables();
        // Star components of repository from combobox
        //this.componentComboBox.addItem(new String("< Choose type >"));
    }
    
    private void defaultValues(){
        this.codeEditTextArea.setText("");
        this.currentEdit = "";
        simpleRadioButton.setEnabled(true);
        applicationRadioButton.setEnabled(true);
        compName.setEnabled(true);
        compName.setText("ComponentName");
        simpleRadioButton.setSelected(false);
        applicationRadioButton.setSelected(false);
        bwPanel.removeAll();
        m_ComponentWork.clear();
        if(m_SelectedObjects != null)
            m_SelectedObjects.removeAllElements();
        connectorVector.removeAllElements();
        this.repaint();
        this.componentComboBox.removeAllItems();
        this.allComponentsCollector.clear();
        this.componentComboBox.addItem(new String("< Choose type >"));
        this.jTabbedPane1.setSelectedIndex(0);
    }
    
    /**
     * This method is called from within the constructor to
     * initialize the variables used.
     */
    private void initVariables(){
        m_Status           = s_StateAddInstr;
        m_SelectedObjects  = new Vector<ImagePanel>();
        pressedInsideInstrument = false;
        
        addComponentButton.setToolTipText("Add component");
        addComponentButton.setMnemonic(KeyEvent.VK_I);
        
        removeComponentButton.setToolTipText("Remove component");
        removeComponentButton.setMnemonic(KeyEvent.VK_R);
        
        connectorButton.setToolTipText("Add connector");
        connectorButton.setMnemonic(KeyEvent.VK_C);
        
        moveComponentButton.setToolTipText("Move");
        moveComponentButton.setMnemonic(KeyEvent.VK_M);
        
        generateCode.setToolTipText("Save code");
        generateCode.setMnemonic(KeyEvent.VK_S);
    }
    
    /**
     * This method add components for menu in workplace, this use
     * an vector variable for to do this list, and get information from
     * XLM file with caracteristics of components
     */
    private void addComponentToList() {
        String name;
        int size;
        ColectorDTO colector;
        ManagerDataStore mds;
        Vector<?> componentsDescription;
        
        
        // Load all component descriprion in a vector
        if(!applChoose){
            mds = new ManagerDataStore(Config.getChildConfigFile());
        } else {
            mds = new ManagerDataStore(Config.getFatherConfigFile());
        }
        componentsDescription = mds.loadRepository();
        
        // Load comboBox and all components collectors hash
        
        size = componentsDescription.size();
                
        System.out.println(size);
        
        
        for(int i = 0; i < size; i++){
            if(!(componentsDescription.get(i) instanceof ColectorDTO)){
                continue;
            }
            colector = (ColectorDTO)componentsDescription.get(i);
            name     = colector.getComponent_name();
            
            this.componentComboBox.addItem(name);
            this.allComponentsCollector.put(name, colector);
            
        }
    }
    
    /**
     * This method add components for menu in workplace, this use
     * an vector variable for to do this list, and get information from
     * XLM file with caracteristics of components
     */
    private void addAllComponentToList() {
        String name;
        int size;
        ColectorDTO colector;
        ManagerDataStore mds;
        Vector<?> componentsDescription;
        
        
        mds = new ManagerDataStore(Config.getChildConfigFile());
        componentsDescription = mds.loadRepository();
        
        // Load comboBox and all components collectors hash
        size = componentsDescription.size();
        for(int i = 0; i < size; i++){
            if(!(componentsDescription.get(i) instanceof ColectorDTO)){
                continue;
            }
            colector = (ColectorDTO)componentsDescription.get(i);
            
            name     = colector.getComponent_name();
            if(name.equals("Filter")){
                continue;
            }
            
            this.componentComboBox.addItem(name);
            this.allComponentsCollector.put(name, colector);
            
        }
        
        mds = new ManagerDataStore(Config.getFatherConfigFile());
        componentsDescription = mds.loadRepository();
        
        // Load comboBox and all components collectors hash
        size = componentsDescription.size();
        for(int i = 0; i < size; i++){
            if(!(componentsDescription.get(i) instanceof ColectorDTO)){
                continue;
            }
            colector = (ColectorDTO)componentsDescription.get(i);
            name     = colector.getComponent_name();
            
            this.componentComboBox.addItem(name);
            this.allComponentsCollector.put(name, colector);
            
        }
    }
    
    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        java.awt.GridBagConstraints gridBagConstraints;

        toolPanel = new javax.swing.JPanel();
        componentComboBox = new javax.swing.JComboBox<String>();
        addComponentButton = new javax.swing.JButton();
        removeComponentButton = new javax.swing.JButton();
        connectorButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        moveComponentButton = new javax.swing.JButton();
        propertiesButton = new javax.swing.JButton();
        wizardButton = new javax.swing.JButton();
        generateCode = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        componentScrollPane = new javax.swing.JScrollPane();
        bwPanel = new javax.swing.JPanel();
        codeScrollPane = new javax.swing.JScrollPane();
        codeEditTextArea = new javax.swing.JTextArea();
        applChoosePanel = new javax.swing.JPanel();
        simpleComponent = new javax.swing.JPanel();
        simpleRadioButton = new javax.swing.JRadioButton();
        compName = new javax.swing.JTextField();
        application = new javax.swing.JPanel();
        applicationRadioButton = new javax.swing.JRadioButton();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        newFileMenuItem = new javax.swing.JMenuItem();
        openFileMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsFileMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        exitFileMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutHelpMenuItem = new javax.swing.JMenuItem();

        setTitle("BeanWatcher");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        toolPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        toolPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 204), null, new java.awt.Color(204, 204, 204), new java.awt.Color(204, 204, 204)));
        toolPanel.add(componentComboBox);

        addComponentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addinstr.gif")));
        addComponentButton.setContentAreaFilled(false);
        addComponentButton.setName("addButton");
        addComponentButton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        addComponentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addComponentButtonActionPerformed(evt);
            }
        });

        toolPanel.add(addComponentButton);

        removeComponentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/reminstr.gif")));
        removeComponentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeComponentButtonActionPerformed(evt);
            }
        });

        toolPanel.add(removeComponentButton);

        connectorButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chain.gif")));
        connectorButton.setMaximumSize(new java.awt.Dimension(57, 33));
        connectorButton.setMinimumSize(new java.awt.Dimension(57, 33));
        connectorButton.setPreferredSize(new java.awt.Dimension(57, 33));
        connectorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectorButtonActionPerformed(evt);
            }
        });

        toolPanel.add(connectorButton);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setFont(new java.awt.Font("Arial", 0, 10));
        toolPanel.add(jSeparator1);

        moveComponentButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/edit.gif")));
        moveComponentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moveComponentButtonActionPerformed(evt);
            }
        });

        toolPanel.add(moveComponentButton);

        propertiesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/propert.gif")));
        propertiesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                propertiesButtonActionPerformed(evt);
            }
        });

        toolPanel.add(propertiesButton);

        wizardButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/new.gif")));
        wizardButton.setMaximumSize(new java.awt.Dimension(57, 33));
        wizardButton.setMinimumSize(new java.awt.Dimension(57, 33));
        wizardButton.setPreferredSize(new java.awt.Dimension(57, 33));
        toolPanel.add(wizardButton);

        generateCode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/genpanel.gif")));
        generateCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateCodeActionPerformed(evt);
            }
        });

        toolPanel.add(generateCode);

        getContentPane().add(toolPanel, java.awt.BorderLayout.NORTH);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(600, 400));
        componentScrollPane.setName("Component");
        componentScrollPane.setPreferredSize(new java.awt.Dimension(33, 33));
        componentScrollPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                componentScrollPaneComponentShown(evt);
            }
        });

        bwPanel.setBackground(new java.awt.Color(255, 255, 255));
        bwPanel.setBorder(new javax.swing.border.BevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray, java.awt.Color.lightGray));
        bwPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bwPanelMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bwPanelMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                bwPanelMouseReleased(evt);
            }
        });
        bwPanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                bwPanelMouseDragged(evt);
            }
        });

        componentScrollPane.setViewportView(bwPanel);

        jTabbedPane1.addTab("Component", componentScrollPane);

        codeScrollPane.setName("Code");
        codeScrollPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                codeScrollPaneComponentShown(evt);
            }
        });

        codeScrollPane.setViewportView(codeEditTextArea);

        jTabbedPane1.addTab("Code", codeScrollPane);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        applChoosePanel.setLayout(new java.awt.GridBagLayout());

        applChoosePanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, null, java.awt.Color.lightGray, java.awt.Color.lightGray));
        simpleComponent.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        simpleRadioButton.setText("Simple Component");
        simpleRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpleRadioButtonActionPerformed(evt);
            }
        });

        simpleComponent.add(simpleRadioButton);

        compName.setText("ComponentName");
        compName.setPreferredSize(new java.awt.Dimension(120, 20));
        simpleComponent.add(compName);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        applChoosePanel.add(simpleComponent, gridBagConstraints);

        application.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        applicationRadioButton.setText("Complete application");
        applicationRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applicationRadioButtonActionPerformed(evt);
            }
        });

        application.add(applicationRadioButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        applChoosePanel.add(application, gridBagConstraints);

        getContentPane().add(applChoosePanel, java.awt.BorderLayout.SOUTH);

        mainMenuBar.setBorder(null);
        fileMenu.setMnemonic('F');
        fileMenu.setText("File");
        newFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        newFileMenuItem.setMnemonic('N');
        newFileMenuItem.setText("New");
        fileMenu.add(newFileMenuItem);

        openFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openFileMenuItem.setMnemonic('O');
        openFileMenuItem.setText("Open...");
        fileMenu.add(openFileMenuItem);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setMnemonic('S');
        saveMenuItem.setText("Save");
        fileMenu.add(saveMenuItem);

        saveAsFileMenuItem.setMnemonic('A');
        saveAsFileMenuItem.setText("Save As...");
        fileMenu.add(saveAsFileMenuItem);

        fileMenu.add(jSeparator2);

        exitFileMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        exitFileMenuItem.setMnemonic('x');
        exitFileMenuItem.setText("Exit");
        fileMenu.add(exitFileMenuItem);

        mainMenuBar.add(fileMenu);

        helpMenu.setMnemonic('H');
        helpMenu.setText("Help");
        aboutHelpMenuItem.setMnemonic('A');
        aboutHelpMenuItem.setText("About...");
        helpMenu.add(aboutHelpMenuItem);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        pack();
    }//GEN-END:initComponents
    
    private void componentScrollPaneComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_componentScrollPaneComponentShown
        codeArea = false;
        defaultValues();
    }//GEN-LAST:event_componentScrollPaneComponentShown
    
    private void codeScrollPaneComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_codeScrollPaneComponentShown
        codeArea = true;
        this.componentComboBox.removeAllItems();
        addAllComponentToList();
    }//GEN-LAST:event_codeScrollPaneComponentShown
    
    private void propertiesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_propertiesButtonActionPerformed
        m_Status = s_StateProperties;
    }//GEN-LAST:event_propertiesButtonActionPerformed
    
    private void applicationRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applicationRadioButtonActionPerformed
        simpleRadioButton.setSelected(false);
        simpleRadioButton.setEnabled(false);
        compName.setEnabled(false);
        compName.setText("ComponentName");
        applChoose = true;
        
        bwPanel.removeAll();
        m_ComponentWork.clear();
        m_SelectedObjects.removeAllElements();
        connectorVector.removeAllElements();
        this.repaint();
        
        this.componentComboBox.removeAllItems();
        this.allComponentsCollector.clear();
        addComponentToList();
    }//GEN-LAST:event_applicationRadioButtonActionPerformed
    
    private void simpleRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpleRadioButtonActionPerformed
        applicationRadioButton.setSelected(false);
        applicationRadioButton.setEnabled(false);
        applChoose = false;
        
        this.componentComboBox.removeAllItems();
        this.allComponentsCollector.clear();
        addComponentToList();
    }//GEN-LAST:event_simpleRadioButtonActionPerformed
    
    /** Methods for mouse actions start */
    private void bwPanelMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bwPanelMouseReleased
        Object obj = evt.getSource();
        Graphics g;
        ImagePanel component;
        try {
            // Check if the obj is one ImagePanel
            if (obj instanceof ImagePanel) {
                switch (m_Status) {
                    // Case to edit button pressed
                    case s_StateEdit: {
                        if (pressedInsideInstrument) {
                            moveSelectedObjects(evt.getX()-m_ClickedPt.x, evt.getY()-m_ClickedPt.y, m_RectObjs);
                            pressedInsideInstrument = false;
                        }
                        g = bwPanel.getGraphics();
                        
                        g.setXORMode(xorColor);
                        drawRects(evt.getX()-m_PrevPt.x, evt.getY()-m_PrevPt.y, setRectsToDraw(), g);
                        g.dispose();
                        repaint();
                        //paintLine();
                        
                        component = (ImagePanel)obj;
                        //this.componentSelected = component.getComponentName();
                        break;
                    }
                    
                    // Case for properties button pressed
                    case s_StateProperties:
                        component = (ImagePanel)obj;
                        this.setPropertyTable(component.getComponentName());
                        break;
                }
            }
            
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_bwPanelMouseReleased
    
    private void bwPanelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bwPanelMousePressed
        Object obj = evt.getSource();
        Graphics   g;
        ImagePanel instrument;
        
        try {
            // Check if the obj is one ar51 object and it�s in workplace
            if ((obj instanceof ImagePanel) && (obj != bwPanel)) {
        	// DEBUG - Alla 07-01-16
        	//if ((evt.getComponent() instanceof ImagePanel) && (evt.getComponent() != bwPanel)) {
                instrument = (ImagePanel) obj;
        		// DEBUG - Alla 07-01-16
        		//instrument = (ImagePanel)evt.getComponent();
                switch (m_Status) {
                    // Case to edit button pressed
                    case s_StateEdit: {
                        m_RectObjs  = setRectsToDraw();
                        m_ClickedPt = evt.getPoint();
                        m_PrevPt    = m_ClickedPt;
                        
                        g = bwPanel.getGraphics();
                        
                        g.setXORMode(xorColor);
                        drawRects(m_RectObjs, g);
                        g.dispose();
                        if (m_SelectedObjects.contains(instrument)) {
                            pressedInsideInstrument = true;
                        } else {
                            pressedInsideInstrument = false;
                        }
                        //this.componentSelected = instrument.getComponentName();
                        
                        break;
                    }
                }
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_bwPanelMousePressed
    
    private void bwPanelMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bwPanelMouseDragged
        //ImagePanel instrument;
        Graphics   g;
        Object     obj;
        
        g   = bwPanel.getGraphics();
        obj = evt.getSource();
        try {
            // Check if the obj is one component in workplace
            if ((obj instanceof ImagePanel) && (obj != bwPanel)) {
                //instrument = (ImagePanel) obj;
                switch (m_Status) {
                    // Case to edit button pressed
                    case s_StateEdit:
                        if (pressedInsideInstrument) {
                            g.setXORMode(xorColor);
                            drawRects(m_PrevPt.x-m_ClickedPt.x,m_PrevPt.y-m_ClickedPt.y,m_RectObjs, g);
                            drawRects(evt.getX()-m_ClickedPt.x,evt.getY()-m_ClickedPt.y,m_RectObjs, g);
                            m_PrevPt  = evt.getPoint();
                            g.dispose();
                            //this.componentSelected = instrument.getComponentName();
                        }
                        break;
                }
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_bwPanelMouseDragged
    
    private void bwPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bwPanelMouseClicked
        Object obj = evt.getSource();
        ImagePanel instrument;
        @SuppressWarnings("unused")
		Graphics   g;
        
        try {
            
            switch (m_Status) {
                // Button from add new instrument
                case s_StateAddInstr: {
                    addComponent(evt.getX(), evt.getY());
                    break;
                }
                case s_StateAddConnect: {
                    if (obj instanceof ImagePanel){
                        if(!firstClick){
                            evtBuffer[0] = evt;
                            firstClick = true;
                        } else {
                            evtBuffer[1] = evt;
                            addConnector();
                            firstClick = false;
                        }
                    }
                    break;
                }
                // Case to edit button pressed
                case s_StateEdit: {
                    if (obj instanceof ImagePanel){
                        instrument = (ImagePanel) obj;
                        select(instrument, evt.getModifiers(), true);
                        //this.componentSelected = instrument.getComponentName();
                        repaint();
                        //paintLine();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_bwPanelMouseClicked
    /** Methods for mouse actions end*/
    
    /** Methods for buttons actions start*/
    private void removeComponentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeComponentButtonActionPerformed
        int size;
        int i;
        String componentName;
        ImagePanel componentRmv;
        
        try {
            if (!m_SelectedObjects.isEmpty()) {
                size = m_SelectedObjects.size();
                System.out.println(size);
                for (i = 0; i < size; i++) {
                    componentRmv = m_SelectedObjects.elementAt(i);
                    bwPanel.remove(componentRmv);
                    componentName = componentRmv.getComponentName();
                    m_ComponentWork.remove(componentName);
                    //remove line
                }
                m_SelectedObjects.removeAllElements();
                this.repaint();
                //paintLine();
            }
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_removeComponentButtonActionPerformed
    
    private void connectorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectorButtonActionPerformed
        try {
            m_Status = s_StateAddConnect;
        } catch (java.lang.Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_connectorButtonActionPerformed
    
    private void generateCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateCodeActionPerformed
        if(codeArea){
            if(!currentEdit.equals("")){
                String path;
                ColectorDTO colector;
                
                colector = allComponentsCollector.get(currentEdit);
                path = colector.getComponent_path()+currentEdit;
                FileFactor.writeFile(path, codeEditTextArea.getText());
            }
        } else {
            if(!this.m_ComponentWork.isEmpty()){
                if(applChoose){
                    this.saveApplication();
                    javax.swing.JOptionPane.showMessageDialog(this, "Successfully generated application!");
                } else {
                    this.saveComponent();
                    javax.swing.JOptionPane.showMessageDialog(this, "Successfully generated component!");
                }
                
            }
        }
        this.defaultValues();
    }//GEN-LAST:event_generateCodeActionPerformed
    
    private void moveComponentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moveComponentButtonActionPerformed
        m_Status = s_StateEdit;
    }//GEN-LAST:event_moveComponentButtonActionPerformed
    
    private void addComponentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addComponentButtonActionPerformed
        if(!codeArea){
            m_Status = s_StateAddInstr;
        } else {
            addCode();
        }
    }//GEN-LAST:event_addComponentButtonActionPerformed
    
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm
    
    /**
     *
     */
    public void setPropertyTable(String _componentName){
        ManagerDataStore mds;
        ColectorDTO      colector;
        
        Vector<?> allCompDescription;
        Vector<Object> properVector = new Vector<Object>();
        String name;
        int sizeDesc;
        
        // Get model from origy source
        if(!applChoose){ // Child source
            mds = new ManagerDataStore(Config.getChildConfigFile());
        } else {  // application source
            mds = new ManagerDataStore(Config.getFatherConfigFile());
        }
        
        allCompDescription = mds.loadRepository();
        
        sizeDesc = allCompDescription.size();
        int i = 0;
        while((i < sizeDesc)){
            if(allCompDescription.get(i) instanceof ColectorDTO){
                colector = (ColectorDTO)allCompDescription.get(i++);
                name = colector.getComponent_name();
                if(!name.equals(_componentName)){
                    continue;
                }else {
                    properVector = new Vector<Object>();
                    while((i < sizeDesc) && (allCompDescription.get(i) instanceof Properties)){
                        properVector.add((Properties)allCompDescription.get(i++));
                    }
                    break;
                }
            } else {
                i++;
            }
        }
        
        Property prop = new Property(_componentName, properVector);
        System.out.println();
        prop.setVisible(true);
        @SuppressWarnings("unused")
		String[][] st = prop.getValues();
    }
    
    /**
     *
     *
     */
    private void saveApplication(){
        int size = connectorVector.size();
        
        connectorToComposer = new componentmodel.Connector[size];
        for(int i = 0; i < size; i++ ){
            connectorToComposer[i] = connectorVector.get(i);
        }
        
        Enumeration<Component> elementsHash;
        int i = 0;
        int decrease = 0;
        if(this.m_ComponentWork.containsKey("Filter")){
            decrease++;
        }
        if(this.m_ComponentWork.containsKey("Comunicator")){
            decrease++;
        }
        
        size = this.m_ComponentWork.size() - decrease;
        this.componentToComposer = new componentmodel.Component[size];
        elementsHash = this.m_ComponentWork.elements();
        componentmodel.Component componentAux;
        while(elementsHash.hasMoreElements()){
            componentAux = elementsHash.nextElement();
            if (componentAux.getType().equals("Filter") || componentAux.getType().equals("Comunicator")){
                continue;
            }
            this.componentToComposer[i] = componentAux;
            i++;
        }
        
        @SuppressWarnings("unused")
		processing.ApplicationComposer applComp;
        applComp = new processing.ApplicationComposer(this.componentToComposer, this.connectorToComposer);
    }
    
    /**
     *
     *
     */
    private void saveComponent(){
        int size = connectorVector.size();
        
        connectorToComposer = new componentmodel.Connector[size];
        for(int i = 0; i < size; i++ ){
            connectorToComposer[i] = connectorVector.get(i);
        }
        
        Enumeration<Component> elementsHash;
        int i = 0;
        size = this.m_ComponentWork.size();
        
        this.componentToComposer = new componentmodel.Component[size];
        elementsHash = this.m_ComponentWork.elements();
        componentmodel.Component componentAux;
        while(elementsHash.hasMoreElements()){
            componentAux = elementsHash.nextElement();
            this.componentToComposer[i] = componentAux;
            i++;
        }
        
        @SuppressWarnings("unused")
		processing.ComponentComposer applComp;
        String componentName;
        componentName = compName.getText();
        applComp = new processing.ComponentComposer(componentName, this.componentToComposer, this.connectorToComposer);
    }
    
    /**
     *
     *
     */
    public void addCode(){
        String compName;
        String path;
        ColectorDTO colector;
        try{
            
            compName = componentComboBox.getSelectedItem().toString();
            currentEdit = compName;
            if(currentEdit.equals("Filter")){
                //codeEditTextArea.setText("Internal Component");
                javax.swing.JOptionPane.showMessageDialog(this, "Internal component, it's not editable!");
                this.defaultValues();
                return;
            }
            colector = allComponentsCollector.get(compName);
            path = colector.getComponent_path()+"\\"+compName;
            codeEditTextArea.setText(FileFactor.readFile(path));
        }catch (Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    /** This method is to add instrument in workplace in first time
     * @param _xPos is the position x for put component
     * @param _yPos is the position y for put component
     */
    private void addComponent(int _xPos, int _yPos) {
        if(!componentComboBox.getSelectedItem().toString().equals("< Choose type >"))
            addComponent(_xPos, _yPos, componentComboBox.getSelectedItem().toString(), null);
    }
    
    /** This method is to add instrument in workplace, can be used copy paste action
     * @param _xPos is the position x for put component
     * @param _yPos is the position y for put component
     * @param _instrumentToAddName is the name of component
     * @param _instrument is the component interface for workplace
     */
    private void addComponent(int _xPos, int _yPos, String _instrumentToAddName, ImagePanel _instrument) {
        ImagePanel imagePanel;
        ColectorDTO colector;
        String imageName;
        int width, height;
        
        try {
            // Collecting information for Colector
            colector  = allComponentsCollector.get(_instrumentToAddName);
            System.out.println(colector.getComponent_name());
            imageName = colector.getComponent_image();
            width     = colector.getComponent_image_width();
            height    = colector.getComponent_image_height();
            // colector.set ("HUEHUEHUEBRBR");
            // Set instrument for add
            if(_instrument == null){
                //imagePanel = new ImagePanel(_instrumentToAddName, imageName, _xPos, _yPos, width, height);
                // /Users/rlopes/Documents/workspace/bw-alla/src/repository/images/child/bargraph.gif
            	imagePanel = new ImagePanel(_instrumentToAddName,  imageName, _xPos, _yPos, width, height);
            } else {
                imagePanel = _instrument;
            }
            this.setComponentHash(colector);
            imagePanel.setName("hauahauhauahau");
            this.m_ImagePanel.put(_instrumentToAddName, imagePanel);
           
           
            
            imagePanel.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    bwPanelMousePressed(evt);
                }
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    bwPanelMouseReleased(evt);
                }
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    bwPanelMouseClicked(evt);
                }
            });
            
            imagePanel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
                public void mouseDragged(java.awt.event.MouseEvent evt) {
                    bwPanelMouseDragged(evt);
                }
            });
            
            
            
            bwPanel.add(imagePanel).setName("asdufhasu");
           
            m_SelectedObjects.addElement(imagePanel);
            m_SelectedObjects.elementAt(0).setName("HSAUHAUAHUSA");
            
            repaint();
            if(imagePanel.isEnabled() == true){
            	System.out.println("true");
            }
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in new instance: "+e);
        }
    }
    
    public void addConnector(){
        Object obj;
        ImagePanel compIn;
        ImagePanel compOut;
        LinePanel linePanel;
        
        componentmodel.Component componentModelIn;
        componentmodel.Component componentModelOut;
        componentmodel.Connector connectorModel;
        
        obj = evtBuffer[0].getSource();
        compOut = (ImagePanel)obj;
        
        obj = evtBuffer[1].getSource();
        compIn = (ImagePanel)obj;
        
        componentModelIn  = this.m_ComponentWork.get(compIn.getComponentName());
        componentModelOut = this.m_ComponentWork.get(compOut.getComponentName());
        connectorModel = new componentmodel.Connector(componentModelIn, componentModelOut);
        connectorVector.addElement(connectorModel);
        
        linePanel = new LinePanel(compIn, compOut);
        bwPanel.add(linePanel);
        compIn.setLine(linePanel);
        compOut.setLine(linePanel);
        repaint();
    }
    
    /**
     * This method is to set component hash object for generate application
     * @param _colector cointain description of all components
     */
    public void setComponentHash(ColectorDTO _colector){
        componentmodel.Component comp;
        String type;
        String name;
        String typeAppl;
        
        type     = _colector.getComponent_name();
        name     = type + (this.m_ComponentWork.size() + 1);
        typeAppl = _colector.getComponent_typeAppl();
        
        comp = new componentmodel.Component(type, name, typeAppl);
        this.m_ComponentWork.put(type, comp);
    }
    
    /** This method is to selct one object in workplace
     * @param _component is component selected
     * @param _mouseModifiers is the action from mouse
     */
    @SuppressWarnings("unused")
	private void select(ImagePanel _component, int _mouseModifiers) {
        select(_component, _mouseModifiers, true);
    }
    
    /** This method is to selct one object in workplace and drag rectangle,
     * can to select more than one elements
     * @param _component is component selected
     * @param mouseModifiers is the action from mouse
     * @param doRemove if is to remove or no
     */
    private void select(ImagePanel _component, int _mouseModifiers, boolean _doRemove) {
        boolean ctrlPressed = (_mouseModifiers & 0x2)!=0;
        
        // Check if ctrl button is pressed, for allow selct more than object
        if (ctrlPressed) {
            if (!m_SelectedObjects.contains(_component)) {
                m_SelectedObjects.addElement(_component);
            } else {
                m_SelectedObjects.removeElement(_component);
            }
        } else {
            if (_doRemove) {
                m_SelectedObjects.removeAllElements();
            }
            m_SelectedObjects.addElement(_component);
        }
        frameSelectedObjects();
    }
    
    /**
     * This method is to paint box in component selected
     */
    private void frameSelectedObjects() {
        
        ImagePanel   objectToFrame;
        Border       line;
        Border       firstLine;
        Border       empty;
        Hashtable<String, ImagePanel>    allObjects;
        Enumeration<ImagePanel>  e;
        
        // Inicialize variables
        line        = BorderFactory.createLineBorder(xorColor, 2);
        firstLine   = BorderFactory.createLineBorder(firstXorColor, 2);
        empty       = BorderFactory.createEmptyBorder();
        allObjects  = this.m_ImagePanel;
        
        for (e = allObjects.elements(); e.hasMoreElements();) {
            
            objectToFrame = e.nextElement();
            if (m_SelectedObjects.contains(objectToFrame)) {
                if (m_SelectedObjects.indexOf(objectToFrame)==0) {
                    objectToFrame.setBorder(firstLine);
                } else {
                    objectToFrame.setBorder(line);
                }
            } else {
                objectToFrame.setBorder(empty);
            }
        }
    }
    
    /**
     * This method is to allow moving in component selected
     * @param rects is the last position in rectangle for component in workplace
     */
    @SuppressWarnings("unused")
	private void moveSelectedObjects(Rectangle[] _rects) {
        moveSelectedObjects(0, 0, _rects);
    }
    
    /** This method is to allow moving in component selected
     * @param dx is the new value for x position
     * @param dy is the new value for y position
     * @param rects is the last position in rectangle for component in workplace
     */
    private void moveSelectedObjects(int _dx, int _dy, Rectangle[] _rects) {
        ImagePanel objectToMove;
        LinePanel linePanel;
        Vector<?> lines;
        int size, size2;
        
        size = m_SelectedObjects.size();
        
        for (int i = 0; i < size; i++) {
            objectToMove = m_SelectedObjects.elementAt(i);
            if (objectToMove != bwPanel) {
                objectToMove.setX(_rects[i].x + _dx);
                objectToMove.setY(_rects[i].y + _dy);
                
                lines = objectToMove.getLine();
                size2 = lines.size();
                for(int j = 0; j < size2; j++){
                    linePanel = (LinePanel)lines.get(j);
                    linePanel.setImagePanel(objectToMove);
                }
            }
        }
        repaint();
    }
    
    /** This method is to get bounds of component in workplace
     * for to use in rectangle for around component
     * @return the rectangle for draw in component
     */
    private Rectangle[] setRectsToDraw() {
        int size;
        int i;
        
        Rectangle[] m_RectsToDraw;
        ImagePanel objc;
        
        size          = m_SelectedObjects.size();
        m_RectsToDraw = new Rectangle[size];
        for (i = 0; i < size; i++) {
            objc = m_SelectedObjects.elementAt(i);
            m_RectsToDraw[i] = objc.getBounds();
        }
        
        return m_RectsToDraw;
    }
    
    /** This method is to paint one rectangle in object (component) selected
     * in workplace, for to be moved, for example.
     * @param _dx the x position
     * @param _dy the y position
     * @param _rects is the rect to draw
     * @param _g is the graphic where the rect is traced
     * @throws Exception to draw erro
     */
    private void drawRects(int _dx, int _dy, Rectangle[] _rects, Graphics _g) throws Exception {
        for (int i = 0; i < _rects.length; i++) {
            _g.drawRect(_rects[i].x + _dx, _rects[i].y + _dy,
            _rects[i].width-1,
            _rects[i].height-1);
        }
    }
    
    /** This method is to paint one rectangle in object (component) selected
     * in workplace, for to be moved, for example.
     * This method is one abstraction from main method.
     * @param _rects is the rect to draw
     * @param _g is the graphic where the rect is traced
     */
    private void drawRects(Rectangle[] _rects, Graphics _g) {
        try{
            drawRects(0, 0, _rects, _g);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    /** This method is main from BeanWatcher interface
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new WorkPlace().setVisible(true);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutHelpMenuItem;
    private javax.swing.JButton addComponentButton;
    private javax.swing.JPanel applChoosePanel;
    private javax.swing.JPanel application;
    private javax.swing.JRadioButton applicationRadioButton;
    private javax.swing.JPanel bwPanel;
    private javax.swing.JTextArea codeEditTextArea;
    private javax.swing.JScrollPane codeScrollPane;
    private javax.swing.JTextField compName;
    private javax.swing.JComboBox<String> componentComboBox;
    private javax.swing.JScrollPane componentScrollPane;
    private javax.swing.JButton connectorButton;
    private javax.swing.JMenuItem exitFileMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JButton generateCode;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JButton moveComponentButton;
    private javax.swing.JMenuItem newFileMenuItem;
    private javax.swing.JMenuItem openFileMenuItem;
    private javax.swing.JButton propertiesButton;
    private javax.swing.JButton removeComponentButton;
    private javax.swing.JMenuItem saveAsFileMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JPanel simpleComponent;
    private javax.swing.JRadioButton simpleRadioButton;
    private javax.swing.JPanel toolPanel;
    private javax.swing.JButton wizardButton;
    // End of variables declaration//GEN-END:variables
}