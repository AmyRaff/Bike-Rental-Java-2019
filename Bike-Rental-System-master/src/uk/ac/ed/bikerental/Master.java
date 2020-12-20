package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Master {
    
    public static Collection<Quote> getQuotes(QueryInfo query, Collection<BikeProvider> providerList){
        // we store the initial duration so we can keep it constant if we 
        // don't find any quotes initially
        ArrayList<Quote> quoteList = new ArrayList<Quote>();
        long duration = query.searchDates.toDays();
        
        while (quoteList.size() == 0) {
            // determine the the first date on the search for use if we
            // have to iterate through several date ranges
            LocalDate queryStart = query.searchDates.getStart();
            
            // iterate through all possible date ranges of original duration
            // between the start and end of the search range
            for(long i = 0; i <= (query.searchDates.toDays() - duration); i++) {
                
                // create a variable to contain the current hire dates under consideration,
                // i days away from the start of the search
                DateRange hireDates = new DateRange(queryStart.plusDays(i), queryStart.plusDays(duration + i));
                for(BikeProvider currProvider : providerList) {
                	
                    // initialise a variable to determine whether we have found all required bikes
                    // must be initialised to true or it would always be false as we are using it in
                    // with the "&&" operator
                	boolean finished = true;
                	
                    // check that the provider is close enough to be considered for a quote
                    if (currProvider.getStoreAddress().isNearTo(query.hireLocation)) {
                        ArrayList<Bike> proposedBikes = new ArrayList<>();
                        // initialises a new HashMap with the same values as the search criteria
                        // so that we can remove bikes from the current search as we find them
                        // without changing the overall search criteria
                        HashMap<String, Integer> bikesToFind = new HashMap<>(query.bikesRequired);
                        BigDecimal depositAmount = BigDecimal.ZERO;
                        
                        for(Bike currBike : currProvider.getStockList()) {
                        	finished = true;
                            String currTypeName = currBike.getType().getTypeName();
                            
                            // check whether the current bike is acceptable for the current search criteria
                            if (currBike.bikeMatch(query, hireDates)) {
                                // check that all required bikes of this type haven't been found already
                                if (bikesToFind.get(currTypeName) != 0) {
                                    bikesToFind.put(currTypeName, bikesToFind.get(currTypeName)-1);
                                    proposedBikes.add(currBike);
                                    
                                    // calculate the value of the bike at the start of the hire
                                    BigDecimal bikeValue = currBike.getOwner().valuationPolicy.
                                    		calculateValue(currBike, hireDates.getStart());
                                    depositAmount = depositAmount.add(bikeValue.multiply(currBike.
                                    		getOwner().getDepositRate()));
                                }
                            }
                            
                            // checks that there are no bikes left to find within bikesToFind
                            for (int val : bikesToFind.values()) {
                                finished = finished && (val == 0);
                            }
                            
                            if (finished) {
                                break;
                            } 
                        }
                    
                        // creates a new quote if all required bikes have been found for the current provider
                        if (finished) {
                            DepositInfo newDeposit = new DepositInfo(depositAmount.setScale(2, RoundingMode.HALF_UP));
                            BigDecimal totalCost = currProvider.pricingPolicy.calculatePrice(proposedBikes, hireDates);
                            Quote newQuote = new Quote(quoteList.size(), currProvider, 
                            		proposedBikes.toArray(new Bike[proposedBikes.size()]), totalCost, newDeposit, hireDates);
                            quoteList.add(newQuote);
                        }
                    }
                }
            }
            
            // if no quotes have been found and the searchDates are still the users original dates, extend the search range
            if (quoteList.size() == 0 && query.searchDates.toDays() == duration) {
                query.searchDates.extendDates();
                System.out.println("No quotes found, extending dates by 3 days");
            }
            // if the search range has already been extended and no quotes have been found, return null
            else if(query.searchDates.toDays() != duration && quoteList.size() == 0){
            	return null;
            }
        }

        return quoteList;
    }
    
    // creates a string representing all of the quotes in a list
    public static String displayQuotes(Collection<Quote> quoteList) {
        String result = "";
        for (Quote newQuote : quoteList) {
            result = result.concat(newQuote.toString());
        }
        return result;
    }
}