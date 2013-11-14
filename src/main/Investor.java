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
	public Investor(String firstname,String surname,String password){
		this.firstName=firstname;
		this.surname=surname;
		this.password=password;
		this.companiesInterested=new ArrayList<String>();
		this.companiesInvested=new ArrayList<String>();
	}
	public Investor(String firstname,String surname,String username,String password){
		this.firstName=firstname;
		this.surname=surname;
		this.username=username;
		this.password=password;
		this.companiesInterested=new ArrayList<String>();
		this.companiesInvested=new ArrayList<String>();
	}
	public Investor(String firstname,String surname,String username,String password,ArrayList<String> companiesInvested,
			ArrayList<String> companiesInterested){
		this.firstName=firstname;
		this.username=username;
		this.password=password;
		this.surname=surname;
		this.companiesInterested=companiesInterested;
		this.companiesInvested=companiesInvested;
	}
	/**
	 * 
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

	public Investor(String username, String password, LocalDate dateOfBirth,
			String firstName, String surname, String email, String telephone,
			Address address) {
		this(username, password, dateOfBirth, firstName,
				surname, email, telephone, address, null, null);
	}

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

	public boolean changePassword(String old_password, String new_password) {
		if (verifyPassword(old_password)) {
			this.password = new_password;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Update
	 * 2013/11/11	Jia Heng	
	 * 		-Uses Collection's method
	 * 
	 * @param stock
	 */
	public void addInterested(String stock) {
		if (!isInterested(stock)) {
			companiesInterested.add(stock);
		}
	}

	public void addInvested(String stock) {
		if (!isInvested(stock)) {
			companiesInvested.add(stock);
		}
	}

	public boolean isInterested(String stock) {
		return companiesInterested.contains(stock);
	}

	public boolean isInvested(String stock) {
		return companiesInvested.contains(stock);
	}

	public ArrayList<String> getInterestList() {
		return companiesInterested;
	}

	public ArrayList<String> getInvestedList() {
		return companiesInterested;
	}
}
