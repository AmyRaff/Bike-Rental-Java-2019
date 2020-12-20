package uk.ac.ed.bikerental;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

public class SystemTests {
    
	private QueryInfo query1, query2, query3, query4, query5, query6, query7, query8, query9, query10,
					  query11, query12, query13, query14;
	private BikeProvider provider1, provider2, provider3, provider4, provider5;
	private BikeType biketype1, biketype2, biketype3;
	private Bike bike1, bike2, bike3, bike4, bike5, bike6, bike7;
	private Customer customer1, customer2;
	private HashMap<String, Integer> bikesRequired1, bikesRequired2, bikesRequired3, bikesRequired4, bikesRequired5;
	private ArrayList<BikeProvider> providerList;
	private Quote quote1, quote2, quote3, quote4, quote5, quote6, quote7, quote8, quote9, quote10, quote11;
	private Booking booking1, booking2, booking3;
	private MockPricingPolicy mock1;

    @BeforeEach
    void setUp() throws Exception {
        DeliveryServiceFactory.setupMockDeliveryService();
        
        this.mock1 = new MockPricingPolicy();
        this.biketype1 = new BikeType(new BigDecimal(900), "Boi");
		this.biketype2 = new BikeType(new BigDecimal(218), "Smol");
		this.biketype3 = new BikeType(new BigDecimal(1000000000), "Syzygy");
		
        this.bikesRequired1 = new HashMap<String, Integer>();
        bikesRequired1.put(biketype1.getTypeName(), 1);
        bikesRequired1.put(biketype2.getTypeName(), 0);
        
        this.bikesRequired2 = new HashMap<String, Integer>();
        bikesRequired2.put(biketype1.getTypeName(), 0);
        bikesRequired2.put(biketype2.getTypeName(), 1);
        
        // should never exist
        this.bikesRequired3 = new HashMap<String, Integer>();
        bikesRequired3.put(biketype3.getTypeName(), 88);
        
        this.bikesRequired4 = new HashMap<String, Integer>();
        bikesRequired4.put(biketype1.getTypeName(), 2);
        
        this.bikesRequired5 = new HashMap<String, Integer>();
        bikesRequired5.put(biketype2.getTypeName(), 2);
        
        this.query1 = new QueryInfo(bikesRequired1, new Location("PA12 G89", "45 Long Road"), 
        		new DateRange(LocalDate.of(2019, 10, 1), LocalDate.of(2019, 10, 7)));
        
        this.query2 = new QueryInfo(bikesRequired2, new Location("EH2 9AY", "12 Curvy Road"), 
        		new DateRange(LocalDate.of(2019, 11, 1), LocalDate.of(2019, 11, 8)));
        
        this.query3 = new QueryInfo(bikesRequired3, new Location("PA12 G89", "45 Long Road"), 
        		new DateRange(LocalDate.of(2019, 10, 12), LocalDate.of(2019, 10, 25)));
        
        this.query4 = new QueryInfo(bikesRequired4, new Location("EH2 9AY", "12 Curvy Road"), 
        		new DateRange(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 1, 8)));
        
        this.query5 = new QueryInfo(bikesRequired5, new Location("EH19 5EU", "78 Short Street"),
        		new DateRange(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 1, 8)));
        
        this.query6 = new QueryInfo(bikesRequired1, new Location("PA45 7FK", "19 Hill Hill"),
        		new DateRange(LocalDate.of(2020, 10, 3), LocalDate.of(2020, 10, 9)));
        
        this.query7 = new QueryInfo(bikesRequired2, new Location("EH2 9AY", "12 Curvy Road"), 
        		new DateRange(LocalDate.of(2020, 10, 29), LocalDate.of(2020, 11, 11)));
        
        this.query8 = new QueryInfo(bikesRequired1, new Location("IV12 G89", "45 Long Road"), 
        		new DateRange(LocalDate.of(2019, 10, 1), LocalDate.of(2019, 10, 7)));
        
        this.query9 = new QueryInfo(bikesRequired1, new Location("EH12 3HH", "11/4 Nice Bridge"), 
        		new DateRange(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 12)));

        this.query10 = new QueryInfo(bikesRequired2, new Location("PA11 9PQ", "1 First Lane"),
        		new DateRange(LocalDate.of(2020, 2, 12), LocalDate.of(2020, 2, 22)));
        
        // Mock Pricing queries
        this.query11 = new QueryInfo(bikesRequired5, new Location("AB5 6UN", "67 Bank Bank"),
        		new DateRange(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 12)));
        
        this.query12 = new QueryInfo(bikesRequired5, new Location("AB5 6UN", "67 Bank Bank"),
        		new DateRange(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 16)));
        
        this.query13 = new QueryInfo(bikesRequired5, new Location("AB5 6UN", "67 Bank Bank"),
        		new DateRange(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 19)));
        
        this.query14 = new QueryInfo(bikesRequired5, new Location("AB5 6UN", "67 Bank Bank"),
        		new DateRange(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 30)));
        
        
        
		this.provider1 = new BikeProvider(2, "ByeBikes", new Location("PA1 1PP", "12 North Street"),
        		BigDecimal.valueOf(0.43), new DefaultPricing(), new DefaultValuation());
		
		this.provider2 = new BikeProvider(5, "BikeyBobs", new Location("EH9 7GG", "3/2 Forth Avenue"),
        		BigDecimal.valueOf(0.21), new DefaultPricing(), new DefaultValuation());
		
		// provider with linear deprecation valuation for integration testing

		this.provider3 = new BikeProvider(10, "ISellWheels", new Location("EH2 9OO", "11 High Grove"),
				BigDecimal.valueOf(0.30), new DefaultPricing(), new LinearDepreciation(BigDecimal.valueOf(0.10)));

		// provider with double declining valuation for integration testing
		this.provider4 = new BikeProvider(12, "NyoomsRUs", new Location("PA1 7YO", "45 Swirl View"),
				BigDecimal.valueOf(0.29), new DefaultPricing(), new DoubleDecliningDepreciation(0.01));
		
		// provider with mock pricing for integration testing
		this.provider5 = new BikeProvider(45, "UniCycles Ltd", new Location("AB2 5NO", "88 Mossy Patch"),
				BigDecimal.valueOf(0.21), mock1, new DefaultValuation());
		
		this.providerList = new ArrayList<>();
		provider1.pricingPolicy.setDailyRentalPrice(biketype1, new BigDecimal(25.30));
		provider1.pricingPolicy.setDailyRentalPrice(biketype2, new BigDecimal(10.33));
		provider2.pricingPolicy.setDailyRentalPrice(biketype1, new BigDecimal(45.66));
		provider2.pricingPolicy.setDailyRentalPrice(biketype2, new BigDecimal(28.37));
		provider3.pricingPolicy.setDailyRentalPrice(biketype1, new BigDecimal(19.06));
		provider3.pricingPolicy.setDailyRentalPrice(biketype2, new BigDecimal(22.22));
		provider4.pricingPolicy.setDailyRentalPrice(biketype1, new BigDecimal(38.12));
		provider4.pricingPolicy.setDailyRentalPrice(biketype2, new BigDecimal(19.32));
		provider5.pricingPolicy.setDailyRentalPrice(biketype2, new BigDecimal(15.00));
		
		providerList.add(provider1);
		providerList.add(provider2);
		providerList.add(provider3);
		providerList.add(provider4);
		providerList.add(provider5);
		
		this.bike1 = new Bike(biketype1, provider1, LocalDate.of(2010, 9, 10));
        this.bike2 = new Bike(biketype2, provider2, LocalDate.of(2017, 2, 9));
        this.bike3 = new Bike(biketype2, provider2, LocalDate.of(2017, 2, 14));
        this.bike4 = new Bike(biketype1, provider3, LocalDate.of(2016, 6, 4));
        this.bike5 = new Bike(biketype2, provider4, LocalDate.of(2012, 3, 21));
        this.bike6 = new Bike(biketype2, provider5, LocalDate.of(2017, 2, 9));
        this.bike7 = new Bike(biketype2, provider5, LocalDate.of(2017, 2, 9));
        
        
        this.quote1 = new Quote(0, provider1, new Bike[] {bike1}, new BigDecimal(151.80).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(387.00).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2019, 10, 1), LocalDate.of(2019, 10, 7)));
        this.quote2 = new Quote(0, provider2, new Bike[] {bike2}, new BigDecimal(198.59).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(45.78).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2019, 11, 1), LocalDate.of(2019, 11, 8)));
        this.quote3 = new Quote(0, provider2, new Bike[] {bike2,bike3}, new BigDecimal(453.92).setScale(2, RoundingMode.HALF_UP),
        		new DepositInfo(BigDecimal.valueOf(91.56).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2019, 12, 31), LocalDate.of(2020, 1, 8)));
        this.quote4 = new Quote(1, provider1, new Bike[] {bike1}, new BigDecimal(151.80).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(387.00).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2020, 10, 1), LocalDate.of(2020, 10, 7)));
        this.quote5 = new Quote(0, provider2, new Bike[] {bike2,bike3}, new BigDecimal(453.92).setScale(2, RoundingMode.HALF_UP),
        		new DepositInfo(BigDecimal.valueOf(91.56).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2020, 12, 31), LocalDate.of(2021, 1, 8)));
        this.quote6 = new Quote(0, provider3, new Bike[] {bike4}, new BigDecimal(209.66).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(189).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2020, 1, 1), LocalDate.of(2020, 1, 12)));
        this.quote7 = new Quote(0, provider4, new Bike[] {bike5}, new BigDecimal(193.20).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(54.88).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2020, 2, 12), LocalDate.of(2020, 2, 22)));
        this.quote8 = new Quote(0, provider5, new Bike[] {bike6,bike7}, new BigDecimal(60.00).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(91.56).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 12)));
        this.quote9 = new Quote(0, provider5, new Bike[] {bike6,bike7}, new BigDecimal(171.00).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(91.56).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 16)));
        this.quote10 = new Quote(0, provider5, new Bike[] {bike6,bike7}, new BigDecimal(243.00).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(91.56).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 19)));
        this.quote11 = new Quote(0, provider5, new Bike[] {bike6,bike7}, new BigDecimal(510.00).setScale(2, RoundingMode.HALF_UP), 
        		new DepositInfo(BigDecimal.valueOf(91.56).setScale(2, RoundingMode.HALF_UP)), 
        		new DateRange(LocalDate.of(2020, 1, 10), LocalDate.of(2020, 1, 30)));

        
        
        this.customer1 = new Customer("Bobert Bobbling", new Location("PA12 G89", "45 Long Road"), "03338276251");
        this.customer2 = new Customer("Shlobert Slobbling", new Location("EH45 8UU", "77 Loopy Lane"), "78976546789");
        
        this.booking1 = new Booking(0, customer1, quote4, LocalDate.now(), false, false, null);
        this.booking2 = new Booking(1, customer2, quote5, LocalDate.now(), true, false, null);
        this.booking3 = new Booking(0, customer2, quote5, LocalDate.now(), false, true, provider1);
    }

/* -------------------------------------------------------------------------------------------------------------------
 * 		TESTS FOR FINDING A QUOTE
 *  ------------------------------------------------------------------------------------------------------------------
 */
    @Test
    // testing correct outputs - includes range testing for providers
    void testQueryOne() {
    	ArrayList<Quote> quoteList = new ArrayList<>();
    	quoteList.add(quote1);
    	assertEquals(quoteList, Master.getQuotes(query1, providerList));
    }
    
    @Test
    // testing correct outputs - includes range testing for providers
    void testQueryTwo() {
    	ArrayList<Quote> quoteList = new ArrayList<>();
    	quoteList.add(quote2);
    	assertEquals(quoteList, Master.getQuotes(query2, providerList));
    }
    
    @Test
    // testing that if no providers have a requested bike type, null is returned
    void testBadType() {
    	assertEquals(null, Master.getQuotes(query3, providerList));
    }
    
    @Test
    // testing that if no providers have enough bikes to cover the query, null is returned
    void testTooManyBikes() {
    	assertEquals(null, Master.getQuotes(query4, providerList));
    }
    
    @Test
    // testing if several bikes can be found
    void testSeveralBikes() {
    	ArrayList<Quote> quoteList = new ArrayList<>();
    	quoteList.add(quote3);
    	assertEquals(quoteList, Master.getQuotes(query5, providerList));
    }
    
    @Test
    // test extend dates finds correct quotes, return quotes for 30/9/2020 - 6/10/2020, 1/10/2020 - 7/10/2020 (quote4)
    void testExtendDates() {
    	bike1.addReservedDates(new DateRange(LocalDate.of(2020, 10, 8), LocalDate.of(2020, 10, 14)));
    	assertTrue(Master.getQuotes(query6, providerList).contains(quote4));
    }
    
    @Test
    // test returns null when bikes are reserved in extended query dates
    void testReservedInExtendedDates() {
    	bike2.addReservedDates(new DateRange(LocalDate.of(2020, 10, 26), LocalDate.of(2020, 11, 14)));
    	bike3.addReservedDates(new DateRange(LocalDate.of(2020, 10, 26), LocalDate.of(2020, 11, 14)));
    	assertEquals(null, Master.getQuotes(query7, providerList));
    }
    
    @Test
    // tests that no bikes are returned if there are no providers in range
    // uses query1 with a different post code
    void testNoProviderInRange() {
    	assertEquals(null, Master.getQuotes(query8, providerList));
    }
    
    
/* -------------------------------------------------------------------------------------------------------------------
 * 		TESTS FOR BOOKING A QUOTE
 *  ------------------------------------------------------------------------------------------------------------------
 */
    
    // testing booking 1 is from quote 1 and everything is correct
    @Test
    void testBookingOne() {
    	assertEquals(booking1, quote4.createBooking(customer1, false, false, null, providerList));
    }
    
    // testing a booking with delivery
    @Test
    void testDeliveryBooking() {
    	MockDeliveryService deliveryBoy = (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
    	provider2.addBooking(booking2);
    	assertEquals(booking2, quote5.createBooking(customer2, true, false, null, providerList));
    	DeliveryServiceFactory.getDeliveryService().scheduleDelivery(booking2, 
    			booking2.getChosenQuote().getProvider().getStoreAddress(), booking2.getCustomer().getAddress(), 
    			booking2.getChosenQuote().getDatesChosen().getStart());
    	
    	deliveryBoy.carryOutPickups(booking2.getChosenQuote().getDatesChosen().getStart());
    	assertEquals(BikeStatus.InTransitCustomer, booking2.getChosenQuote().getSuitableBikes()[0].getStatus());
    	
    	deliveryBoy.carryOutDropoffs();
    	assertEquals(BookingStatus.InProgress, booking2.getBookingStatus());
    	assertEquals(BikeStatus.CurrentlyRented, booking2.getChosenQuote().getSuitableBikes()[0].getStatus());
    	
    	assertTrue(provider2.updateBookingDeposit(1, DepositStatus.Received));
    }
    
    // testing a booking with return to partner
    @Test
    void testPartnerReturnBooking() {
    	assertEquals(booking3, quote5.createBooking(customer2, false, true, provider1, providerList));
    	DeliveryServiceFactory.getDeliveryService().scheduleDelivery(booking3, 
    			booking3.getReturnPartner().getStoreAddress(), booking3.getChosenQuote().getProvider().getStoreAddress(), 
    			booking3.getChosenQuote().getDatesChosen().getEnd());
    }
    
    // Testing return null when customer out of delivery range and requests delivery
    @Test
    void testOutOfRange() {
    	assertEquals(null, quote5.createBooking(customer1, true, false, null, providerList));
    }
    
/* -------------------------------------------------------------------------------------------------------------------
 * 		TESTS FOR RETURNING BIKES
 *  ------------------------------------------------------------------------------------------------------------------
 */
    
    // Test for return to original provider when everything is fine
    @Test
    void testOriginalReturnTrue() {
    	provider1.addBooking(booking1);
    	assertTrue(provider1.updateBooking(0));
    	assertTrue(provider1.updateBookingDeposit(0, DepositStatus.ReturnedToCustomer));
    }
    
    // Test for false when booking number doesn't match any bookings
    @Test
    void testBadOrderNo() {
    	provider1.addBooking(booking1);
    	assertFalse(provider1.updateBooking(72));
    }
    
    // Test for false when wrong provider
    @Test
    void testWrongProvider() {
    	provider2.addBooking(booking2);
    	assertFalse(provider1.updateBooking(0));
    }
    
    // Test for return to partner provider
    @Test
    void testPartnerReturn() {
    	MockDeliveryService deliveryBoy = (MockDeliveryService) DeliveryServiceFactory.getDeliveryService();
    	provider2.addBooking(booking3);
    	assertTrue(provider1.updateBooking(0));
    	assertTrue(provider1.updateBookingDeposit(0, DepositStatus.ReturnedToCustomer));
    	deliveryBoy.scheduleDelivery(booking3, booking3.getReturnPartner().getStoreAddress(), 
    			booking3.getChosenQuote().getProvider().getStoreAddress(), 
    			booking3.getChosenQuote().getDatesChosen().getEnd());
    	deliveryBoy.carryOutPickups(booking3.getChosenQuote().getDatesChosen().getEnd());
    	assertEquals(BikeStatus.InTransitProvider, booking3.getChosenQuote().getSuitableBikes()[0].getStatus());
    	deliveryBoy.carryOutDropoffs();
    	assertEquals(BookingStatus.BikesReturned, booking3.getBookingStatus());
    	assertEquals(BikeStatus.AvailableInStore, booking3.getChosenQuote().getSuitableBikes()[0].getStatus());
    }
    
    /* -------------------------------------------------------------------------------------------------------------------
     * 		INTEGRATION TESTS FOR IMPLEMENTED SUBMODULE
     *  ------------------------------------------------------------------------------------------------------------------
     */

    @Test
    // testing linear depreciation
    void testLinearProvider() {
    	ArrayList<Quote> quoteList = new ArrayList<>();
    	quoteList.add(quote6);
    	assertEquals(quoteList, Master.getQuotes(query9, providerList));
    }

    @Test
    // testing double declining depreciation
    void testDoubleDecliningProvider() {
    	ArrayList<Quote> quoteList = new ArrayList<>();
    	quoteList.add(quote7);
    	assertEquals(quoteList, Master.getQuotes(query10, providerList));
   }
    
   @Test
   // testing mock pricing policy integration
   void testIntegrationPricing2Days() {
	   ArrayList<Quote> quoteList = new ArrayList<>();
	   quoteList.add(quote8);
	   assertEquals(quoteList, Master.getQuotes(query11, providerList));
   }
   
   @Test
   void testIntegrationPricing6Days() {
	   ArrayList<Quote> quoteList = new ArrayList<>();
	   quoteList.add(quote9);
	   assertEquals(quoteList, Master.getQuotes(query12, providerList));
   }
   
   @Test
   void testIntegrationPricing9Days() {
	   ArrayList<Quote> quoteList = new ArrayList<>();
	   quoteList.add(quote10);
	   assertEquals(quoteList, Master.getQuotes(query13, providerList));
   }
   
   @Test
   void testIntegrationPricing20Days() {
	   ArrayList<Quote> quoteList = new ArrayList<>();
	   quoteList.add(quote11);
	   assertEquals(quoteList, Master.getQuotes(query14, providerList));
   }
   
    
}