package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class DoubleDecliningDepreciation implements ValuationPolicy {

    private double fixedPercent;

    public DoubleDecliningDepreciation(double fixedPercent) {
    	this.errorCatcher(fixedPercent);
        this.fixedPercent = fixedPercent;
    }  

    @Override
    public BigDecimal calculateValue(Bike bike, LocalDate date) {
        BigDecimal replacementVal = bike.getType().getReplacementValue();
        DateRange lifetime = new DateRange(bike.getCreationDate(), date);
        // finds the age in years converts to a double for easier manipulation in the calculation
        double bikeAge = (double) lifetime.toYears();
        // calculates the percentage of the original value that the bike is now valued at
        double valueDecrease = Math.pow(1-2*fixedPercent, bikeAge);
        // creates a new BigDecimal object to multiply with the original value to calculate the new value
        BigDecimal value = replacementVal.multiply(new BigDecimal(valueDecrease));
        // always rounds to 2 dp for price format
        return value.setScale(2, RoundingMode.HALF_UP);
    }
    
    private void errorCatcher(double fixedPercent) {
		if (fixedPercent < 0 || fixedPercent > 1) {
			throw new IllegalArgumentException("A percentage must be between 0.00 and 1.00!");
		}
    }
}