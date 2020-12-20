package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.util.ArrayList;

public class Bike {

    private final BikeType type;
    private final BikeProvider owner;
    private ArrayList<DateRange> datesReserved;
    private final LocalDate creationDate;
    private BikeStatus status;
    
    public Bike(BikeType type, BikeProvider owner, LocalDate creationDate) {
    	this.errorCatcher(type, owner, creationDate);
        this.type = type;
        this.owner = owner;
        this.creationDate = creationDate;
        this.datesReserved = new ArrayList<DateRange>();
        this.status = BikeStatus.AvailableInStore;
        owner.addBike(this);
    }
    
    public BikeType getType() {
        return type;
    }
    public BikeProvider getOwner() {
        return owner;
    }
    public ArrayList<DateRange> getReservedDates() {
        return datesReserved;
    }
    public LocalDate getCreationDate() {
        return creationDate;
    }
    public BikeStatus getStatus() {
        return status;
    }
    
    private void errorCatcher(BikeType type, BikeProvider owner, LocalDate creationDate) {
    	if (type.equals(null)) {
    		throw new NullPointerException("Bike Type cannot be null!");
    	}
    	if (owner.equals(null)) {
    		throw new NullPointerException("Bike Owner cannot be null!");
    	}
    	if (creationDate.isAfter(LocalDate.now())) {
    		throw new IllegalArgumentException("Bike creation date cannot be in the future!");
    	}
    }
    
    public boolean bikeMatch(QueryInfo query, DateRange hireDates) {
        
    	boolean suitable = true;
        for (String requestedType : query.bikesRequired.keySet()) {
        	suitable = true;
            if (type.getTypeName() == requestedType) { 
                for(DateRange reservation : datesReserved) {
                    if (reservation.overlaps(hireDates)){
                        suitable = false;
                        break; 
                    }
                }
                break;
            }
            else {
            	suitable = false;
            }
           
        }
        return suitable;
    }
    
    public void updateBikeStatus(BikeStatus newStatus) {
        this.status = newStatus;
    }
    
    public void addReservedDates(DateRange dates) {
    	if (dates.getStart().isBefore(LocalDate.now())) {
    		throw new IllegalArgumentException("Cannot reserve dates in the past!");
    	}
        this.datesReserved.add(dates);
    } 

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        Bike other = (Bike) obj;
        if (creationDate == null) {
            if (other.creationDate != null)
                return false;
        } else if (!creationDate.equals(other.creationDate))
            return false;
        if (owner == null) {
            if (other.owner != null)
                return false;
        } else if (!owner.equals(other.owner))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }
}