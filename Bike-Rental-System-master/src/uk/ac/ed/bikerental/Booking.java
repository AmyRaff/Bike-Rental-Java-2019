package uk.ac.ed.bikerental;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;

public class Booking implements Deliverable {
	
	private final int orderNo;
	private final Customer customer;
	private final Quote chosenQuote;
	private final LocalDate paymentDate;
	private final boolean isDelivered;
	private final boolean isReturnedToPartner;
	private final BikeProvider returnPartner;
	private BookingStatus status;
	
	public Booking(int orderNo, Customer customer, Quote chosenQuote, 
			LocalDate paymentDate, boolean isDelivered, 
			boolean isReturnedToPartner, BikeProvider returnPartner) {
		assert orderNo >= 0;
		assert !customer.equals(null);
		assert !chosenQuote.equals(null);
		if (isReturnedToPartner == false) {
			assert Objects.equals(returnPartner, null);
		}
		this.orderNo = orderNo;
		this.customer = customer;
		this.chosenQuote = chosenQuote;
		this.paymentDate = paymentDate;
		this.isDelivered = isDelivered;
		this.isReturnedToPartner = isReturnedToPartner;
		this.returnPartner = returnPartner;
		this.status = BookingStatus.Upcoming;
	}

	public int getOrderNo() {
		return orderNo;
	}
	public Customer getCustomer() {
		return customer;
	}
	public Quote getChosenQuote() {
		return chosenQuote;
	}
	public LocalDate getPaymentDate() {
		return paymentDate;
	}
	public boolean isDelivered() {
		return isDelivered;
	}
	public boolean isReturnedToPartner() {
		return isReturnedToPartner;
	}
	public BikeProvider getReturnPartner() {
		return returnPartner;
	}
	public BookingStatus getBookingStatus() {
		return status;
	}
	
	public void updateBookingStatus(BookingStatus newStatus) {
		this.status = newStatus;
	}
    
    @Override
    public void onPickup() {
        BikeStatus newBikeStatus = null;
        
        switch(status) {
        case Upcoming:
            status = BookingStatus.InProgress;
            newBikeStatus = BikeStatus.InTransitCustomer;
            break;
        case BikesWithPartner:
            newBikeStatus = BikeStatus.InTransitProvider;
            break;
        default:
            break;
        }
        
        if (!newBikeStatus.equals(null)) {
            for(Bike currentBike : chosenQuote.getSuitableBikes()) {
                currentBike.updateBikeStatus(newBikeStatus);
            }
        } 
    }

    @Override
    public void onDropoff() {
        BikeStatus newBikeStatus = null;
        
        switch(status) {
        case InProgress:
            newBikeStatus = BikeStatus.CurrentlyRented;
            break;
        case BikesWithPartner:
            status = BookingStatus.BikesReturned;
            newBikeStatus = BikeStatus.AvailableInStore;
            break;
        default:
            break;
        }
        
        if (!newBikeStatus.equals(null)) {
            for(Bike currentBike : chosenQuote.getSuitableBikes()) {
                currentBike.updateBikeStatus(newBikeStatus);
            }
        }
    }
    
    
	
	// for the customer
	public String produceConfirmation() {
		String line1 = "-----------------------------------------------------------\n";
		String line2 = "Order Number: " + orderNo + "\n";
		String line3 = "Order Summary:\n" + chosenQuote.printBikes();
		String line4 = "Deposit Amount: £" + chosenQuote.getDeposit().getDepositAmount() + "\n";
		String line5 = "Total Cost: £" + chosenQuote.getTotalCost().setScale(2, RoundingMode.HALF_UP) + "\n";
		String line6 = "";
		if (isDelivered) {
			line6 = "Your bikes will be delivered to your home address on " + 
				chosenQuote.getDatesChosen().getStart() + "\n";
		} else {
			line6 = "Please collect your bikes from " + chosenQuote.getProvider().getStoreName() + " on " + 
					chosenQuote.getDatesChosen().getStart() + " at:\n  " +
				chosenQuote.getProvider().getStoreAddress() + "\n";
		}
		String line7 = "";
		if (isReturnedToPartner) {
			line7 = "Please return your bikes to " + 
				returnPartner.getStoreName() + " on " +
				chosenQuote.getDatesChosen().getEnd() + " at:\n  " + 
				returnPartner.getStoreAddress() + "\n";
		} else {
			line7 = "Please return your bikes to the Original Provider " + 
				chosenQuote.getProvider().getStoreName() + " on " +
				chosenQuote.getDatesChosen().getEnd() + " at:\n  " + 
				chosenQuote.getProvider().getStoreAddress() + "\n";
		}
		
		String line8 = "-----------------------------------------------------------\n";
		String output = line1 + line2 + line3 + line4 + line5 + line6
				+ line7 + line8;
		return output;
	}
	
	// For the provider to see their own bookings
	@Override
	public String toString() {
		String line1 = "-----------------------------------------------------------\n";
		String line2 = "Order Number: " + orderNo + "\n";
		String line3 = "Customer Name: " + customer.getFullName() + "\n";
		String line4 = "Booked Dates: " + chosenQuote.getDatesChosen().toString() + "\n";
		String line5 = "Deposit Amount: £" + chosenQuote.getDeposit().getDepositAmount() + "\n";
		String line6 = "Total Cost: £" + chosenQuote.getTotalCost().setScale(2, RoundingMode.HALF_UP) + "\n";
		String line7 = "Bikes Ordered:\n" + chosenQuote.printBikes();
		String line8 = "";
		if (isDelivered) {
			line8 = "Bikes to be delivered to " + customer.getAddress() + "\n";
		} else {
			line8 = "No Delivery - Bikes will be collected by the Customer\n";
		}
		String line9 = "";
		if (isReturnedToPartner) {
			line9 = "Bikes will be returned to your Partner (" + returnPartner.getStoreName() + ")\n";
		} else {
			line9 = "Bikes will be returned to you by the Customer\n";
		}
		String line10 = "  - Booking Status: " + status + "\n";
		String line11 = "  - Deposit Status: " + chosenQuote.getDeposit().getDepositStatus() + "\n";
		String line12 = "-----------------------------------------------------------\n";
		String output = line1 + line2 + line3 + line4 + line5 + line6 + line7 + line8 + line9 + line10 + 
				line11 + line12;
		return output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chosenQuote == null) ? 0 : chosenQuote.hashCode());
		result = prime * result + orderNo;
		result = prime * result + ((paymentDate == null) ? 0 : paymentDate.hashCode());
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
		Booking other = (Booking) obj;
		if (chosenQuote == null) {
			if (other.chosenQuote != null)
				return false;
		} else if (!chosenQuote.equals(other.chosenQuote))
			return false;
		if (orderNo != other.orderNo)
			return false;
		if (paymentDate == null) {
			if (other.paymentDate != null)
				return false;
		} else if (!paymentDate.equals(other.paymentDate))
			return false;
		return true;
	}
	
	
}
