package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;

public class MockPricingPolicy implements PricingPolicy {
	
	private HashMap<BikeType, BigDecimal> priceMap;
	
	public MockPricingPolicy() {
		this.priceMap = new HashMap<BikeType, BigDecimal>();
	}

	@Override
	public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
		priceMap.put(bikeType, dailyPrice);
	}

	@Override
	public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		long numberOfDays = duration.toDays();
		assert(numberOfDays > 0);
		Bike bikeArray[] = new Bike[bikes.size()];
		// shoves bikes.toArray() into bikeArray
		bikeArray = bikes.toArray(bikeArray);
		for (int i = 0; i < bikeArray.length; i++) {
			BigDecimal bikeTypePrice = priceMap.get(bikeArray[i].getType());
			totalPrice = totalPrice.add(bikeTypePrice);
		}
		totalPrice = totalPrice.multiply(BigDecimal.valueOf(numberOfDays));
		if (numberOfDays <= 2) {
			totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
		} else if (numberOfDays <= 6) {
			totalPrice = totalPrice.multiply(BigDecimal.valueOf(0.95)).setScale(2, RoundingMode.HALF_UP);
		} else if (numberOfDays <= 13) {
			totalPrice = totalPrice.multiply(BigDecimal.valueOf(0.90)).setScale(2, RoundingMode.HALF_UP);
		} else {
			totalPrice = totalPrice.multiply(BigDecimal.valueOf(0.85)).setScale(2, RoundingMode.HALF_UP);
		}
		return totalPrice;
	}

}
 