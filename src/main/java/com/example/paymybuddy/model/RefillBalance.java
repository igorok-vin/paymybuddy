package com.example.paymybuddy.model;

import javax.persistence.*;

@Entity
@Table(name = "refill_balance")
public class RefillBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refill_id")
    private long refillId;

    @Column(name = "refill_amount")
    private double refillAmount;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name="user_email")
    private User user;

    public RefillBalance() {
    }

    public RefillBalance(long refillId, double refillAmount, User user) {
        this.refillId = refillId;
        this.refillAmount = refillAmount;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
