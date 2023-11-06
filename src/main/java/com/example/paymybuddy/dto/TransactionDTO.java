package com.example.paymybuddy.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TransactionDTO {

    private String userReceiver;

    private String email;

    @Min(value = 1, message = "Transaction amount cannot be less than 1$")
    @Max(value = 10000, message = "Transaction amount cannot be greater than 10000$")
    private double amount;

    @NotBlank(message = "Please enter the comment")
    @Size(max = 15, message = "Description transaction should be maximum 15 characters")
    private String description;

    private double fee;

    public TransactionDTO() {
    }

    public TransactionDTO(String userReceiver, String email, String description, double amount, double fee) {
        this.userReceiver = userReceiver;
        this.email = email;
        this.description = description;
        this.amount = amount;
        this.fee = fee;
    }

    public String getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(String userReceiver) {
        this.userReceiver = userReceiver;
    }

    public String getEmail() {
        return email;
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

    public void setEmail(String email) {
        this.email = email;
    }
}
