package com.example.paymybuddy.controller;

import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @Transactional
    @GetMapping("/home")
    public String home(Model model) {
        User user = userService.getLoggedUserbyEmail();
        model.addAttribute("userName", user.getFirstName());
        logger.info("Controller: Success. HTTP GET request received at /home URL." +
                "Curent user: " + user.getEmail());
        return "/home";
    }
}
