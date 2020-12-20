package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultPricing implements PricingPolicy {

	private HashMap<BikeType, BigDecimal> priceMap;
	
	public DefaultPricing() {
		this.priceMap = new HashMap<BikeType, BigDecimal>();
	}
	
	public Map<BikeType, BigDecimal> getPriceMap() {
		return priceMap;
	}
	
	@Override
	public void setDailyRentalPrice(BikeType bikeType, BigDecimal dailyPrice) {
		priceMap.put(bikeType, dailyPrice);
	}

	@Override
	public BigDecimal calculatePrice(Collection<Bike> bikes, DateRange duration) {
		BigDecimal totalPrice = BigDecimal.ZERO;
		long numberOfDays = duration.toDays();
		Bike bikeArray[] = new Bike[bikes.size()];
		// shoves bikes.toArray() into bikeArray
		bikeArray = bikes.toArray(bikeArray);
		for (int i = 0; i < bikeArray.length; i++) {
			BigDecimal bikeTypePrice = priceMap.get(bikeArray[i].getType());
			totalPrice = totalPrice.add(bikeTypePrice);
		}
		return totalPrice.multiply(BigDecimal.valueOf(numberOfDays)).setScale(2, RoundingMode.HALF_UP);
	}
}
