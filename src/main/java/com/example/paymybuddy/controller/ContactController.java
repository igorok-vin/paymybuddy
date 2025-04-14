package com.example.paymybuddy.controller;

import com.example.paymybuddy.dao.IContactRepository;
import com.example.paymybuddy.dto.ContactsListDTO;
import com.example.paymybuddy.exception.ContactExistInDataBaseException;
import com.example.paymybuddy.exception.ContactListLoggedUserForDeleteException;
import com.example.paymybuddy.model.Contact;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.ContactService;
import com.example.paymybuddy.service.SecurityService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/contacts")
public class ContactController {

    private final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    ContactService contactService;

    @Autowired
    IContactRepository contactRepository;

    @Autowired
    SecurityService securityService;


    @Transactional
    @GetMapping
    public String displayContacts(@ModelAttribute("contactList")ContactsListDTO contactsList, Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {

        int pageNumber = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<ContactsListDTO> contactsOnPage = contactService.getPaginatedContactsList(PageRequest.of(pageNumber - 1, pageSize));

        List<User> getAllUsersThatDoNotBelongToLoggedUser = contactService.getAllUsersThatDoNotBelongToLoggedUser();

        List<Contact> contactListLoggedUserForDelete = contactService.loggedUserContactList();

        model.addAttribute("getAllUsersThatDoNotBelongToLoggedUser", getAllUsersThatDoNotBelongToLoggedUser);
        model.addAttribute("contactList", contactsList);
        model.addAttribute("contactsOnPage", contactsOnPage);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("contactsOnPageTotalPages", contactsOnPage.getTotalPages());
        model.addAttribute("contactListLoggedUserForDelete",contactListLoggedUserForDelete);
        logger.info("Controller: Success. HTTP GET request received at /contacts URL");
        return "contacts";
    }

    @Transactional
    @PostMapping("/save")
    public String createContact(@Valid @ModelAttribute("contactList") ContactsListDTO contactsList, BindingResult bindingResult, ModelMap model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) throws ContactExistInDataBaseException {

        int pageNumber = page.orElse(1);
        int pageSize = size.orElse(3);

        Page<ContactsListDTO> contactsOnPage = contactService.getPaginatedContactsList(PageRequest.of(pageNumber - 1, pageSize));

        List<User> getAllUsersThatDoNotBelongToLoggedUser = contactService.getAllUsersThatDoNotBelongToLoggedUser();

        List<Contact> contactListLoggedUserForDelete = contactService.loggedUserContactList();

        if(bindingResult.hasErrors()){
            model.addAttribute("getAllUsersThatDoNotBelongToLoggedUser", getAllUsersThatDoNotBelongToLoggedUser);
            model.addAttribute("contactList", contactsList);
            model.addAttribute("contactsOnPage", contactsOnPage);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("contactsOnPageTotalPages", contactsOnPage.getTotalPages());
            model.addAttribute("contactListLoggedUserForDelete",contactListLoggedUserForDelete);
            logger.error("Controller: Error. Fields have error");
            return "contacts";
        }
        try {
            contactService.addContactToUserList(contactsList.getEmail());
        } catch (ContactExistInDataBaseException e){
            bindingResult.rejectValue("", "", e.getMessage());
            model.addAttribute("getAllUsersThatDoNotBelongToLoggedUser", getAllUsersThatDoNotBelongToLoggedUser);
            model.addAttribute("contactList", contactsList);
            model.addAttribute("contactsOnPage", contactsOnPage);
            model.addAttribute("pageNumber", pageNumber);
            model.addAttribute("contactsOnPageTotalPages", contactsOnPage.getTotalPages());
            model.addAttribute("contactListLoggedUserForDelete",contactListLoggedUserForDelete);
            logger.warn("Controller: Warning. Contact already in a contact list");
            return "/contacts";
        }
        model.addAttribute("getAllUsersThatDoNotBelongToLoggedUser", getAllUsersThatDoNotBelongToLoggedUser);
        model.addAttribute("contactList", contactsList);
        model.addAttribute("contactsOnPage", contactsOnPage);
        model.addAttribute("pageNumber", pageNumber);
        model.addAttribute("contactsOnPageTotalPages", contactsOnPage.getTotalPages());
        model.addAttribute("contactListLoggedUserForDelete",contactListLoggedUserForDelete);

        logger.info("Controller: Success. HTTP POST request received at /contacts/save URL");
        return "redirect:/contacts";
    }

    @Transactional
    @PostMapping("/delete")
    public String deleteContact(@RequestParam(value = "email", required = false) String email) throws ContactListLoggedUserForDeleteException {
        if(email==null){
            logger.info("Controller: Error. Attempting to remove a friend from an empty list. HTTP POST request /contacts/delete URL");
            return "redirect:/contacts";
        }
            contactService.deleteByContactEmail(email);
        logger.info("Controller: Success. HTTP POST request received at /contacts/delete URL. Contact " + email+ " has been removed");
        return "redirect:/contacts";
    }
}
