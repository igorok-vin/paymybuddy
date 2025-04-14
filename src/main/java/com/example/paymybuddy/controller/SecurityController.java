package com.example.paymybuddy.controller;

import com.example.paymybuddy.dto.UserProfileDTO;
import com.example.paymybuddy.exception.PasswordException;
import com.example.paymybuddy.exception.UserExistInDBException;
import com.example.paymybuddy.service.SecurityService;
import com.example.paymybuddy.validationgroups.ProfileUpdateValidation;
import com.example.paymybuddy.validationgroups.SignUpValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SecurityController {

    private final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    SecurityService securityService;

    @GetMapping("/login")
    public String login() {
        logger.info("Controller: Displaying login form");
        return "login";
    }

    @GetMapping("/signup")
    public String displaySignUpForm(@ModelAttribute("userProfileDTO")UserProfileDTO userProfileDTO) {
        logger.info("Controller: Success. Displaying sign up form at /sign_up");
        return "signup";
    }

    @PostMapping("/signup")
    public String saveNewUser(@Validated(SignUpValidation.class) UserProfileDTO userProfileDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("userProfileDTO", userProfileDTO);
            return "signup";
        }
        try {
            securityService.save(userProfileDTO);
        } catch (UserExistInDBException e) {
            logger.warn("Controller: Error. This email already in use by another user");
            bindingResult.rejectValue("email","EmailAlreadyExist","This email is used");
            return "signup";
        } catch (PasswordException e) {
            logger.warn("Controller: Warning. Passwords not match. HTTP POST request /signup");
            bindingResult.rejectValue("confirmPassword", "", "Passwords not match");
            return "signup";
        }
        model.addAttribute("userProfileDTO",userProfileDTO);
        model.addAttribute("message", "Account successfully registered.");
        logger.info("Controller: Success. HTTP POST request received at /sign_up URL");
        return "redirect:/login?success";
    }
}
