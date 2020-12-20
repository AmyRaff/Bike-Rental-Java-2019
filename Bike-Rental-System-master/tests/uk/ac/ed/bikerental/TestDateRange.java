package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestDateRange {
    private DateRange dateRange1, dateRange2, dateRange3,
                      dateRange4, dateRange5, dateRange6;

    @BeforeEach
    void setUp() throws Exception {
        this.dateRange1 = new DateRange(LocalDate.of(2019, 1, 7),
                                LocalDate.of(2019, 1, 10));
        this.dateRange2 = new DateRange(LocalDate.of(2019, 1, 5),
                                LocalDate.of(2019, 1, 23));
        this.dateRange3 = new DateRange(LocalDate.of(2015, 1, 7),
                                LocalDate.of(2018, 1, 10));
        this.dateRange4 = new DateRange(LocalDate.of(2019, 1, 20),
                                LocalDate.of(2019, 1, 27));
        this.dateRange5 = new DateRange(LocalDate.of(2019, 1, 1),
                                LocalDate.of(2019, 1, 6));
        this.dateRange6 = new DateRange(LocalDate.of(2019, 1, 7),
                LocalDate.of(2019, 1, 10));
    }

    @Test
    void testToYears1() {
        assertEquals(0, this.dateRange1.toYears());
    }

    @Test
    void testToYears3() {
        assertEquals(3, this.dateRange3.toYears());
    }

    @Test
    void testToDays1() {
        assertEquals(3, this.dateRange1.toDays());
    }

    @Test
    void testToDays2() {
        assertEquals(18, this.dateRange2.toDays());
    }
    
    @Test
    // one range is contained in the other
    void testOverlapsTrue1() {
        assertTrue(dateRange1.overlaps(dateRange2));
        assertTrue(dateRange2.overlaps(dateRange1));
    }
    
    @Test
    // the start of one range is before the end of the other
    void testOverlapsTrue2() {
        assertTrue(dateRange4.overlaps(dateRange2));
        assertTrue(dateRange2.overlaps(dateRange4));
    }
    
    @Test
    // the end of one range is after the start of the other
    void testOverlapsTrue3() {
        assertTrue(dateRange5.overlaps(dateRange2));
        assertTrue(dateRange2.overlaps(dateRange5));
    }
    
    @Test
    // checking that equal date ranges overlap
    void testOverlapsTrue4() {
        assertTrue(dateRange6.overlaps(dateRange1));
        assertTrue(dateRange1.overlaps(dateRange6));
    }
    

    @Test
    // the ranges don't overlap
    void testOverlapsFalse() {
    	assertFalse(dateRange1.overlaps(dateRange3));
    	assertFalse(dateRange1.overlaps(dateRange3));
    }
    
    @Test
    // tests date extension works as required
    void testExtend1() {
        dateRange1.extendDates();
        assertEquals(new DateRange(LocalDate.of(2019, 1, 4),
                LocalDate.of(2019, 1, 13)), dateRange1);
    }
    
    @Test
    void testExtend2(){
        dateRange2.extendDates();
        assertEquals(new DateRange(LocalDate.of(2019, 1, 2),
                LocalDate.of(2019, 1, 26)), dateRange2);
    }

}
