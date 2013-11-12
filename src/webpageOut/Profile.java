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
			if(add.getString()[i]!=""){
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
		out.write("<p><b>Name: </b>"+ip.getFirstName()+"</p>");
		out.write("<p><b>Surname: </b>"+ip.getSurname()+"</p>");
		out.write("<p><b>Date of Birth: </b>"+ip.getDateOfBirth().toString()+"</p>");
		out.write("<h3><u>Contact Info</u></h3>");
		out.write("<p><b>E-mail Address: </b>"+ip.getEmail()+"</p>");
		out.write("<p><b>Home Telephone: </b>"+ip.getTelephone()+"</p>");
		out.write("<p><b>Address</b></p>");
		printAddress(ip.getAddress());
		/*out.write("<h3>Companies Interested In</h3>");
		printList(ip.getCompaniesInterested());
		out.write("<h3>Companies Invested In</h3>");
		printList(ip.getCompaniesInvested());*/
	}
	
}
