package webpageOut;

import java.io.PrintWriter;

import main.Investor;
import profile.InvestorProfile;

public class Profile extends WriteOut{
	
	public Profile(PrintWriter out, Investor ip){
		this.out=out;
		writeProfile(ip);
	}

	public void writeProfile(Investor ip){
		htmlStart();
		if(ip!=null)
			writeDetails(ip);
		htmlEnd();
		out.close();
	}
	
	public void writeDetails(Investor ip){
		out.write("<h1> Investor Profile - "+ip.getUsername()+"</h1>");
		out.write("<p>Name: "+ip.getFirstName()+"</p>");
		out.write("<p>Surname: "+ip.getSurname()+"</p>");
	}
	
}
