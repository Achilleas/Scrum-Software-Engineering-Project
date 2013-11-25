package webpageOut;

import java.io.PrintWriter;

/**
 * @author cl72
 * Used to display an error message
 */
public class DisplayError extends WriteOut{
	
	String errorMessage;
	
	/**
	 * Constructor for the class
	 * @param out the location of where to write the HTML file
	 * @throws IOException 
	 */
	public DisplayError(PrintWriter out, String message){
		this.out = out;
		errorMessage = message;
	}
	
	/**
	 * Writes the error message to the user
	 */
	public void writeErrorMessage(){
		htmlStart();
		out.println("<h1>ERROR: "+errorMessage+"</h1>");
		htmlEnd();
	}
	
	
}
