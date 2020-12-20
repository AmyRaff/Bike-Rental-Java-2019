package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.*;

public class TestBike {
	
	private Bike bike1, bike2, bike3;
	private BikeType biketype1, biketype2, biketype3;
	private DateRange daterange1, daterange2, daterange3;
	private BikeProvider provider1;
	private HashMap<String, Integer> bikes1, bikes2, bikes3;
	private QueryInfo query1, query2, query3, query4;
	private DefaultPricing default1;
	
	@BeforeEach
	void setUp() throws Exception {
		this.daterange1 = new DateRange(LocalDate.of(2020, 1, 7), LocalDate.of(2020, 1, 10));
        this.daterange2 = new DateRange(LocalDate.of(2020, 5, 5), LocalDate.of(2020, 5, 12));
        this.daterange3 = new DateRange(LocalDate.of(2020, 1, 5), LocalDate.of(2020, 1, 15));
        
		this.biketype1 = new BikeType(new BigDecimal(900), "Boi");
		this.biketype2 = new BikeType(new BigDecimal(500), "Shaun");
		this.biketype3 = new BikeType(new BigDecimal(1500), "Wholf");
		
		this.provider1 = new BikeProvider(1, "ByeBikes",
		        new Location("EH1 1PP", "12 North Street"),
        		BigDecimal.valueOf(0.43),
        		default1,
        		null);
        
		this.bike1 = new Bike(biketype1, provider1, LocalDate.of(2010, 9, 10));
		this.bike2 = new Bike(biketype2, provider1, LocalDate.of(2016, 2, 15));
		this.bike3 = new Bike(biketype3, provider1, LocalDate.of(2014, 12, 12));

        this.bikes1 = new HashMap<String, Integer>();
        this.bikes1.put("Boi", 1);
        
        this.bikes2 = new HashMap<String, Integer>();
        this.bikes2.put("Boi", 1);
        this.bikes2.put("Shaun", 1);
        
        this.bikes3 = new HashMap<String, Integer>();
        this.bikes3.put("Wholf", 3);
        this.bikes3.put("Shaun", 6);
        
        this.query1 = new QueryInfo(bikes1, new Location("EH3 5EN", "1 Garden Gardens"), daterange2);
        this.query2 = new QueryInfo(bikes2, new Location("EH18 1IH", "97 Upward Falls"), daterange2);
        this.query3 = new QueryInfo(bikes3, new Location("EH6 2BB", "12 Orange Fields"), daterange2);
        this.query4 = new QueryInfo(bikes3, new Location("EH4 3EE", "69 Windy Heights"), daterange3);
        }
	
	@Test
	// test bike status updated correctly
	void testUpdateStatusTrue() {
		bike1.updateBikeStatus(BikeStatus.CurrentlyRented);
		assertEquals(BikeStatus.CurrentlyRented, bike1.getStatus());
	}
	
	@Test
	void testUpdateStatusFalse() {
		bike1.updateBikeStatus(BikeStatus.CurrentlyRented);
		assertNotEquals(BikeStatus.AvailableInStore, bike1.getStatus());
	}
	
	@Test
	// test adding reserved dates to a bike functions correctly
	void testAddReservedDates() {
		ArrayList<DateRange> testdates = new ArrayList<DateRange>();
		testdates.add(daterange1);
		bike1.addReservedDates(daterange1);
		assertEquals(testdates, bike1.getReservedDates());
	}
	
	@Test
	// test that a bike is accepted for a query for one instance of one bike type
	void testBikeMatchTrue1() {
	    assertTrue(bike1.bikeMatch(query1, daterange2));
	}
	
	@Test
	// test that a bike is accepted for a query with several different types
	void testBikeMatchTrue2() {
	    assertTrue(bike1.bikeMatch(query2, daterange2));
	}
	
	@Test
	// test that a bike is accepted for a query with several instances of several types
	void testBikeMatchTrue3() {
	    assertTrue(bike2.bikeMatch(query3, daterange2));
	}
	
	@Test
	// test that a bike is not accepted when it is a different type than requested
	void testBikeMatchFalse1() {
	    assertFalse(bike3.bikeMatch(query2, daterange2));
	}
	
	@Test
	// test that a bike is not accepted if it is reserved on the required dates
	void testBikeMatchFalse2() {
	    bike2.addReservedDates(daterange1);
	    assertFalse(bike2.bikeMatch(query4, daterange3));
	    
	}
}
