package uk.ac.ed.bikerental;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDepositInfo {
	
	private DepositInfo deposit1;
	
	@BeforeEach
	void setUp() throws Exception {
		this.deposit1 = new DepositInfo(BigDecimal.valueOf(52.36));
	}
	
	@Test
	// tests status updating for deposits
	void testUpdateStatus() {
		deposit1.updateDepositStatus(DepositStatus.Received);
		assertEquals(DepositStatus.Received, deposit1.getDepositStatus());
	}
}
