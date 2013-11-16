package webpageOut;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author cl72
 * Used to display an error message
 */
public class CreateError extends WriteOut{
	
	/**
	 * Constructor for the class
	 * @param out the location of where to write the HTML file
	 * @throws IOException 
	 */
	public CreateError(PrintWriter out, String message) throws IOException{
		this.out = out;
		writeErrorMessage(message);
	}
	
	/**
	 * Writes the message to the client
	 * @param message the error message that is to be displayed
	 * @throws IOException 
	 */
	public void writeErrorMessage(String message) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader("./WebRoot/static/register.html"));
		String value = reader.readLine();
		while(value!=null){
			String a = "\t"+"<link rel=\"stylesheet\" type=\"text/css\" href=\"Style.css\" />";
			System.out.println(value);
			if(value.equals(a)){
				System.out.println("here!");
				BufferedReader reader2 = new BufferedReader(new FileReader("./WebRoot/static/Style.css"));
				String value2 = reader2.readLine();
				while(value2!=null){
					out.write(value2+"\n");
					value2 = reader2.readLine();
				}
				reader2.close();
			}
			//	out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"./WebRoot/static/Style.css\" />");
			//else
			else
				out.write(value);
			if(value.equals("<body>"))
				out.write("<h1>ERROR WITH SUPPLIED DETAILS</h1>");
			value = reader.readLine();			
		}
		
		//htmlStart(); //method in the WriteOut class
		//printMessage(message); 
		//htmlEnd(); //method in the WriteOut class
		out.close(); //close the data stream
		reader.close();
	}
	
	/**
	 * Prints out the error message in the HTML file
	 * @param message the error message that is to be displayed
	 */
	public void printMessage(String message){
		out.println("<p>"+message+"</p>");
	}
	
}
