package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBooking {

	private Bike bike1, bike2;
	private BikeType biketype1, biketype2;
	private BikeProvider provider1, provider2;
	private Booking booking1;
	private Customer customer1;
	private Quote quote1;
	private DefaultPricing default1;
	
	@BeforeEach
	void setUp() throws Exception {
		this.default1 = new DefaultPricing();
		this.biketype1 = new BikeType(new BigDecimal(900), "Boi");
		this.biketype2 = new BikeType(new BigDecimal(218), "Smol");
		this.provider1 = new BikeProvider(2, "ByeBikes", new Location("EH1 1PP", "12 North Street"),
        		BigDecimal.valueOf(0.43), default1, null);
		this.provider2 = new BikeProvider(5, "BikeyBobs", new Location("EH9 7GG", "3/2 Forth Avenue"),
        		BigDecimal.valueOf(0.21), default1, null);
		provider1.pricingPolicy.setDailyRentalPrice(biketype1, new BigDecimal(25.30));
		provider1.pricingPolicy.setDailyRentalPrice(biketype2, new BigDecimal(10.33));
		provider2.pricingPolicy.setDailyRentalPrice(biketype1, new BigDecimal(45.66));
		provider2.pricingPolicy.setDailyRentalPrice(biketype2, new BigDecimal(28.37));
        this.bike1 = new Bike(biketype1, provider1, LocalDate.of(2010, 9, 10));
        this.bike2 = new Bike(biketype2, provider1, LocalDate.of(2017, 9, 10));
        this.customer1 = new Customer("Bobert Bobbling", new Location("PA12 G89", "45 Long Road"), "03338276251");
        this.quote1 = new Quote(32, provider1, new Bike[] {bike1, bike2}, new BigDecimal(342.34), 
        		new DepositInfo(BigDecimal.valueOf(30.43)), new DateRange(LocalDate.of(2019, 12, 2), 
        		LocalDate.of(2019, 12, 5)));
        this.booking1 = new Booking(01, customer1, quote1, LocalDate.of(2019, 01, 01), false, true, provider2);
	}
	
	@Test
	// tests updating a booking status works as expected
	void testUpdateStatus() {
		booking1.updateBookingStatus(BookingStatus.InProgress);
		assertEquals(BookingStatus.InProgress, booking1.getBookingStatus());
	}
	
	@Test
	// tests booking confirmations print what is expected of them
	void testProduceConfirmation() {
		System.out.println(booking1.produceConfirmation());
	}
	
	@Test
	// tests toString() prints what is expected
	void testToString() {
		System.out.println(booking1.toString());
	}
}