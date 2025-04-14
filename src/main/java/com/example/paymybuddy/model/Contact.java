package com.example.paymybuddy.model;

import javax.persistence.*;

@Entity
@IdClass(ContactId.class)
public class Contact {
    @Id
    @Column(name = "user_email")
    private String userEmail;

    @Id
    @Column(name = "contact_email")
    private String contactEmail;

    public Contact() {
    }

    public Contact(String userEmail, String contactEmail) {
        this.userEmail = userEmail;
        this.contactEmail = contactEmail;
    }

    public Contact(String email) {
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String firstName) {
        this.userEmail = firstName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String userEmail) {
        this.contactEmail = userEmail;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "userEmail='" + userEmail + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
