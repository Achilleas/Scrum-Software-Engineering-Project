package webpageOut;

import java.io.PrintWriter;
import java.util.ArrayList;

import main.Address;
import main.Investor;

//Writes HTML showing user information
public class ProfileHTML extends WriteOut{
	
	public ProfileHTML(PrintWriter out, Investor ip){
		this.out=out;
		//Set title of HTML output
		this.title="Investor Profile";
		writeProfile(ip);
	}

	//Writes HTML
	public void writeProfile(Investor ip){
		htmlStart();
		if(ip!=null)
			writeDetails(ip);
		htmlEnd();
	}
	
	//Writes out all non-empty fields of Address
	public void printAddress(Address add){
		for(int i=0; i<6; i++){
			if(!add.getString()[i].isEmpty()&&add.getString()[i]!=""){
				out.write(add.getString()[i]+"<br />");
			}
			if(i==5) out.write("</p>");
		}
	}
	
	//Prints list of companies invested / interested in
	public void printList(ArrayList<String> list){
		for(int i=0; i<list.size();i++){
			if(i==0) out.write("<p>");
			out.write(list.get(i)+"<br />");
			if(i==(list.size()-1)) out.write("</p>");
		}
	}
	
	public void writeDetails(Investor ip){
		out.write("<h1> Investor Profile - "+ip.getUsername()+"</h1>");
		out.write("<p><b>Name: </b>"+ip.getFirstName()+"<br />");
		out.write("<b>Surname: </b>"+ip.getSurname()+"<br />");
		out.write("<b>Date of Birth: </b>"+ip.getDateOfBirth().toString()+"</p>");
		out.write("<h3><u>Contact Info</u></h3>");
		out.write("<p><b>E-mail Address: </b>"+ip.getEmail()+"<br />");
		out.write("<b>Home Telephone: </b>"+ip.getTelephone()+"</p>");
		out.write("<p><b>Address</b><br />");
		printAddress(ip.getAddress());
		out.write("<h3><u>Companies Invested In</u></h3>");
		printList(ip.getCompaniesInvested());
		out.write("<h3><u>Companies Interested In</u></h3>");
		printList(ip.getCompaniesInterested());
	}
	
}
