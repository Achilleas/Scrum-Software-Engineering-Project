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
		//TODO store new firstname to file
	}
	
	public void updateSurname(){
		//TODO store new surname to file
	}
	
	public void updateUsername(){
		//TODO store new username to file
	}
	
	public void updatePassword(){
		//TODO store new password to file
	}
	
	public String getFirstname() {
		return firstname;
	}

	public String getSurname() {
		return surname;
	}

	public String getUsername() {
		return username;
	}
}
