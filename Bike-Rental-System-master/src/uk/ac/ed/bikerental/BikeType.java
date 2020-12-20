package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class BikeType {

    private final BigDecimal replacementVal; 
	private final String typeName;
	
    public BigDecimal getReplacementValue() {
    	return replacementVal;
    }
    public String getTypeName() {
    	return typeName;
    }
    
    public BikeType(BigDecimal replacementValue, String bikeTypeName) {
    	this.errorCatcher(replacementValue, bikeTypeName);
    	this.replacementVal = replacementValue;
    	this.typeName = bikeTypeName;
    }
    
    private void errorCatcher(BigDecimal replacementValue, String bikeTypeName) {
    	if (replacementValue.doubleValue() < 0) {
    		throw new IllegalArgumentException("Replacement Value cannot be negative!");
    	}
    	if (bikeTypeName.equals(null)) {
    		throw new NullPointerException("Bike type name cannot be null!");
    	}
    }
    
    // functions for equality testing
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((replacementVal == null) ? 0 : replacementVal.hashCode());
        result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BikeType other = (BikeType) obj;
        if (replacementVal == null) {
            if (other.replacementVal != null)
                return false;
        } else if (!replacementVal.equals(other.replacementVal))
            return false;
        if (typeName == null) {
            if (other.typeName != null)
                return false;
        } else if (!typeName.equals(other.typeName))
            return false;
        return true;
    }
}