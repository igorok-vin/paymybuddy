package com.example.paymybuddy.controller;

import com.example.paymybuddy.dao.IContactRepository;
import com.example.paymybuddy.dto.TransactionDTO;
import com.example.paymybuddy.exception.LowBalanceException;
import com.example.paymybuddy.model.Contact;
import com.example.paymybuddy.service.ContactService;
import com.example.paymybuddy.service.SecurityService;
import com.example.paymybuddy.service.TransactionService;
import com.example.paymybuddy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    ContactService contactService;

    @Autowired
    IContactRepository contactRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    UserService userService;

    @Autowired
    TransactionService transactionService;

    @GetMapping("/transaction")
    public String displayTransaction(@ModelAttribute("transactionDTO") TransactionDTO transactionDTO, Model model, @RequestParam("page")Optional<Integer> page, @RequestParam("size") Optional<Integer> size ) {

        int pageNumber = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<TransactionDTO> transactionListPage = transactionService.getPaginatedTransactionList(PageRequest.of(pageNumber - 1, pageSize));

        List<Contact> contactListLoggedUser = contactService.loggedUserContactList();

        model.addAttribute("transactionDTO", new TransactionDTO());
        model.addAttribute("contactListLoggedUser", contactListLoggedUser);
        model.addAttribute("transactionListPage", transactionListPage);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("transactionListPageTotalPages", transactionListPage.getTotalPages());
        logger.info("Controller: Success. HTTP GET request received at /transaction URL");
        return "transaction";
    }

    @Transactional
    @PostMapping("/transaction")
    public ModelAndView createTransaction(@Valid @ModelAttribute("transactionDTO") TransactionDTO transactionDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, ModelMap model, @RequestParam("page")Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        int pageNumber = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<TransactionDTO> transactionListPage = transactionService.getPaginatedTransactionList(PageRequest.of(pageNumber - 1, pageSize));

        List<Contact> contactListLoggedUser = contactService.loggedUserContactList();

        if(bindingResult.hasErrors()){
            model.addAttribute("transactionDTO", transactionDTO);
            model.addAttribute("contactListLoggedUser", contactListLoggedUser);
            model.addAttribute("transactionListPage", transactionListPage);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("transactionListPageTotalPages", transactionListPage.getTotalPages());
            redirectAttributes.addAttribute("hasErrors", true);
            logger.error("Controller: Error. Form fields contain errors. HTTP POST request /transaction URL");
            return new ModelAndView("transaction");
        }
        try {
            transactionService.createTransaction(transactionDTO);
        } catch (LowBalanceException e) {
            bindingResult.rejectValue(null,"",e.getMessage());
            model.addAttribute("transactionDTO", transactionDTO);
            model.addAttribute("contactListLoggedUser", contactListLoggedUser);
            model.addAttribute("transactionListPage", transactionListPage);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("transactionListPageTotalPages", transactionListPage.getTotalPages());
            logger.warn("Controller: Warning. There are not enough funds on the balance to complete the transaction");
            return new ModelAndView("transaction");
        }
        model.addAttribute("transactionDTO", transactionDTO);
        model.addAttribute("contactListLoggedUser", contactListLoggedUser);
        model.addAttribute("transactionListPage", transactionListPage);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("transactionListPageTotalPages", transactionListPage.getTotalPages());
        logger.info("Controller: Success. HTTP POST request received at /transaction URL");
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/transaction");
        return new ModelAndView( redirectView);
    }
}
