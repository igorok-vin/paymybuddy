package com.example.paymybuddy.UnitTest;

import com.example.paymybuddy.dao.IContactRepository;
import com.example.paymybuddy.dao.IUserListRepository;
import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.ContactsListDTO;
import com.example.paymybuddy.exception.ContactExistInDataBaseException;
import com.example.paymybuddy.exception.ContactListLoggedUserForDeleteException;
import com.example.paymybuddy.model.Contact;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.ContactService;
import com.example.paymybuddy.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContactServiceTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserListRepository userListRepository;

    @Mock
    private IContactRepository contactRepository;

    @Mock
    SecurityService securityService;

    @InjectMocks
    ContactService contactService;

    @Test
    public void getAllUsersThatDoNotBelongToLoggedUserTest() {
        User user1 = new User();
        user1.setFirstName("Joo");
        user1.setLastName("Sim");
        user1.setBalance(242.20);
        user1.setEmail("joo@sim.email");
        user1.setPassword("pass");
        user1.setRole("ROLE_USER");

        User user2 = new User();
        user2.setFirstName("John");
        user2.setLastName("Walker");
        user2.setBalance(357.27);
        user2.setEmail("john@walker.email");
        user2.setPassword("pass");
        user2.setRole("ROLE_USER");

        User user3 = new User();
        user3.setFirstName("Alice");
        user3.setLastName("Pot");
        user3.setBalance(431.54);
        user3.setEmail("alice@pot.email");
        user3.setPassword("pass");
        user3.setRole("ROLE_USER");

        User user4 = new User();
        user4.setFirstName("Crystal");
        user4.setLastName("Mad");
        user4.setBalance(874.96);
        user4.setEmail("crystal@mad.email");
        user4.setPassword("pass");
        user4.setRole("ROLE_USER");

        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);
        allUsers.add(user3);
        allUsers.add(user4);

        List<User> loggedUser = new ArrayList<>();
        loggedUser.add(user1);

        when(userRepository.findAll()).thenReturn(allUsers);
        when(userListRepository.findByEmail(any())).thenReturn(loggedUser);

        allUsers.stream().filter(user -> !loggedUser.contains(user))
                .collect(Collectors.toList());

        List<User> userList = contactService.getAllUsersThatDoNotBelongToLoggedUser();

        assertThat(userList.size()).isEqualTo(3);
        assertThat(userList.get(0).getFirstName()).isEqualTo("John");
        assertThat(userList.get(1).getFirstName()).isEqualTo("Alice");
        assertThat(userList.get(2).getFirstName()).isEqualTo("Crystal");
        verify(userRepository, times(1)).findAll();
        verify(userListRepository, times(1)).findByEmail(any());
    }

    @Test
    public void addContactToUserListTest() throws ContactExistInDataBaseException {
        User user1 = new User();
        user1.setFirstName("Joo");
        user1.setLastName("Sim");
        user1.setBalance(242.20);
        user1.setEmail("joo@email");
        user1.setPassword("pass");
        user1.setRole("ROLE_USER");

        String loggedUser = "logged@user";
        String contactEmail = "new_contact@email";

        Contact contact1 = new Contact(loggedUser, "user1@email");
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact1);

        Page<Contact> contactPage = new PageImpl<>(contactList);

        when(securityService.getLoggedUser()).thenReturn(loggedUser);
        Contact contactToAdd = new Contact(loggedUser, contactEmail);
        when(contactRepository.findByUserEmail(any(),any())).thenReturn(contactPage);
        when(contactRepository.save(any())).thenReturn(contactToAdd);


        Contact addContactToUserList = contactService.addContactToUserList(contactEmail);

        assertThat(addContactToUserList.getContactEmail()).isEqualTo("new_contact@email");
        verify(contactRepository).save(isA(Contact.class));
    }

    @Test
    public void deleteByContactEmailTest() throws ContactListLoggedUserForDeleteException {
        contactService.deleteByContactEmail("user@test.email");
        verify(contactRepository).deleteByContactEmail("user@test.email");
    }

    @Test
    public void deleteByContactEmailWhenEmailIsNullTest() {
        assertThrows(ContactListLoggedUserForDeleteException.class,()-> contactService.deleteByContactEmail(null));
    }

    @Test
    public void loggedUserContactListTest() {
        String loggedUser = "logged@user";

        Contact contact1 = new Contact(loggedUser, "user1@email");
        Contact contact2 = new Contact(loggedUser, "user2@email");
        Contact contact3 = new Contact
                ("notlogged@email", "user3@email");
        Contact contact4 = new Contact
                ("notlogged@email", "user4@email");

        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact1);
        contactList.add(contact2);
        contactList.add(contact3);
        contactList.add(contact4);

        List<Contact> contactListLoggedUser = new ArrayList<>();
        contactListLoggedUser.add(contact1);
        contactListLoggedUser.add(contact2);

        when(contactRepository.findByUserEmail(any())).thenReturn(contactListLoggedUser);
        contactList.stream().filter(contact -> contact.getContactEmail().equals(when(securityService.getLoggedUser()).thenReturn(loggedUser)));

        List<Contact> loggedUserContactList = contactService.loggedUserContactList();

        assertThat(loggedUserContactList.size()).isEqualTo(2);
        verify(contactRepository, times(1)).findByUserEmail(any());
    }

    @Test
    public void getPaginatedContactsListTest() {
        User user1 = new User();
        user1.setFirstName("Joo");
        user1.setLastName("Sim");
        user1.setBalance(242.20);
        user1.setEmail("user1@email");
        user1.setPassword("pass");
        user1.setRole("ROLE_USER");

        User user2 = new User();
        user2.setFirstName("John");
        user2.setLastName("Walker");
        user2.setBalance(357.27);
        user2.setEmail("user2@email");
        user2.setPassword("pass");
        user2.setRole("ROLE_USER");

        String loggedUser = "logged@user";

        Contact contact1 = new Contact(loggedUser, "user1@email");
        Contact contact2 = new Contact(loggedUser, "user2@email");
        List<Contact> contactList = new ArrayList<>();
        contactList.add(contact1);
        contactList.add(contact2);
        Page<Contact> contactPage = new PageImpl<>(contactList);
        User user = mock(User.class);

        when(contactRepository.findByUserEmail(any(),any())).thenReturn(contactPage);
        contactList.stream().map(contact -> {
            when(userRepository.findByEmail(any())).thenReturn(user1,user2);
            return new ContactsListDTO(user.getEmail(), user.getFirstName(),user.getLastName());
        }).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(1,3);

        Page<ContactsListDTO> getPaginatedContactsList = contactService.getPaginatedContactsList(pageable);

        assertThat(getPaginatedContactsList.stream().count()).isEqualTo(2);
        verify(userRepository,times(2)).findByEmail(anyString());
    }
}
