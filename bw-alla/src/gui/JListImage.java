/*
 * JListImage.java
 *
 * Created on 17 de Junho de 2003, 01:15
 */
package gui;
import javax.swing.*;
import java.awt.*;
import java.util.*;
/**
 *
 * @author  Lincoln Souza Rocha
 */

@SuppressWarnings("unused")
class JListImage extends JList<Object>{	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JListImage(){
            this.setCellRenderer(new CustomCellRenderer());		

	}   

        class CustomCellRenderer implements ListCellRenderer<Object> {
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,boolean cellHasFocus) {
                Component component = (Component)value;
                component.setBackground(isSelected ? Color.RED : Color.WHITE);
                component.setForeground(isSelected ? Color.BLUE : Color.BLACK);
                return component;
           }
       }
}

