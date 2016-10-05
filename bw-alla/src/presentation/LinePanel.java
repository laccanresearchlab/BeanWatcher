/*
 * LinePanel.java
 *
 * Created on October 28, 2003, 10:39 AM
 */

package presentation;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author  alla
 */
public class LinePanel extends JPanel{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	int x1, y1;
    int x2, y2;
    ImagePanel inImage;
    ImagePanel outImage;
    
    /** Creates a new instance of LinePanel */
    public LinePanel(int _x1, int _y1, int _x2, int _y2) {
        x1 = _x1;
        y1 = _y1;
        x2 = _x2;
        y2 = _y2;
    }
    
    /** Creates a new instance of LinePanel */
    public LinePanel(ImagePanel _in, ImagePanel _out) {
        inImage  = _in;
        outImage = _out;
        x1 = inImage.getX() + (inImage.getWidth()/2);
        y1 = inImage.getY() + (inImage.getHeight()/2);
        x2 = outImage.getX()+ (outImage.getWidth()/2);
        y2 = outImage.getY()+ (outImage.getHeight()/2);
    }

    public void setImagePanel(ImagePanel _in, ImagePanel _out){
        inImage  = _in;
        outImage = _out;
        x1 = inImage.getX() + (inImage.getWidth()/2);
        y1 = inImage.getY() + (inImage.getHeight()/2);
        x2 = outImage.getX()+ (outImage.getWidth()/2);
        y2 = outImage.getY()+ (outImage.getHeight()/2);
    }
    
    public void setImagePanel(ImagePanel _image){
        //if(_image.equals(inImage)){
        if(_image.equals(inImage)){
            inImage  = _image;
            x1 = inImage.getX() + (inImage.getWidth()/2);
            y1 = inImage.getY() + (inImage.getHeight()/2);
        } else if(_image.equals(outImage)){
            outImage  = _image;
            x2 = outImage.getX()+ (outImage.getWidth()/2);
            y2 = outImage.getY()+ (outImage.getHeight()/2);
        }
    }
    
    /***/
    public void paint(Graphics g){
        int sx;
        int sy;
        
        // Trace shape for paint element
        if(x1 > x2){
            sx = x1;
        } else {
            sx = x2;
        }
        if(y1 > y2){
            sy = y1;
        } else {
            sy = y2;
        }
        
        this.setBackground(Color.WHITE);
        this.setBounds(0,0 , sx, sy);
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
        //g.drawString("> > >", (x1+x2)/2, (y1+y2)/2);
    }
}