package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.Address;
import main.DOB;
import main.Investor;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InvestorTest {

	String username = "john";
	String password = "1234";
	String firstName = "John";
	String surname = "Doe";
	DOB dob = new DOB(23, 11, 1950);
	String email = "jd@hotmail.co.uk";
	String telephone = "07754791234";
	Address address = new Address("5 Hartwell House", "Strawberry Terrace",
			"Oz", "Wizardshire", "OZ15 9YR", "Oztopia");
	ArrayList<String> companiesInvested;
	ArrayList<String> companiesInterested;

	@Before
	public void setupBefore() {
		companiesInvested = new ArrayList<String>();
		companiesInterested = new ArrayList<String>();

		companiesInterested.add("HSBA");
		companiesInvested.add("BARC");
	}

	@Test
	public void getterTest() {
		Investor john = new Investor(username, password, dob, firstName,
				surname, email, telephone, address, companiesInvested,
				companiesInterested);
		assertEquals(username, john.getUsername());
		assertEquals(password, john.getPassword());
		assertEquals(dob, john.getDob());
		assertEquals(firstName, john.getFirstName());
		assertEquals(surname, john.getSurname());
		assertEquals(email, john.getEmail());
		assertEquals(telephone, john.getTelephone());
		assertEquals(address, john.getAddress());
	}

	@Test
	public void DOBTest() {
		Investor john = new Investor(username, password, dob, firstName,
				surname, email, telephone, address, companiesInvested,
				companiesInterested);
		DOB birthday = john.getDob();
		assertEquals(birthday.getBirthday()[0], 23);
		assertEquals(birthday.getBirthday()[1], 11);
		assertEquals(birthday.getBirthday()[2], 1950);
	}

	@Test
	public void arrayListTest() {
		Investor john = new Investor(username, password, firstName, surname,
				companiesInvested, companiesInterested);

		assertTrue(john.isInterested("HSBA"));
		assertTrue(john.isInvested("BARC"));

		assertFalse(john.isInterested("BARC"));
		assertFalse(john.isInvested("HSBA"));
	}

	@Test
	public void addInterestedTest() {
		Investor john = new Investor(username, password, firstName, surname,
				companiesInvested, companiesInterested);

		assertTrue(john.isInterested("HSBA"));
		assertTrue(john.isInvested("BARC"));

		john.addInterested("BARC");

		assertTrue(john.isInterested("BARC"));
		assertFalse(john.isInvested("HSBA"));
	}

	@Test
	// create a profile with empty list
	public void createClass() {
		Investor john = new Investor(username, password, firstName, surname);

		assertFalse(john.isInterested("BARC"));
		assertFalse(john.isInvested("BARC"));
		assertFalse(john.isInterested("HSBA"));
		assertFalse(john.isInvested("HSBA"));

		john.addInterested("BARC");
		assertTrue(john.isInterested("BARC"));
	}
}
