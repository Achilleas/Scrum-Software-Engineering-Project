package webpageOut;

import java.io.PrintWriter;

import main.Investor;

public class SessionHTML extends WriteOut{

	public SessionHTML(PrintWriter out){
		this.out=out;
		this.title = "Redirect...";
	}
	
	public void writeHTML(Investor ip){
		htmlStart();
		out.println("<h1>Already logged in as user: "+ip.getUsername()+"</h1>");
		htmlEnd();
		out.close();
	}
	
}
