package uk.ac.ed.bikerental;

import java.math.BigDecimal;

public class DepositInfo {

    private final BigDecimal depositAmount;
	private DepositStatus status;
	
	public DepositInfo(BigDecimal depositAmount) {
		assert depositAmount.compareTo(BigDecimal.ZERO) >= 0;
		this.depositAmount = depositAmount;
		this.status = DepositStatus.NotYetReceived;
	}
	
	public DepositStatus getDepositStatus() {
		return status;
	}
	
	public BigDecimal getDepositAmount() {
		return depositAmount;
	}
	
	public void updateDepositStatus(DepositStatus newStatus) {
		this.status = newStatus;
	}
	
	// functions for equality testing
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((depositAmount == null) ? 0 : depositAmount.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
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
        DepositInfo other = (DepositInfo) obj;
        if (depositAmount == null) {
            if (other.depositAmount != null)
                return false;
        } else if (!depositAmount.equals(other.depositAmount))
            return false;
        if (status != other.status)
            return false;
        return true;
    }
	
}