package com.example.paymybuddy.controller;

import com.example.paymybuddy.dto.UserProfileDTO;
import com.example.paymybuddy.exception.UserExistInDBException;
import com.example.paymybuddy.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class SecurityController {

    private final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    SecurityService securityService;

    @Transactional
    @GetMapping("/login")
    public String login() {
        logger.info("Controller: Displaying login form");
        return "/login";
    }

    @Transactional
    @GetMapping("/sign_up")
    public String displaySignUpForm(UserProfileDTO userProfileDTO, Model model) {
        model.addAttribute("userProfileDTO", userProfileDTO);
        logger.info("Controller: Success. Displaying sign up form at /sign_up");
        return "/sign_up";
    }

    @Transactional
    @PostMapping("/sign_up")
    public String saveNewUser(@Valid @ModelAttribute("userProfileDTO") UserProfileDTO userProfileDTO, BindingResult bindingResult, Model model) {
        try {
            securityService.save(userProfileDTO);
        } catch (UserExistInDBException e) {
            logger.warn("Controller: Error. This email already in use by another user");
            bindingResult.rejectValue(null,"",e.getMessage());
            return "sign_up";
        }
        model.addAttribute("userProfileDTO",userProfileDTO);
        model.addAttribute("message", "Account successfully registered.");
        logger.info("Controller: Success. HTTP POST request received at /sign_up URL");
        return "redirect:/login?success";
    }
}
