package uk.ac.ed.bikerental;

import java.util.HashMap;

public class QueryInfo {
    
    public HashMap<String, Integer> bikesRequired;
    public Location hireLocation;
    public DateRange searchDates;
    
    public QueryInfo(HashMap<String, Integer> bikesRequired, Location hireLocation, DateRange searchDates) {
        // key of bikesRequired is the name of the BikeType
        this.bikesRequired = bikesRequired;
        this.hireLocation = hireLocation;
        this.searchDates = searchDates;
    }

}