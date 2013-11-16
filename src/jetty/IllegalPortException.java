package jetty;

/**
 * @author cl72
 * Based on code from Graham Kirby's Servlet lecture
 * Prints out a message when this class is instantiated
 */
public class IllegalPortException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7932701055252857374L;

	public IllegalPortException(String message) {
		super(message);
	}
}
