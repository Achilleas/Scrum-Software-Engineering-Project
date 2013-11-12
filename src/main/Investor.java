package main;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.Validate;

public class Investor {

	private String username;
	private String password;
	private Date dateOfBirth;
	private String firstName;
	private String surname;
	private String email;
	private String telephone;
	private Address address;
	private ArrayList<String> companiesInvested;
	private ArrayList<String> companiesInterested;

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
	public Investor(String username, String password, Date dateOfBirth,
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

	public Investor(String username, String password, Date dateOfBirth,
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

	public Date getDateOfBirth() {
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

	public void setDob(Date dateOfBirth) {
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
