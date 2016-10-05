/*
 * BeanWatcherException.java
 *
 * Created on September 30, 2003, 3:18 PM
 */

package bwexception;

/**
 *
 * @author  alla
 */
public class BeanWatcherException extends Exception {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** Creates a new instance of BeanWatcherException */
    public BeanWatcherException() {
        super (" Exception in BeanWatcher class");
    }
    
    public BeanWatcherException(String _message) {
        super (_message);
    }
}
