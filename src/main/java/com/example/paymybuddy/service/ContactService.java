package com.example.paymybuddy.service;

import com.example.paymybuddy.dao.IContactRepository;
import com.example.paymybuddy.dao.IUserListRepository;
import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.ContactsListDTO;
import com.example.paymybuddy.exception.ContactExistInDataBaseException;
import com.example.paymybuddy.exception.ContactListLoggedUserForDeleteException;
import com.example.paymybuddy.model.Contact;
import com.example.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IUserListRepository userListRepository;

    @Autowired
    private IContactRepository contactRepository;

    @Autowired
    SecurityService securityService;

    public List<User> getAllUsersThatDoNotBelongToLoggedUser() {
        List<User> usersList = userRepository.findAll();

        List<User> loggedUser = userListRepository.findByEmail(securityService.getLoggedUser());

        List<User> listOfUsersThatDoNotBelongToLoggedUser = usersList.stream().filter(user -> !loggedUser.contains(user))
                .collect(Collectors.toList());

        return listOfUsersThatDoNotBelongToLoggedUser;
    }

    public Contact addContactToUserList(String email) throws ContactExistInDataBaseException {

        Contact contactToAdd = new Contact(securityService.getLoggedUser(), email);

        Page<Contact> contactList= contactRepository.findByUserEmail(securityService.getLoggedUser(), null);

        List<Contact> contactListLoggedUser = contactList.getContent();

        for(Contact contact: contactListLoggedUser){
            if(contact.getContactEmail().equals(email)){
                throw new ContactExistInDataBaseException
                        ("This contact \n"+ email +"\n already exist in you list");
            }
        }
        return contactRepository.save(contactToAdd);
    }

    public void deleteByContactEmail(String email) throws ContactListLoggedUserForDeleteException {
        if(email==null){
            throw new ContactListLoggedUserForDeleteException("List empty");
        }
        contactRepository.deleteByContactEmail(email);
    }

    public List<Contact> loggedUserContactList() {
        List<Contact> contactListLoggedUser = contactRepository.findByUserEmail(securityService.getLoggedUser());

        contactListLoggedUser.stream().filter(contact -> contact.getContactEmail().equals(securityService.getLoggedUser()));

        return contactListLoggedUser;
    }

    public Page<ContactsListDTO> getPaginatedContactsList(Pageable pageable) {
        Page<Contact> contactListLoggedUser = contactRepository.findByUserEmail(securityService.getLoggedUser(), pageable);

        long totalItems = contactListLoggedUser.getTotalElements();

        return new PageImpl<>(contactListLoggedUser.stream().map(contact -> {
            User user = userRepository.findByEmail(contact.getContactEmail());
            return new ContactsListDTO(user.getEmail(), user.getFirstName(), user.getLastName());
        }).collect(Collectors.toList()), pageable, totalItems);
    }
}
