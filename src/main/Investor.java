package main;

import java.util.ArrayList;

import org.apache.commons.lang3.Validate;


public class Investor {
	
	private String firstName;
	private String surname;
	private ArrayList<String> companiesInvested;
	private ArrayList<String> companiesInterested;
	private String username;
	private String password;

	public Investor(String firstName2, String surname2, String username2,
			String password2, ArrayList<String> companiesInvested2,
			ArrayList<String> companiesInterested2) {
		// TODO Auto-generated constructor stub
	}

	public Investor(String firstName2, String surname2, String username2,
			String password2) {
		// TODO Auto-generated constructor stub
	}

	public Investor(String firstName, String surname,
			String password2) {
		
		Validate.notNull(firstName, "firstName can't be null");
		Validate.notNull(surname, "surname can't be null");
		//Validate.notNull(username, "username can't be null");
		//Validate.notNull(password, "password can't be null");
		
		this.firstName = firstName;
		this.surname = surname;
		this.companiesInterested = new ArrayList<String>();
		this.companiesInvested = new ArrayList<String>();
		//this.username = username;
		//this.password = password;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public String getSurname() {
		return surname;
	}
	/**
	 * -------------------------------------------------------------------------
	 * @author Qiao
	 * -------------------------------------------------------------------------
	 * @param password
	 * @return if change is made
	 */
	public boolean verifyPassword(String password){
		return password.equals(this.password);
	}
	public boolean changePassword(String old_password,String new_password){
		if(verifyPassword(old_password)){
			this.password=new_password;
			return true;
		}else{
			return false;
		}
	}
	public void addInterested(String stock){
		if(!isStockList(stock,companiesInterested)){
			companiesInterested.add(stock);
		}
	}
	public void addInvested(String stock){
		if(!isStockList(stock,companiesInvested)){
			companiesInvested.add(stock);
		}
	}
	public boolean isInterested(String stock){
		return isStockList(stock,companiesInterested);
	}
	public boolean isInvested(String stock){
		return isStockList(stock,companiesInvested);
	}
	public ArrayList<String> getInterestList(){
		return companiesInterested;
	}
	public ArrayList<String> getInvestedList(){
		return companiesInterested;
	}
	private static boolean isStockList(String stock,ArrayList<String> list){
		for(int i=0;i<list.size();i++){
			if(stock.equals(list.get(i))){
				return true;
			}
		}
		return false;
	}
}
