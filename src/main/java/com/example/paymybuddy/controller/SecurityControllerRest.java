package com.example.paymybuddy.controller;

import com.example.paymybuddy.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityControllerRest {
    @Autowired
    public SecurityService securityService;

    @GetMapping("/users/check_unique_email")
    public String checkEmail(@RequestParam(value = "email", required = false) String email) {
        System.out.println("Method called");
        return securityService.findEmail(email) ? "unique" : "duplicated";
    }
}
