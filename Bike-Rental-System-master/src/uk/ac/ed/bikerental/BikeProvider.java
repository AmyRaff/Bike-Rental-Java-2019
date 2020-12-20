package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class BikeProvider {

    private final int providerID;
    private final String storeName;
    private final Location storeAddress;
    private BigDecimal depositRate;
    private ArrayList<Bike> stockList;
    public PricingPolicy pricingPolicy;
    public ValuationPolicy valuationPolicy;
    private ArrayList<BikeProvider> partners;
    private ArrayList<Booking> bookings;

	public BikeProvider(int providerID, String storeName, Location storeAddress, 
			BigDecimal depositRate, PricingPolicy pricingPolicy,
			ValuationPolicy valuationPolicy) {
		assert depositRate.compareTo(BigDecimal.ONE) == -1;
		assert depositRate.compareTo(BigDecimal.ZERO) == 1;
	    this.providerID = providerID;
	    this.storeName = storeName;
	    this.storeAddress = storeAddress;
	    this.depositRate = depositRate;
	    this.pricingPolicy = pricingPolicy;
	    this.valuationPolicy = valuationPolicy;
	    this.stockList = new ArrayList<Bike>();
	    this.partners = new ArrayList<BikeProvider>();
	    this.bookings = new ArrayList<Booking>();
	}

    public int getProviderID() {
        return providerID;
    }
    public String getStoreName() {
        return storeName;
    }
    public Location getStoreAddress() {
        return storeAddress;
    }
    
    public BigDecimal getDepositRate() {
        return depositRate;
    }
    
    public Collection<Bike> getStockList() {
        return stockList;
    }
    
    public Collection<BikeProvider> getPartners(){
        return partners;
    }
    
    public int getNoOfBookings() {
        return bookings.size();
    }

    public void addBike(Bike newBike) {
        stockList.add(newBike);
    }
    
    public void addPartner(BikeProvider newPartner) {
        partners.add(newPartner);
        newPartner.partners.add(this);
    }

    public void addBooking(Booking newBooking) {
        bookings.add(newBooking);
        if (newBooking.isReturnedToPartner()) {
        	newBooking.getReturnPartner().bookings.add(newBooking);
        }
    }

    public void viewBookings() {
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println(bookings.get(i));
        }
    }
    
    // updates the booking status and bike statuses from an orderNo
    public boolean updateBooking(int orderNo) {
    	Booking relevantBooking = null;
    	for (Booking booking : bookings) {
    		if(booking.getOrderNo() == orderNo) {
    			relevantBooking = booking;
    			break;
    		}
    	}

    	if(relevantBooking != null) {
    		BikeProvider ogProvider = relevantBooking.getChosenQuote().getProvider();
        	BikeProvider partnerProvider = relevantBooking.getReturnPartner();
        	
    		if (this.equals(ogProvider)) {
    			relevantBooking.updateBookingStatus(BookingStatus.BikesReturned);
    			for(Bike currBike : relevantBooking.getChosenQuote().getSuitableBikes()) {
    				currBike.updateBikeStatus(BikeStatus.AvailableInStore);
    			}
    			return true;
    		}
    		else if (partnerProvider != null){
    			if (this.equals(partnerProvider)) {
        			relevantBooking.updateBookingStatus(BookingStatus.BikesWithPartner);
        			for(Bike currBike : relevantBooking.getChosenQuote().getSuitableBikes()) {
        				currBike.updateBikeStatus(BikeStatus.ReturnedToPartner);
        			}
        			return true;
    			}
    		}
    	}
    	return false;
    }
    
    // updates the deposit status from an orderNo
    public boolean updateBookingDeposit(int orderNo, DepositStatus depositStatus) {
        Booking relevantBooking = null;
        for (Booking booking : bookings) {
            if(booking.getOrderNo() == orderNo) {
                relevantBooking = booking;
                break;
            }
        }
        
        // checks that there was a booking found for the given orderNo
        if(relevantBooking != null) {
            if (this.equals(relevantBooking.getChosenQuote().getProvider())){
                relevantBooking.getChosenQuote().getDeposit().updateDepositStatus(depositStatus);
                return true;
            }
            else if (relevantBooking.isReturnedToPartner()) {
                if(relevantBooking.getReturnPartner() == null) {
                    throw new NullPointerException("Booking is returned to partner but no partner is defined");
                }
               if(this.equals(relevantBooking.getReturnPartner())) {
                   relevantBooking.getChosenQuote().getDeposit().updateDepositStatus(depositStatus);
                   return true;
               }
            }  
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + providerID;
        result = prime * result + ((storeName == null) ? 0 : storeName.hashCode());
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
        BikeProvider other = (BikeProvider) obj;
        if (providerID != other.providerID)
            return false;
        if (storeName == null) {
            if (other.storeName != null)
                return false;
        } else if (!storeName.equals(other.storeName))
            return false;
        return true;
    }
    
}