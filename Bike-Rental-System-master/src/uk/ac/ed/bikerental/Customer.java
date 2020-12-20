package uk.ac.ed.bikerental;

public class Customer {
    
    private final String fullName;
    private final Location address;
    private final String phoneNo;

    public Customer(String fullName, Location address, String phoneNo) {
    	assert !fullName.equals(null);
    	assert !phoneNo.equals(null);
        this.fullName = fullName;
        this.address = address;
        this.phoneNo = phoneNo;
    }

    public String getFullName() {
        return fullName;
    }

    public Location getAddress() {
        return address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}
