package com.example.paymybuddy.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private long transactionId;

    private double amount;

    private String description;

    private double fee;

    private LocalDateTime date;

    @ManyToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_sender")
    private User userSenderEmail;

    @ManyToOne (cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_receiver")
    private User userReceiverEmail;

    public Transaction() {
    }

    public Transaction(long transactionId, double amount, String description, double fee, LocalDateTime date, User userSenderEmail, User userReceiverEmail) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.description = description;
        this.fee = fee;
        this.date = date;
        this.userSenderEmail = userSenderEmail;
        this.userReceiverEmail = userReceiverEmail;
    }

    public Transaction(double v, String gift, String email, String email1) {

    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUserSenderEmail() {
        return userSenderEmail;
    }

    public void setUserSenderEmail(User userSender) {
        this.userSenderEmail = userSender;
    }

    public User getUserReceiverEmail() {
        return userReceiverEmail;
    }

    public void setUserReceiverEmail(User userReceiver) {
        this.userReceiverEmail = userReceiver;
    }
}
