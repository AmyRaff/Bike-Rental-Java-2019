package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

public class PricingPolicyTests {
    
	private DefaultPricing default1;
	private MockPricingPolicy mock1;
	private BikeProvider provider1, provider2;
	private BikeType biketype1, biketype2;
	private Bike bike1, bike2, bike3, bike4, bike5;

    @BeforeEach
    void setUp() throws Exception {
    	this.default1 = new DefaultPricing();
    	this.mock1 = new MockPricingPolicy();
        this.provider1 = new BikeProvider(1, "ByeBikes", new Location("EH1 1PP", "12 North Street"),
        		BigDecimal.valueOf(0.43), default1, null);
        this.provider2 = new BikeProvider(2, "WheelyLads", new Location("EH25 6JJ", "55 Rue Du Faubourg St-Honore"),
        		BigDecimal.valueOf(0.31), mock1, null);
        this.mock1 = new MockPricingPolicy();
        this.biketype1 = new BikeType(new BigDecimal(900), "Boi");
        this.biketype2 = new BikeType(new BigDecimal(612), "Gorl");
        this.bike1 = new Bike(biketype1, provider1, LocalDate.of(2010, 9, 10));
        this.bike2 = new Bike(biketype2, provider1, LocalDate.of(2017, 2, 26));
        this.bike3 = new Bike(biketype2, provider1, LocalDate.of(1998, 5, 1));
        this.bike4 = new Bike(biketype2, provider2, LocalDate.of(2016, 7, 2));
        this.bike5 = new Bike(biketype1, provider2, LocalDate.of(2000, 1, 1));
    }
    
    @Test
    // test the calculatePrice() method in DefaultPricing
    void testDefaultValueBikes() {
    	ArrayList<Bike> bikes = new ArrayList<>();
    	bikes.add(bike1);
    	bikes.add(bike2);
    	default1.setDailyRentalPrice(biketype1, BigDecimal.valueOf(80.28));
    	default1.setDailyRentalPrice(biketype2, BigDecimal.valueOf(117.02));
    	assertEquals(BigDecimal.valueOf(1973).setScale(2, RoundingMode.HALF_UP), default1.calculatePrice(bikes, 
    			new DateRange(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20))));
    }
    
    @Test
    // test value correct when more than one bike of a type
    void testDefaultValueDuplicateBikeType() {
    	ArrayList<Bike> bikes = new ArrayList<>();
    	bikes.add(bike1);
    	bikes.add(bike2);
    	bikes.add(bike3);
    	default1.setDailyRentalPrice(biketype1, BigDecimal.valueOf(80.28));
    	default1.setDailyRentalPrice(biketype2, BigDecimal.valueOf(117.02));
    	assertEquals(BigDecimal.valueOf(3143.20).setScale(2, RoundingMode.HALF_UP), default1.calculatePrice(bikes, 
    			new DateRange(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20))));
    }
    
    @Test
    // testing for duration << 2 days
    void testMockIntegration1() {
    	ArrayList<Bike> bikes = new ArrayList<>();
    	bikes.add(bike4);
    	bikes.add(bike5);
    	mock1.setDailyRentalPrice(biketype1, BigDecimal.valueOf(80.28));
    	mock1.setDailyRentalPrice(biketype2, BigDecimal.valueOf(117.02));
    	assertEquals(BigDecimal.valueOf(394.60).setScale(2, RoundingMode.HALF_UP), 
    			mock1.calculatePrice(bikes, new DateRange(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 12))));
    }
    
    @Test
    // testing for duration << 6 days
    void testMockIntegration2() {
    	ArrayList<Bike> bikes = new ArrayList<>();
    	bikes.add(bike4);
    	bikes.add(bike5);
    	mock1.setDailyRentalPrice(biketype1, BigDecimal.valueOf(80.28));
    	mock1.setDailyRentalPrice(biketype2, BigDecimal.valueOf(117.02));
    	assertEquals(BigDecimal.valueOf(749.74).setScale(2, RoundingMode.HALF_UP), 
    			mock1.calculatePrice(bikes, new DateRange(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 14))));
    }
    
    @Test
    // testing for duration << 13 days
    void testMockIntegration3() {
    	ArrayList<Bike> bikes = new ArrayList<>();
    	bikes.add(bike4);
    	bikes.add(bike5);
    	mock1.setDailyRentalPrice(biketype1, BigDecimal.valueOf(80.28));
    	mock1.setDailyRentalPrice(biketype2, BigDecimal.valueOf(117.02));
    	assertEquals(BigDecimal.valueOf(1598.13).setScale(2, RoundingMode.HALF_UP), 
    			mock1.calculatePrice(bikes, new DateRange(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 19))));
    }
    
    @Test
    // testing for duration > 14 days
    void testMockIntegration4() {
    	ArrayList<Bike> bikes = new ArrayList<>();
    	bikes.add(bike4);
    	bikes.add(bike5);
    	mock1.setDailyRentalPrice(biketype1, BigDecimal.valueOf(80.28));
    	mock1.setDailyRentalPrice(biketype2, BigDecimal.valueOf(117.02));
    	assertEquals(BigDecimal.valueOf(3354.10).setScale(2, RoundingMode.HALF_UP), 
    			mock1.calculatePrice(bikes, new DateRange(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 30))));
    }
}