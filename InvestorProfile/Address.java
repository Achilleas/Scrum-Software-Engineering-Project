//Home Address Class
public class Address {
	private String line1;
	private String line2;
	private String city;
	private String county;
	private String postcode;
	private String country;
	
	//Constructor
	public Address(String line1, String line2, String city, String county, String postcode, String country){
		this.line1 = line1;
		this.line2 = line2;
		this.city = city;
		this.county = county;
		this.postcode = postcode;
		this.country = country;
	}
	
	//Returns String Array containing Address elements
	public String[] getAddress() {
		String add[6];
		add[0] = line1;
		add[1] = line2;
		add[2] = city;
		add[3] = county;
		add[4] = postcode;
		add[5] = country;
		return add;
	}
	
}
