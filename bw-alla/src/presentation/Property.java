// Imports
package presentation;

import java.awt.*;
import java.util.*;
//import javax.swing.*;
//import javax.swing.table.*;
import br.ufpi.die.jflash.property.Properties;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JPanel;


class Property extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Instance attributes used in this example
    private	JPanel		topPanel;
    private	JTable		table;
    private	JScrollPane scrollPane;
    
    private	String		columnNames[];
    private	String		dataValues[][];
    
    @SuppressWarnings("unused")
	private String componentName;
    Vector<Object> prptValues = new Vector<Object>();
    
    
    // Constructor of main frame
    public Property(String _title, Vector<Object> _valuesVect) {
        // Set the frame characteristics
        setTitle(_title);
        setSize( 300, 200 );
        setBackground( Color.gray );
        
        // for atualize components properties when closing this frame
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        componentName = _title;
        prptValues    = _valuesVect;
        // Create a panel to hold all other components
        topPanel = new JPanel();
        topPanel.setLayout( new BorderLayout() );
        getContentPane().add( topPanel );
        
        // Create columns
        createColumns();
        setValues();
        
        // Create a new table instance
        table = new JTable( dataValues, columnNames );
        
        // Configure some of JTable's paramters
        table.setShowHorizontalLines( true );
        table.setRowSelectionAllowed( true );
        table.setColumnSelectionAllowed( true );
        
        // Change the selection colour
        table.setSelectionForeground( Color.white );
        table.setSelectionBackground( Color.red );
        
        // Add the table to a scrolling pane
        //scrollPane = table.createScrollPaneForTable( table );
        scrollPane = new JScrollPane(table);
        topPanel.add( scrollPane, BorderLayout.CENTER );
    }
    
    private void createColumns() {
        // Create column string labels
        columnNames = new String[3];
        columnNames[0] = "Property";
        columnNames[1] = "Type";
        columnNames[2] = "Value";
    }
    
    public void setValues(){
        String propertiesNames;
        String propertiesValues;
        String propertiesType;
        Properties prop;
        @SuppressWarnings("unused")
		Vector<?> propVector;
        
        dataValues = new String[prptValues.size()][3];
        for (int i = 0; i < prptValues.size(); i++){
            prop = (Properties)prptValues.get(i);
            propertiesNames  = prop.getName();
            propertiesValues = prop.getValue();
            propertiesType   = prop.getType();
            dataValues[i][0] = propertiesNames;
            dataValues[i][1] = propertiesType;
            dataValues[i][2] = propertiesValues;
        }
    }
 
    public String[][] getValues(){
        return dataValues;
    }
    
    private void exitForm(java.awt.event.WindowEvent evt) {
        setVisible( false );
    }
}