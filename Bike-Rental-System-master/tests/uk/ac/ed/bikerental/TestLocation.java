package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class TestLocation {
    
    private Location location1, location2, location3;
    
    @BeforeEach
    void setUp() throws Exception {
        this.location1 = new Location("EH5 4BW","24 Bike Lane");
        this.location2 = new Location("EH8 6HN","7 Glorp Street");
        this.location3 = new Location("PA3 5EE","1444 Big Street Avenue");
    }
    
    // testing isNearTo() works correctly
    @Test
    void NearLocation1() {
        assertTrue(location1.isNearTo(location2));
    }
    
    @Test
    void NearLocation2() {
        assertTrue(location2.isNearTo(location1));
    }
    
    @Test
    void FarLocation1() {
        assertFalse(location3.isNearTo(location1));
    }
    
    @Test
    void FarLocation2() {
        assertFalse(location2.isNearTo(location3));
    }

}
