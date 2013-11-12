package main;

//Home Address Class
public class Address {
	private String line1;
	private String line2;
	private String town;
	private String county;
	private String postcode;
	private String country;

	// Constructor
	public Address(String line1, String line2, String town, String county,
			String postcode, String country) {
		this.line1 = line1;
		this.line2 = line2;
		this.town = town;
		this.county = county;
		this.postcode = postcode;
		this.country = country;
	}

	public Address() {
		this.line1 = "";
		this.line2 = "";
		this.town = "";
		this.county = "";
		this.postcode = "";
		this.country = "";
	}

	// Returns String Array containing Address elements
	public String[] getString() {
		String[] add = new String[6];
		add[0] = line1;
		add[1] = line2;
		add[2] = town;
		add[3] = county;
		add[4] = postcode;
		add[5] = country;
		return add;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public void setCountry(String country) {
		this.country = country;
	}

}
