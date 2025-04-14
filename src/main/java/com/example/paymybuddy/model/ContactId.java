package com.example.paymybuddy.model;

import javax.swing.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Class configuration for key composite of the entity Contact
 */
public class ContactId implements Serializable {

    private String userEmail;

    private String contactEmail;

    public ContactId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactId contactId = (ContactId) o;
        return userEmail.equals(contactId.userEmail) && contactEmail.equals(contactId.contactEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, contactEmail);
    }
}
