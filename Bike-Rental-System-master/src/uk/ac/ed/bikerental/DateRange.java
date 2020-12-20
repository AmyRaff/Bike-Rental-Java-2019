package uk.ac.ed.bikerental;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DateRange {
    private LocalDate start, end;
    
    public DateRange(LocalDate start, LocalDate end) {
    	this.errorCatcher(start, end);
        this.start = start;
        this.end = end;
    }
    
    public LocalDate getStart() {
        return this.start;
    }
    
    public LocalDate getEnd() {
        return this.end;
    }
    
    public void extendDates() {
        this.start = start.minusDays(3);
        this.end = end.plusDays(3);
    }

    private void errorCatcher(LocalDate start, LocalDate end) {
    	if (start.isAfter(end)) {
    		throw new IllegalArgumentException("The start of a date range cannot be after it's end!");
    	}
    }
    
    public long toYears() {
        return ChronoUnit.YEARS.between(this.getStart(), this.getEnd());
    }

    public long toDays() {
        return ChronoUnit.DAYS.between(this.getStart(), this.getEnd());
    }

    public Boolean overlaps(DateRange other) {
        if ((this.start.isAfter(other.getStart()) && this.start.isBefore(other.getEnd())) ||
        	(this.end.isAfter(other.getStart()) && this.end.isBefore(other.getEnd())) ||
        	(other.start.isAfter(this.getStart()) && other.start.isBefore(this.getEnd())) ||
        	(other.end.isAfter(this.getStart()) && other.end.isBefore(this.getEnd())) ||
        	this.end.isEqual(other.getStart()) || this.start.isEqual(other.getEnd()) ||
        	this.end.isEqual(other.getEnd()) || this.start.isEqual(other.getStart()) ||
        	this.equals(other)) {
        	return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        // hashCode method allowing use in collections
        return Objects.hash(end, start);
    }

    @Override
    public boolean equals(Object obj) {
        // equals method for testing equality in tests
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        DateRange other = (DateRange) obj;
        return Objects.equals(end, other.end) && Objects.equals(start, other.start);
    }
    
    @Override
    public String toString() {
    	String range = start + " - " + end;
    	return range;
    }
    
}
