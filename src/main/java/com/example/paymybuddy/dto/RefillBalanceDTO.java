package com.example.paymybuddy.dto;

public class RefillBalanceDTO {

    private long refillId;

    private double refillAmount;

    public RefillBalanceDTO() {
    }

    public RefillBalanceDTO(long refillId, double refillAmount) {
        this.refillId = refillId;
        this.refillAmount = refillAmount;
    }

    public long getRefillId() {
        return refillId;
    }

    public void setRefillId(long balanceId) {
        this.refillId = balanceId;
    }

    public double getRefillAmount() {
        return refillAmount;
    }

    public void setRefillAmount(double balanceAmount) {
        this.refillAmount = balanceAmount;
    }
}
