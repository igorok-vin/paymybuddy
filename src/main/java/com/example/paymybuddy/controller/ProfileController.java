package com.example.paymybuddy.controller;

import com.example.paymybuddy.dto.UserProfileDTO;
import com.example.paymybuddy.exception.PasswordException;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.SecurityService;
import com.example.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @GetMapping
    public String displayProfile(@ModelAttribute("userProfileDTO") UserProfileDTO userProfileDTO,
                                 @ModelAttribute("user") User userLogged, Model model) {
        User user = userService.getLoggedUserbyEmail();
        user.setPassword(user.getPassword());
        user.setEmail(securityService.getLoggedUser());
        model.addAttribute("user", user);
        model.addAttribute("userProfileDTO", userProfileDTO);
        logger.info("Controller: Success. HTTP GET request received at /profile URL. " +
                "Current user: "+user.getEmail());
        return "/profile";
    }

    @PostMapping("/update")
    public String updateProfile(@Valid @ModelAttribute("userProfileDTO") UserProfileDTO userProfileDTO,
                                BindingResult errors, Model model) {
        model.addAttribute("user", userService.getLoggedUserbyEmail());
        if (errors.hasFieldErrors()) {
            model.addAttribute("userProfileDTO", userProfileDTO);
            model.addAttribute("user", userService.getLoggedUserbyEmail());
            logger.error("Controller: Error. Form fields contain errors.  HTTP POST request /contacts/delete URL");
            return "/profile";
        }
        try {
            userService.updateProfile(userProfileDTO);
        } catch (PasswordException e) {
            errors.rejectValue("confirmPassword", "", "Password confirmation not match");
            logger.warn("Controller: Warning. Password confirmation not match. HTTP POST request /profile/update URL");
            return "/profile";
        }
        model.addAttribute("userProfileDTO", userProfileDTO);
        logger.info("Controller: Success. HTTP POST request received at /profile/update URL");
        return "redirect:/profile?success";
    }
}
