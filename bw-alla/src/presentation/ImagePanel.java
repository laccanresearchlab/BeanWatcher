/*
 * ImagePanel.java
 *
 * Created on October 8, 2003, 4:53 PM
 */






//O PROBLEMA ESTÁ NESSA CLASSE
package presentation;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Vector;

/**
 *
 * @author  alla
 */
public class ImagePanel extends JPanel {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** Variable for name of the component*/
    private String componentName;
    /** Variable for image panel*/
    private Image image;
    /** Variable for heigth of panel*/
    private int height;
    /** Variable for width of panel*/
    private int width;
    /** Variable for position 'X' of panel*/
    private int positionX;
    /** Variable for position 'Y' of panel*/
    private int positionY;
    /**Variable for check if there is image*/
    @SuppressWarnings("unused")
	private boolean hasImage;
    /**/
    //private LinePanel linePanel;
    private Vector<LinePanel> linePanelvect = new Vector<LinePanel>();
    
    /**
     * Constructor for Image panel object, from image object
     * @param _componentName is the component's name
     * @param _image is the object image
     * @param _x is the X position
     * @param _y is the Y position
     * @param _width is the width of panel
     * @param _height is the height of panel
     */
    public ImagePanel(String _componentName, Image _image, int _x, int _y, int _width, int _height){
        this.componentName = _componentName;
        this.image = _image;
        this.positionX = _x;
        this.positionY = _y;
        this.width     = _width;
        this.height    = _height;
        this.hasImage  = true;
    }
    
    /**
     * Constructor for Image panel object, from image path
     * @param _componentName is the component's name
     * @param _imagePath is the path of image
     * @param _x is the X position
     * @param _y is the Y position
     * @param _width is the width of panel
     * @param _height is the height of panel
     */
    public ImagePanel(String _componentName, String _imagePath, int _x, int _y, 
                      int _width, int _height){
        try{
            String imageName = "file:///" + _imagePath;
            System.out.println(imageName);
            URL iconURL      = new URL(imageName);
            ImageIcon icon   = new ImageIcon(iconURL);
            this.componentName = _componentName;
            this.image = icon.getImage();
            
            this.positionX = _x;
            this.positionY = _y;
            this.width     = _width;
            this.height    = _height;
            this.hasImage  = true;
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    /**
     *
     *
     */
    public void setLine(LinePanel _line){
        linePanelvect.add(_line);
        //linePanel = _line;
    }
    
    /**
     *
     *
     */
    public Vector<LinePanel> getLine(){
        return linePanelvect;
    }
    
    /**
     * This method is to get component's name
     * @return the value for components name
     */
    public String getComponentName(){
        return componentName;
    }
    
    /**
     * This method is to set width value
     * @param _width is the new value for atribute width
     */
    public void setWidthLocal(int _width){
        this.width = _width;
    }
    
    /**
     * This method is to get width value
     * @return the value for atribute width
     */
    public int getWidthLocal(){
        return this.width;
    }
    
    /**
     * This method is to set heigth value
     * @param _height is the new value for atribute heigth
     */
    public void setHeightLocal(int _height){
        this.height = _height;
    }
    
    /**
     * This method is to get heigth value
     * @return the value for atribute heigth
     */
    public int getHeightLocal(){
        return this.height;
    }
    
    /**
     * This method is to set positionX value
     * @param _valueX is the new value for atribute positionX
     */
    public void setX(int _valueX){
        this.positionX = _valueX;
    }
    
    /**
     * This method is to get position X value
     * @return the value for atribute positionX
     */
    public int getX(){
        return this.positionX;
    }
    
    /**
     * This method is to set positionY value
     * @param _valueY is the new value for atribute positionY
     */
    public void setY(int _valueY){
        this.positionY = _valueY;
    }
    
    /**
     * This method is to get position Y value
     * @return the value for atribute positionY
     */
    public int getY(){
        return this.positionY;
    }
    
    /**
     * The default paint method
     */
    public void paint( Graphics g ) {
        int x, y;
		int width, heigth;
        
        x = getX();
        y = getY();
        width  = getWidthLocal();
        height = getHeightLocal();
        
        if ( this.image == null ) {
            g.setColor(this.getBackground());
            System.out.println("ëntrou");
            g.fillRect(x, y, width, height);
        } else {
            //this.reshape(x, y, width, height);
        	this.setBounds(x, y, width, height);
        	//g.drawRect(0, 0, 10, 10);
        	//g.setColor(Color.BLACK);
        	//System.out.println(this.getWidth());
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            
        }
    }
}