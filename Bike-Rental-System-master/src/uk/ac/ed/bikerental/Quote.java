package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

public class Quote {
    private final int quoteID;
    private final BikeProvider provider;
    private final Bike suitableBikes[];
    private final BigDecimal totalCost;
    private final DepositInfo deposit;
    private final DateRange datesChosen;
    
    public Quote(int quoteID, BikeProvider provider, Bike[] suitableBikes, BigDecimal totalCost, DepositInfo deposit,
            DateRange datesChosen) {
        this.quoteID = quoteID;
        this.provider = provider;
        this.suitableBikes = suitableBikes;
        this.totalCost = totalCost;
        this.deposit = deposit;
        this.datesChosen = datesChosen;
    }

    public int getQuoteID() {
        return quoteID;
    }

    public BikeProvider getProvider() {
        return provider;
    }

    public Bike[] getSuitableBikes() {
        return suitableBikes;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    public DepositInfo getDeposit() {
        return deposit;
    }

    public DateRange getDatesChosen() {
        return datesChosen;
    }
    
    @Override
    public String toString() {
        String entry = "--------------------------------------\n"
                    + "Quote ID: " + Integer.toString(quoteID) + "\n"
                    + "Dates: " + datesChosen.toString() + "\n"
                    + "Bike Provider: " + provider.getStoreName() + "\n"
                    + "Total Cost: £" + totalCost.setScale(2, RoundingMode.HALF_UP) + "\n"
                    + "Deposit Amount: £" + deposit.getDepositAmount().toString() + "\n"
                    + "---------------------------------------\n";
        return entry;
    }
    
    public String printBikes() {
    	String output = "";
    	for (int i = 0; i < suitableBikes.length; i++) {
    		output = output.concat(suitableBikes[i].getType().getTypeName() + "\n");
    	}
    	return output;
    }
    
    public Booking createBooking(Customer customer, boolean isDelivered,
            boolean isReturnedToPartner, BikeProvider returnPartner, Collection<BikeProvider> allProviders) {
        
        // calculates a new unique orderNo that is based on all of the orders placed so far
        int orderNo = 0;
        for(BikeProvider provider : allProviders) {
            orderNo += provider.getNoOfBookings();
        }
        
        // checks whether the customer is close enough for delivery
        if (isDelivered) {
            if (!customer.getAddress().isNearTo(provider.getStoreAddress())) {
                System.out.println("You are not close enough for delivery!");
                return null;
            }
        }
        
        Booking returnedBooking = new Booking(orderNo, customer, this, LocalDate.now(), isDelivered, isReturnedToPartner, returnPartner);
        provider.addBooking(returnedBooking);
        
        for(Bike currentBike : suitableBikes) {
            currentBike.addReservedDates(datesChosen);
        }
        return returnedBooking;
    }
    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((datesChosen == null) ? 0 : datesChosen.hashCode());
        result = prime * result + ((deposit == null) ? 0 : deposit.hashCode());
        result = prime * result + ((provider == null) ? 0 : provider.hashCode());
        result = prime * result + quoteID;
        result = prime * result + Arrays.hashCode(suitableBikes);
        result = prime * result + ((totalCost == null) ? 0 : totalCost.hashCode());
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
        Quote other = (Quote) obj;
        if (datesChosen == null) {
            if (other.datesChosen != null)
                return false;
        } else if (!datesChosen.equals(other.datesChosen))
            return false;
        if (deposit == null) {
            if (other.deposit != null)
                return false;
        } else if (!deposit.equals(other.deposit))
            return false;
        if (provider == null) {
            if (other.provider != null)
                return false;
        } else if (!provider.equals(other.provider))
            return false;
        if (quoteID != other.quoteID)
            return false;
        if (!Arrays.equals(suitableBikes, other.suitableBikes))
            return false;
        if (totalCost == null) {
            if (other.totalCost != null)
                return false;
        } else if (!totalCost.equals(other.totalCost))
            return false;
        return true;
    }
    
    
}