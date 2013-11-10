package test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import main.Address;
import main.Investor;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InvestorTest {
	
	String username = "john";
	String password = "1234";
	String firstName = "John";
	String surname = "Doe";
	Date dob = new Date();
	String email = "jd@hotmail.co.uk";
	String telephone = "07754791234";
	Address address = new Address("5 Hartwell House", "Strawberry Terrace", "Oz", "Wizardshire", "OZ15 9YR", "Oztopia");
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
		Investor john = new Investor(firstName, surname, username, password, companiesInvested, companiesInterested);
		assertEquals(firstName, john.getFirstName());
		assertEquals(surname, john.getSurname());
	}
	
	@Test
	public void arrayListTest() {
		Investor john = new Investor(firstName, surname, username, password, companiesInvested, companiesInterested);
		
		assertTrue(john.isInterested("HSBA"));
		assertTrue(john.isInvested("BARC"));
		
		assertFalse(john.isInterested("BARC"));
		assertFalse(john.isInvested("HSBA"));
	}
	
	@Test
	public void addInterestedTest() {
		Investor john = new Investor(firstName, surname, username, password, companiesInvested, companiesInterested);
		
		assertTrue(john.isInterested("HSBA"));
		assertTrue(john.isInvested("BARC"));
		
		john.addInterested("BARC");
		
		assertTrue(john.isInterested("BARC"));
		assertFalse(john.isInvested("HSBA"));
	}
	
	@Test
	// create a profile with empty list
	public void createClass() {
		Investor john = new Investor(firstName, surname, username, password);
		
		assertFalse(john.isInterested("BARC"));
		assertFalse(john.isInvested("BARC"));
		assertFalse(john.isInterested("HSBA"));
		assertFalse(john.isInvested("HSBA"));
		
		john.addInterested("BARC");
		assertTrue(john.isInterested("BARC"));
	}
}
