package webpageOut;

import java.io.PrintWriter;

import profile.InvestorProfile;

public class Profile extends WriteOut{
	
	public Profile(PrintWriter out, InvestorProfile ip){
		this.out=out;
		writeProfile(ip);
	}

	public void writeProfile(InvestorProfile ip){
		htmlStart();
		if(ip!=null)
			writeDetails(ip);
		htmlEnd();
		out.close();
	}
	
	public void writeDetails(InvestorProfile ip){
		out.write("<h1> Investor Profile - "+ip.getUsername()+"</h1>");
		out.write("<p>Name: "+ip.getFirstname()+"</p>");
		out.write("<p>Surname: "+ip.getSurname()+"</p>");
	}
	
}
