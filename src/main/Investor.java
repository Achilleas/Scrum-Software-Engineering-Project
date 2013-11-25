package main;

import java.util.ArrayList;
import org.apache.commons.lang3.Validate;
import org.joda.time.LocalDate;

public class Investor {

	public static final int FIRSTNAME=0;
	public static final int SURNAME=1;
	public static final int INVESTEDED=2;
	public static final int INTERESTED=3;
	public static final int EMAIL=4;
	public static final int PASSWORD = 5;
	public static final int DATEOFBIRTH=6;
	public static final int ADDRESS=7;
	public static final int TELEPHONE=8;
	public static final int USERNAME=9;
	private String username;
	private String password;
	private LocalDate dateOfBirth;
	private String firstName;
	private String surname;
	private String email;
	private String telephone;
	private Address address;
	private ArrayList<String> companiesInvested;
	private ArrayList<String> companiesInterested;
	/**
	 * @author Qiao
	 * New Investor
	 * Build an investor from scratch
	 */
	public Investor(){
		this.password = ""; //DO NOT REMOVE - IMPORTANT FOR PASSWORD CHANGE & PROFILE WRITER!!
		this.companiesInterested=new ArrayList<String>();
		this.companiesInvested=new ArrayList<String>();
	}
	/**
	 * Investor constructor
	 * Update:
	 * 2013/11/11	Jia Heng
	 * 		- Modify constructor to pass the test]
	 * 		- change Date Class
	 * 
	 * @param username
	 * @param password
	 * @param dateOfBirth
	 * @param firstName
	 * @param surname
	 * @param email
	 * @param telephone
	 * @param address
	 * @param companiesInvested
	 * @param companiesInterested
	 */
	public Investor(String username, String password, LocalDate dateOfBirth,
			String firstName, String surname, String email, String telephone,
			Address address, ArrayList<String> companiesInvested,
			ArrayList<String> companiesInterested) {
		
		Validate.notNull(firstName, "firstName can't be null");
		Validate.notNull(surname, "surname can't be null");
		Validate.notNull(username, "username can't be null");
		
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.firstName = firstName;
		this.surname = surname;
		this.email = email;
		this.telephone = telephone;
		this.address = address;
		this.companiesInvested = (companiesInvested==null)?
				new ArrayList<String>() : companiesInvested;
		this.companiesInterested = (companiesInterested==null)?
				new ArrayList<String>() : companiesInterested;
	}
	
	//INVESTOR GETTERS
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public String getTelephone() {
		return telephone;
	}

	public Address getAddress() {
		return address;
	}

	public ArrayList<String> getCompaniesInvested() {
		return companiesInvested;
	}

	public ArrayList<String> getCompaniesInterested() {
		return companiesInterested;
	}
	//END OF INVESTOR GETTERS
	
	//INVESTOR SETTERS
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDob(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	//END OF INVESTOR SETTERS
	
	/**
	 * -------------------------------------------------------------------------
	 * 
	 * @author Qiao
	 *         --------------------------------------------------------------
	 *         -----------
	 * @param password
	 * @return if change is made
	 */
	public boolean verifyPassword(String password) {
		return password.equals(this.password);
	}

	/**
	 * Changes password if old password is verified
	 * @param old_password the old password of the user
	 * @param new_password the new password the user wants to have
	 * @return returns true if old password was verified and a new password exists, 
	 * otherwise false
	 */
	public boolean changePassword(String old_password, String new_password) {
		if (verifyPassword(old_password) && new_password != null) {
			this.password = new_password;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Add another company to the user's list of interested companies
	 * Update
	 * 2013/11/11	Jia Heng	
	 * 		-Uses Collection's method
	 * 
	 * @param stock the stock of that company
	 */
	public void addInterested(String stock) {
		if (!isInterested(stock)) {
			companiesInterested.add(stock);
		}
	}
	
	/**
	 * Add company to the user's list of invested companies
	 * @param stock the sotck of that company
	 */
	public void addInvested(String stock) {
		if (!isInvested(stock)) {
			companiesInvested.add(stock);
		}
	}

	/**
	 * Determines if the user is interested in said stock
	 * @param stock the stock
	 * @return returns true if user is interested, otherwise false
	 */
	public boolean isInterested(String stock) {
		return companiesInterested.contains(stock);
	}

	/**
	 * Determines if the user is investing in said stock
	 * @param stock the stock
	 * @return returns true if user is investing on it, otherwise false
	 */
	public boolean isInvested(String stock) {
		return companiesInvested.contains(stock);
	}

	/**
	 * @return returns an arraylist of all the companies the investor is interested
	 */
	public ArrayList<String> getInterestList() {
		return companiesInterested;
	}

	/**
	 * @return returns an arraylist of all the companies the investor is investing
	 */
	public ArrayList<String> getInvestedList() {
		return companiesInvested;
	}
}
