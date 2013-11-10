package main;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.Validate;

public class Investor {

	private String username;
	private String password;
	private DOB dob;
	private String firstName;
	private String surname;
	private String email;
	private String telephone;
	private Address address;
	private ArrayList<String> companiesInvested;
	private ArrayList<String> companiesInterested;

	public Investor(String username, String password, DOB dob,
			String firstName, String surname, String email, String telephone,
			Address address, ArrayList<String> companiesInvested,
			ArrayList<String> companiesInterested) {
		this.username = username;
		this.password = password;
		this.dob = dob;
		this.firstName = firstName;
		this.surname = surname;
		this.email = email;
		this.telephone = telephone;
		this.address = address;
		this.companiesInvested = companiesInvested;
		this.companiesInterested = companiesInterested;
	}

	public Investor(String username2, String password2, String firstName2,
			String surname2, ArrayList<String> companiesInvested2,
			ArrayList<String> companiesInterested2) {
		// TODO Auto-generated constructor stub
	}

	public Investor(String username2, String password2, String firstName2, String surname2) {
		// TODO Auto-generated constructor stub
	}

	public Investor(String firstName, String surname, String password2) {

		Validate.notNull(firstName, "firstName can't be null");
		Validate.notNull(surname, "surname can't be null");
		// Validate.notNull(username, "username can't be null");
		// Validate.notNull(password, "password can't be null");

		this.firstName = firstName;
		this.surname = surname;
		this.companiesInterested = new ArrayList<String>();
		this.companiesInvested = new ArrayList<String>();
		// this.username = username;
		// this.password = password;
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

	public DOB getDob() {
		return dob;
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

	public void setDob(DOB dob) {
		this.dob = dob;
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

	public void addInterested(String stock) {
		if (!isStockList(stock, companiesInterested)) {
			companiesInterested.add(stock);
		}
	}

	public void addInvested(String stock) {
		if (!isStockList(stock, companiesInvested)) {
			companiesInvested.add(stock);
		}
	}

	public boolean isInterested(String stock) {
		return isStockList(stock, companiesInterested);
	}

	public boolean isInvested(String stock) {
		return isStockList(stock, companiesInvested);
	}

	public ArrayList<String> getInterestList() {
		return companiesInterested;
	}

	public ArrayList<String> getInvestedList() {
		return companiesInterested;
	}

	private static boolean isStockList(String stock, ArrayList<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (stock.equals(list.get(i))) {
				return true;
			}
		}
		return false;
	}
}
