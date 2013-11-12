package webpageOut;

import java.io.PrintWriter;
import java.util.ArrayList;

import main.Address;
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
	
	public void printAddress(Address add){
		for(int i=0; i<6; i++){
			if(add.getString()[i]!=" "&&add.getString()[i]!=null){
				out.write("<p>"+add.getString()[i]+"</p>");
			}
		}
	}
	
	public void printList(ArrayList<String> list){
		for(int i=0; i<list.size();i++){
			out.write("<p>"+list.get(i)+"</p>");
		}
	}
	
	public void writeDetails(Investor ip){
		out.write("<h1> Investor Profile - "+ip.getUsername()+"</h1>");
		out.write("<p>Name: "+ip.getFirstName()+"</p>");
		out.write("<p>Surname: "+ip.getSurname()+"</p>");
		//out.write("<p>Date of Birth: "+ip.getDateOfBirth().toString()+"</p>");
		out.write("<h3>Contact Info</h3>");
		out.write("<p>E-mail Address: "+ip.getEmail()+"</p>");
		out.write("<p>Home Telephone: "+ip.getTelephone()+"</p>");
		/*out.write("<p>Address</p>");
		printAddress(ip.getAddress());
		out.write("<p>Companies Interested In");
		printList(ip.getCompaniesInterested());
		out.write("<p>Companies Invested In");
		printList(ip.getCompaniesInvested());*/
	}
	
}
