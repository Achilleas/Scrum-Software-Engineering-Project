package main;

import java.util.ArrayList;

import org.apache.commons.lang3.Validate;


public class InvestorProfile {
	
	private String firstName;
	private String surname;
	private ArrayList companiesInvested;
	private ArrayList companiesInterested;
	private String username;
	private String password;
	
	public InvestorProfile(String firstName, String surname, ArrayList companiesInvested, ArrayList companiesInterested) {
		
		Validate.notNull(firstName, "firstName can't be null");
		Validate.notNull(surname, "surname can't be null");
		//Validate.notNull(username, "username can't be null");
		//Validate.notNull(password, "password can't be null");
		
		this.firstName = firstName;
		this.surname = surname;
		this.companiesInterested = companiesInterested;
		this.companiesInvested = companiesInvested;
		//this.username = username;
		//this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}
	
}
