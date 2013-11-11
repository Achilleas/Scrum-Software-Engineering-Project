package profile;

public class InvestorProfile {
	
	private String firstname;
	private String surname;
	private String username;
	private String password;
	
	public InvestorProfile(String username, String password, String firstname, String surname){
		this.firstname=firstname;
		this.surname=surname;
		this.username=username;
		this.password=password;
	}
	
	public void storeAllDetails(){
		
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
