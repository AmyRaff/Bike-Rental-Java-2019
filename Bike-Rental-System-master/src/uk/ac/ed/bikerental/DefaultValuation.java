package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class DefaultValuation implements ValuationPolicy {

	public DefaultValuation() {
	}
	
	@Override
	public BigDecimal calculateValue(Bike bike, LocalDate date) {
		// default value = replacement value of bike
		BigDecimal value = bike.getType().getReplacementValue();
		return value.setScale(2, RoundingMode.HALF_UP);
	}
}
