/*
 * ComponentFactoryTest.java
 *
 * Created on October 7, 2003, 5:05 PM
 */

package test;

import processing.ComponentFactory;

/**
 *
 * @author  alla
 */
public class ComponentFactoryTest {
    
    public static void testUnit() {
        String code;
        ComponentFactory compFactory = new ComponentFactory("DataFusion");
        
        code = compFactory.loadComponent();
        code = "/*teste de implementaçao*/\n" + code;
        
        compFactory.saveComponent(code);
        
        System.out.println("ComponentFactoryTest ok!!!");
    }
    
}
