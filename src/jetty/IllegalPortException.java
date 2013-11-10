package jetty;

/**
 * @author cl72
 * Based on code from Graham Kirby's Servlet lecture
 * Prints out a message when this class is instantiated
 */
public class IllegalPortException extends Exception {

	public IllegalPortException(String message) {
		super(message);
	}
}
