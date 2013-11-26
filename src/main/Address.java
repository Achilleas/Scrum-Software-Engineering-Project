package main;

/**
 * Class representing the Address of an {@link Investor} 
 */
public class Address {
	private String line1;
	private String line2;
	private String town;
	private String county;
	private String postcode;
	private String country;

	/**
	 * The Address constructor
	 * @param line1 first line of address
	 * @param line2 second line of address
	 * @param town city of investor
	 * @param county county of investor
	 * @param postcode postcode of investor
	 * @param country country of investor
	 */
	public Address(String line1, String line2, String town, String county,
			String postcode, String country) {
		this.line1 = line1;
		this.line2 = line2;
		this.town = town;
		this.county = county;
		this.postcode = postcode;
		this.country = country;
	}

	/**
	 * Secondary Address constructor if not information was given
	 */
	public Address() {
		this.line1 = "";
		this.line2 = "";
		this.town = "";
		this.county = "";
		this.postcode = "";
		this.country = "";
	}

	/**
	 * Gets all the address elements
	 * @return returns an array of all the address elements
	 */
	public String[] getAddString() {
		String[] add = new String[6];
		add[0] = line1;
		add[1] = line2;
		add[2] = town;
		add[3] = county;
		add[4] = postcode;
		add[5] = country;
		return add;
	}

	/**
	 * Setter for first line of address
	 * @param line1 first line of address
	 */
	public void setLine1(String line1) {
		this.line1 = line1;
	}

	/**
	 * Setter for second line of address
	 * @param line2 second line of address
	 */
	public void setLine2(String line2) {
		this.line2 = line2;
	}

	/**
	 * Setter for city/town of investor
	 * @param town city/town of investor
	 */
	public void setTown(String town) {
		this.town = town;
	}
	
	/**
	 * Setter for county of investor
	 * @param county county of investor
	 */
	public void setCounty(String county) {
		this.county = county;
	}

	/**
	 * Setter for postcode of investor
	 * @param postcode postcode of investor
	 */
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	/**
	 * Setter for country of investor
	 * @param country country of investor
	 */
	public void setCountry(String country) {
		this.country = country;
	}

}
