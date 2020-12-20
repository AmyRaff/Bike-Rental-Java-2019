package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.*;

public class TestBikeProvider {

	private Bike bike1;
	private BikeType biketype1;
	private BikeProvider provider1, provider2;
	private Booking booking1;
	private Customer customer1;
	private Quote quote1;
	private DefaultPricing default1;
	
	@BeforeEach
	void setUp() throws Exception {
		this.biketype1 = new BikeType(new BigDecimal(900), "Boi");
		this.provider1 = new BikeProvider(7, "ByeBikes", new Location("EH1 1PP", "12 North Street"),
        		BigDecimal.valueOf(0.43), default1, null);
		this.provider2 = new BikeProvider(12, "BikeyBobs", new Location("EH9 7GG", "3/2 Forth Avenue"),
        		BigDecimal.valueOf(0.21), default1, null);
        this.bike1 = new Bike(biketype1, provider1, LocalDate.of(2010, 9, 10));
        this.customer1 = new Customer("Bobert Bobbling", new Location("PA12 G89", "45 Long Road"), "03338276251");
        this.quote1 = new Quote(32, provider1, new Bike[] {bike1}, new BigDecimal(342.34), 
        		new DepositInfo(BigDecimal.valueOf(30.43)), new DateRange(LocalDate.of(2019, 12, 2), 
        		LocalDate.of(2019, 12, 5)));
        this.booking1 = new Booking(01, customer1, quote1, LocalDate.of(2019, 01, 01), false, true, provider2);
	}
	
	@Test
	// test adding a partner adds a partner to both providers
	void testAddPartner() {
		int listLength1 = provider1.getPartners().size();
		int listLength2 = provider2.getPartners().size();
		provider1.addPartner(provider2);
		assertEquals(listLength1 + 1, provider1.getPartners().size());
		assertEquals(listLength2 + 1, provider2.getPartners().size());
	}
	
	@Test
	// tests adding a booking functions correctly
	void testAddBooking() {
		int bookingListLength = provider1.getNoOfBookings();
		provider1.addBooking(booking1);
		assertEquals(bookingListLength + 1, provider1.getNoOfBookings());
	}
}
