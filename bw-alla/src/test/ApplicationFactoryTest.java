/*
 * ApplicationFactoryTest.java
 *
 * Created on October 1, 2003, 8:01 PM
 */

package test;

import componentmodel.Component;
import processing.*;

/**
 *
 * @author  alla
 */
public class ApplicationFactoryTest {
    
    public static void testUnit() {
        @SuppressWarnings("unused")
		ApplicationFactory applFactor;
        Component filter = new Component("Filter", "filter", Component.FUNCTIONAL);
        Component comp[] = new Component[8];
        String friendlyFusion[] = new String[1];
        friendlyFusion[0] = "MarzulloDataFusion";
        
        comp[0] = new Component("RssfThermometer", "thermometer", Component.VISUAL);
        comp[1] = new Component("DataFusion", "fusion", Component.FUNCTIONAL);
        comp[2] = new Component("DataFusion", "fusion222", Component.FUNCTIONAL);
        comp[3] = new Component("DataFusion", "fusion333", Component.FUNCTIONAL);
        comp[4] = new Component("DataFusion", "fusion444", Component.FUNCTIONAL);
        
        comp[0].setDependence(comp[1]);
        comp[1].setDependence(comp[2]);
        comp[2].setDependence(comp[3]);
        comp[3].setDependence(comp[4]);
        comp[4].setDependence(filter);
        
        comp[5] = new Component("RssfBarometer", "barometer", Component.VISUAL);
        comp[6] = new Component("DataFusion", "fusion666", Component.FUNCTIONAL);
        comp[7] = new Component("DataFusion", "fusion777", Component.FUNCTIONAL);
        
        comp[5].setDependence(comp[6]);
        comp[6].setDependence(comp[7]);
        comp[7].setDependence(filter);
        
        applFactor = new ApplicationFactory(comp);
        //applFactor.createApplication();
        System.out.println("ApplicationFactory ok!!!");
    }
}
