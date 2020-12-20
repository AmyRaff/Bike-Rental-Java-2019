package uk.ac.ed.bikerental;

public class Location {
    private String postcode;
    private String address;
    
    public Location(String postcode, String address) {
        assert postcode.length() >= 6;
        this.postcode = postcode;
        this.address = address;
    }
    
    public boolean isNearTo(Location other) {
       String otherPostcode = other.getPostcode();
       if (postcode.substring(0, 2).equals(otherPostcode.substring(0,2))) {
           return true;
       }
        return false;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getAddress() {
        return address;
    }
    
    @Override
    public String toString() {
        return address + ", " + postcode;
    }
}
