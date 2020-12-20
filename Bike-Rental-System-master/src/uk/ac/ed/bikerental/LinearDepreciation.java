package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class LinearDepreciation implements ValuationPolicy {

	private final BigDecimal fixedPercent;
	
	public LinearDepreciation(BigDecimal fixedPercentage) {
		this.errorCatcher(fixedPercentage);
		this.fixedPercent = fixedPercentage;
	}
	
	@Override
	public BigDecimal calculateValue(Bike bike, LocalDate date) {
		BigDecimal originalValue = bike.getType().getReplacementValue();
		// deprecation rate = amount taken off each year
		BigDecimal deprecationRate = originalValue.multiply(fixedPercent);
		DateRange lifetime = new DateRange(bike.getCreationDate(), date);
		long years = lifetime.toYears();
		BigDecimal numYears = new BigDecimal(years);
		BigDecimal value = originalValue.subtract(deprecationRate.multiply(numYears));
		if (value.doubleValue() <= 0) {
			throw new IllegalArgumentException("The value of a bike must be greater than zero!");
		}
		// always rounds to 2 dp for price format
		return value.setScale(2, RoundingMode.HALF_UP);
	}
	
	private void errorCatcher(BigDecimal fixedPercentage) {
		if (fixedPercentage.doubleValue() < 0 || fixedPercentage.doubleValue() > 1) {
			throw new IllegalArgumentException("A percentage must be between 0.00 and 1.00!");
		}
	}

}
