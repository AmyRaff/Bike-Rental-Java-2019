package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

public class TestQuote {
	
	private BikeType biketype1, biketype2;
	private Bike bike1, bike2, bike3, bike4;
	private Quote quote1, quote2, quote3;
	private BikeProvider provider1, provider2;
	private DefaultPricing default1;
	
	@BeforeEach
	void setUp() throws Exception {
		this.biketype1 = new BikeType(new BigDecimal(900), "Boi");
		this.biketype2 = new BikeType(new BigDecimal(218), "Smol");
		this.provider1 = new BikeProvider(1,"ByeBikes", new Location("EH1 1PP", "12 North Street"),
        		BigDecimal.valueOf(0.43), default1, null);
		this.provider2 = new BikeProvider(7,"BikeyBobs", new Location("EH9 7GG", "3/2 Forth Avenue"),
        		BigDecimal.valueOf(0.21), default1, null);
        this.bike1 = new Bike(biketype1, provider1, LocalDate.of(2010, 9, 10));
        this.bike2 = new Bike(biketype2, provider1, LocalDate.of(2017, 9, 10));
        this.bike3 = new Bike(biketype1, provider2, LocalDate.of(2018, 3, 14));
        this.bike4 = new Bike(biketype2, provider2, LocalDate.of(2019, 1, 7));
        this.quote1 = new Quote(32, provider1, new Bike[] {bike1, bike2}, new BigDecimal(342.34), 
        		new DepositInfo(BigDecimal.valueOf(40.69)), new DateRange(LocalDate.of(2019, 12, 2), 
        		LocalDate.of(2019, 12, 5)));
        this.quote2 = new Quote(12, provider1, new Bike[] {bike3, bike2}, new BigDecimal(211.56), 
        		new DepositInfo(BigDecimal.valueOf(30.43)), new DateRange(LocalDate.of(2019, 10, 2), 
        		LocalDate.of(2019, 10, 4)));
        this.quote3 = new Quote(17, provider2, new Bike[] {bike4, bike1}, new BigDecimal(427.65), 
        		new DepositInfo(BigDecimal.valueOf(23.56)), new DateRange(LocalDate.of(2019, 7, 20), 
        		LocalDate.of(2019, 8, 1)));
	}
	
	@Test
	// tests toString() gives correct output
	void testToString() {
		System.out.println(quote1.toString());
		System.out.println(quote2.toString());
		System.out.println(quote3.toString());
	}

}
