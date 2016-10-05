/*
 * ApplicationFactoryTest.java
 *
 * Created on October 1, 2003, 8:01 PM
 */

package test;

import componentmodel.*;
import processing.*;

/**
 *
 * @author  alla
 */
public class ApplicationComposerTest {
    
    public static void testUnit() {
		@SuppressWarnings("unused")
		ApplicationComposer applComp;
        Component filter = new Component("Filter", "filter", Component.FUNCTIONAL);
        Component comp[] = new Component[4];
        Connector conector[] = new Connector[4];
        //Port portDefault = new Port("Default", "int");
        
        comp[0] = new Component("RssfThermometer", "thermometer", Component.VISUAL);
        comp[1] = new Component("DataFusion", "fusion", Component.FUNCTIONAL);
        comp[2] = new Component("RssfBarometer", "barometer", Component.VISUAL);
        comp[3] = new Component("DataFusion", "fusion222", Component.FUNCTIONAL);
        try{
            conector[0] = new Connector(comp[0],  comp[1] );
            conector[1] = new Connector(comp[1],  filter );
            conector[2] = new Connector(comp[2],  comp[3] );
            conector[3] = new Connector(comp[3],  filter );
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        
        applComp = new ApplicationComposer(comp, conector);
        System.out.println("ApplicationComposer ok!!!");
    }
}
