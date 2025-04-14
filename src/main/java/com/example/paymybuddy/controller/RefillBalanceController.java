package com.example.paymybuddy.controller;

import com.example.paymybuddy.dto.RefillBalanceDTO;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.RefillBalanceService;
import com.example.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Transactional
@RequestMapping("/balance")
public class RefillBalanceController {

    private final Logger logger = LoggerFactory.getLogger(RefillBalanceController.class);

    @Autowired
    RefillBalanceService refillBalanceService;

    @Autowired
    UserService userService;

    @Transactional
    @GetMapping
    public String displayBalance(@ModelAttribute("user") User user, @ModelAttribute("balanceDTO") RefillBalanceDTO balanceDTO, Model model) {
         user = userService.getLoggedUserbyEmail();
        model.addAttribute("user", user);
        logger.info("Controller: Success. HTTP GET request received at /balance URL. " +
                "Current user: "+ user.getEmail());
        return"balance";
    }

    @Transactional
    @PostMapping("/save")
    public String addMoneyToBalance (@ModelAttribute("user") User user,@ModelAttribute("balanceDTO")RefillBalanceDTO balanceDTO){
        if (balanceDTO.getRefillAmount()>0)
        refillBalanceService.addMoneyToBalance(balanceDTO);
       logger.info("Controller: Success. HTTP POST request received at /balance/save URL. " +
               "Account has been refilled: " + balanceDTO.getRefillAmount()+"$");
       return "redirect:/balance";
    }
}
