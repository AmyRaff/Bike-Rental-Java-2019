package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

public class ValuationPolicyTests {
    
	private LinearDepreciation linear1, linear2;
	private DoubleDecliningDepreciation double1, double2;
	private DefaultValuation default2;
	private Bike bike1, bike2, bike3;
	private BikeProvider provider1;
	private BikeType biketype1, biketype2;
	private DefaultPricing default1;

    @BeforeEach
    void setUp() throws Exception {
        this.linear1 = new LinearDepreciation(new BigDecimal(0.1));
        this.linear2 = new LinearDepreciation(new BigDecimal(0.02));
        this.double1 = new DoubleDecliningDepreciation(0.1);
        this.double2 = new DoubleDecliningDepreciation(0.02);
        this.default1 = new DefaultPricing();
        this.default2 = new DefaultValuation();
        this.biketype1 = new BikeType(new BigDecimal(900), "Boi");
        this.biketype2 = new BikeType(new BigDecimal(612), "Gorl");
        this.provider1 = new BikeProvider(4, "ByeBikes", new Location("EH1 1PP", "12 North Street"),
        		BigDecimal.valueOf(0.43), default1, default2);
        this.bike1 = new Bike(biketype1, provider1, LocalDate.of(2010, 9, 10));
        this.bike2 = new Bike(biketype2, provider1, LocalDate.of(2017, 2, 26));
        this.bike3 = new Bike(biketype2, provider1, LocalDate.of(1998, 5, 1));
    }
   
    // tests that the policies output the expected calculated values
    @Test
    void testLinear1Bike1() {
    	assertEquals(new BigDecimal(90.00).setScale(2, RoundingMode.HALF_UP), this.linear1.calculateValue(bike1, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testLinear1Bike2() {
    	assertEquals(new BigDecimal(489.60).setScale(2, RoundingMode.HALF_UP), this.linear1.calculateValue(bike2, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testDouble1Bike1() {
    	assertEquals(new BigDecimal(120.80).setScale(2, RoundingMode.HALF_UP), this.double1.calculateValue(bike1, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testDouble1Bike2() {
    	assertEquals(new BigDecimal(391.68).setScale(2, RoundingMode.HALF_UP), this.double1.calculateValue(bike2, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testLinear2Bike1() {
    	assertEquals(new BigDecimal(738.00).setScale(2, RoundingMode.HALF_UP), this.linear2.calculateValue(bike1, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testLinear2Bike2() {
    	assertEquals(new BigDecimal(587.52).setScale(2, RoundingMode.HALF_UP), this.linear2.calculateValue(bike2, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testDouble2Bike1() {
    	assertEquals(new BigDecimal(623.28).setScale(2, RoundingMode.HALF_UP), this.double2.calculateValue(bike1, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testDouble2Bike2() {
    	assertEquals(new BigDecimal(564.02).setScale(2, RoundingMode.HALF_UP), this.double2.calculateValue(bike2, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testDefault1Bike1() {
    	assertEquals(new BigDecimal(900).setScale(2, RoundingMode.HALF_UP), this.default2.calculateValue(bike1, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    void testDefault1Bike2() {
    	assertEquals(new BigDecimal(612).setScale(2, RoundingMode.HALF_UP), this.default2.calculateValue(bike2, 
    	LocalDate.of(2019, 11, 12)));
    }
    
    @Test
    // tests that exceptions are caught by assertions
    void testErrors() {
    	try {
    		linear1.calculateValue(bike3, LocalDate.of(2019, 11, 12));
    	} 
    	catch(Exception e) {
    		System.out.println("Exception caught!");
    	}
    }
    
}
