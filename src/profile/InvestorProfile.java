package profile;

public class InvestorProfile {
	
	private String firstname;
	private String surname;
	private String username;
	private String password;
	
	public InvestorProfile(String firstname, String surname, String username, String password){
		this.firstname=firstname;
		this.surname=surname;
		this.username=username;
		this.password=password;
	}
	
	public void storeAllDetails(){
		updateUsername();
		updatePassword();
		updateFirstname();
		updateSurname();
	}
	
	public void updateFirstname(){
		//TODO
	}
	
	public void updateSurname(){
		//TODO
	}
	
	public void updateUsername(){
		//TODO
	}
	
	public void updatePassword(){
		//TODO
	}
}
