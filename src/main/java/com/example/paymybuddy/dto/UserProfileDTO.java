package com.example.paymybuddy.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserProfileDTO {

    private String email;

    @NotBlank(message = "Must give a first name")
    @Size(min = 3, max = 30, message = "First name must be between 3 and 12 characters")
    private String firstName;

    @NotBlank(message = "Must give a last name")
    @Size(min = 3, max = 30, message = "Last name must be between 3 and 12 characters")
    private String lastName;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 3, max = 12, message = "Password must be between 3 and 12 characters")
    private String password;

    @NotBlank(message = "Confirm password cannot be blank")
    private String confirmPassword;

    public UserProfileDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
