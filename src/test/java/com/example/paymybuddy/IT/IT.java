package com.example.paymybuddy.IT;

import com.example.paymybuddy.dao.IContactRepository;
import com.example.paymybuddy.dao.ITransactionRepository;
import com.example.paymybuddy.dao.IUserListRepository;
import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.UserProfileDTO;
import com.example.paymybuddy.model.Contact;
import com.example.paymybuddy.model.Transaction;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.ContactService;
import com.example.paymybuddy.service.SecurityService;
import com.example.paymybuddy.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"/data-test.sql"})
@DisplayName("Integration Tests")
@AutoConfigureMockMvc
public class IT {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ContactService contactService;

    @Autowired
    UserService userService;

    @Autowired
    IUserListRepository userListRepository;

    @Autowired
    ITransactionRepository transactionRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    IContactRepository contactRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    @DisplayName("If new contact created then result was success")
    public void ifNewContactSaved_thenSuccessIT() {
        Contact newContact1 = new Contact("john@gmail.com", "kate@gmail.com");
        Contact newContact2 = new Contact("tom@gmail.com", "stella@gmail.com");
        contactRepository.save(newContact1);
        contactRepository.save(newContact2);

        /*3 contacts will be created through data-test.sql during the test. So, in total 5. */
        assertEquals(6, contactRepository.findAll().size());
        System.out.println(contactRepository.findAll());
    }

    @Test
    @Transactional
    @DisplayName("When given user found by email then result success")
    public void findUserByEmailIT() {
        User userSavedTest = new User();
        userSavedTest.setFirstName("Jasper");
        userSavedTest.setLastName("Jordan");
        userSavedTest.setEmail("jasper@gmail.com");
        userSavedTest.setPassword("12345");
        userSavedTest.setBalance(5500.25);
        userSavedTest.setRole("USER");
        userRepository.save(userSavedTest);
        ;
        userSavedTest = userRepository.findByEmail("jasper@gmail.com");

        assertThat(userSavedTest).isNotNull();
        assertThat(userSavedTest.getEmail()).isEqualTo("jasper@gmail.com");
        assertThat(userSavedTest.getBalance()).isEqualTo(5500.25);
        assertThat(userSavedTest.getRole()).isEqualTo("USER");
    }

    @Test
    @Transactional
    @DisplayName("When new user signed up then result success")
    public void signUpNewUserIT() throws Exception {
        String firstName = "Oliver";
        String lastName = "Smith";
        String email = "oliver@gmail.com";
        String password = "54321";

        mockMvc.perform(post("/sign_up")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("email", email)
                .param("password", password))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/login?success"));
    }

    @Test
    @Transactional
    @DisplayName("When user login then will be returned home page")
    @WithMockUser(username = "igor@gmail.com", password = "123")
    public void getHomePageIT() throws Exception {
        mockMvc.perform(get("/home"))

                .andExpect(status().isOk())
                .andExpect(view().name("/home"))
                .andExpect(model().hasNoErrors())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("When user login then will be returned contacts page")
    @WithMockUser(username = "igor@gmail.com", password = "123")
    public void getContactPageIT() throws Exception {
        mockMvc.perform(get("/contacts"))

                .andExpect(status().isOk())
                .andExpect(view().name("/contacts"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("getAllUsersThatDoNotBelongToLoggedUser"))
                .andExpect(model().attributeExists("contactList"))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("Get list of contacts of logged in user")
    @WithMockUser(username = "john@gmail.com", password = "123")
    public void getListofContactsIT() throws Exception {
        mockMvc.perform(get("/contacts")
                .param("page", String.valueOf(1))
                .param("size", String.valueOf(2)))

                .andExpect(status().isOk())
                .andExpect(view().name("/contacts"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("getAllUsersThatDoNotBelongToLoggedUser", "contactList", "contactsOnPage", "pageNumber", "contactsOnPageTotalPages", "contactListLoggedUserForDelete"))
                .andExpect(model().attribute("pageNumber", is(1)))
                .andExpect(model().attribute("contactsOnPage", Matchers.hasProperty("totalElements", equalTo(3L))))
                .andExpect(model().attribute("contactsOnPageTotalPages", is(2)))
                .andExpect(model().attribute("contactListLoggedUserForDelete", Matchers.hasSize(3)))
                .andExpect(model().attribute("contactsOnPage", hasItem(hasProperty("firstName", is("Stella")))))
                .andExpect(model().attribute("contactsOnPage", hasItem(hasProperty("lastName", is("Miller")))))
                .andExpect(model().attribute("contactsOnPage", hasItem(hasProperty("email", is("stella@gmail.com")))))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("Add new contact to the list of contacts of logged user")
    @WithMockUser(username = "john@gmail.com", password = "123")
    public void addContactIT() throws Exception {
        User userSavedTest = new User();
        userSavedTest.setFirstName("David");
        userSavedTest.setLastName("Woodwick");
        userSavedTest.setEmail("david@gmail.com");
        userSavedTest.setPassword("123");
        userSavedTest.setBalance(5500.25);
        userSavedTest.setRole("USER");
        userRepository.save(userSavedTest);

        mockMvc.perform(post("/contacts/save")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("email", "david@gmail.com"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/contacts"))
                .andExpect(model().hasNoErrors());
    }

    @Test
    @Transactional
    @DisplayName("Delete contact from the list of contacts of logged user")
    @WithMockUser(username = "john@gmail.com", password = "123")
    public void deleteContactIT() throws Exception {
        User userSavedTest = new User();
        userSavedTest.setFirstName("Erica");
        userSavedTest.setLastName("Young");
        userSavedTest.setEmail("erica@gmail.com");
        userSavedTest.setPassword("123");
        userSavedTest.setBalance(5500.25);
        userSavedTest.setRole("USER");
        userRepository.save(userSavedTest);

        contactService.addContactToUserList("erica@gmail.com");

        mockMvc.perform(post("/contacts/delete")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("email", "erica@gmail.com"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/contacts"))
                .andExpect(model().hasNoErrors());
    }


    @Test
    @Transactional
    @DisplayName("When user login then will be returned profile page")
    @WithMockUser(username = "lily@gmail.com", password = "123")
    public void getProfilePageIT() throws Exception {
        mockMvc.perform(get("/profile"))

                .andExpect(status().isOk())
                .andExpect(view().name("/profile"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("userProfileDTO"))
                .andExpect(model().attribute("user", Matchers.hasProperty("firstName", equalTo("Lily"))))
                .andExpect(model().attribute("user", Matchers.hasProperty("lastName", equalTo("Russell"))))
                .andExpect(model().attribute("user", Matchers.hasProperty("email", equalTo("lily@gmail.com"))))
                .andExpect(model().attribute("user", Matchers.hasProperty("password", equalTo("$2a$10$R8VKeu68F8r8VXsThxRV7OsgQqsqTQSVvMRzPqqQVz5AeSKNh1Ymy"))))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("When logged user change own profile then user profile data will be updated")
    @WithMockUser(username = "max@gmail.com", password = "123")
    public void userProfileUpdateIT() throws Exception {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setFirstName("Maxim");
        dto.setLastName("Mad");
        dto.setPassword("456");
        dto.setConfirmPassword("456");
        mockMvc.perform(MockMvcRequestBuilders.post("/profile/update")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", dto.getFirstName())
                .param("lastName", dto.getLastName())
                .param("email", "max@gmail.com")
                .param("password", dto.getPassword())
                .param("confirmPassword", dto.getConfirmPassword()))

                .andExpect(view().name("redirect:/profile?success"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().hasNoErrors());
    }

    @Test
    @Transactional
    @DisplayName("When logged user change own profile with name less then 3 characters then field size error")
    @WithMockUser(username = "max@gmail.com", password = "123")
    public void userProfileUpdateErrorSizeIT() throws Exception {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setFirstName("M ");
        dto.setLastName("Mad");
        dto.setPassword("456");
        dto.setConfirmPassword("456");
        mockMvc.perform(MockMvcRequestBuilders.post("/profile/update")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", dto.getFirstName())
                .param("lastName", dto.getLastName())
                .param("email", "max@gmail.com")
                .param("password", dto.getPassword())
                .param("confirmPassword", dto.getConfirmPassword()))

                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode("userProfileDTO", "firstName", "Size"))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("When logged user changes own name in profile on 3 spaces then blank field error")
    @WithMockUser(username = "max@gmail.com", password = "123")
    public void userProfileUpdateErrorNotBlankIT() throws Exception {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setFirstName("   ");
        dto.setLastName("Mad");
        dto.setPassword("456");
        dto.setConfirmPassword("456");
        mockMvc.perform(MockMvcRequestBuilders.post("/profile/update")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("firstName", dto.getFirstName())
                .param("lastName", dto.getLastName())
                .param("email", "max@gmail.com")
                .param("password", dto.getPassword())
                .param("confirmPassword", dto.getConfirmPassword()))

                .andExpect(model().hasErrors())
                .andExpect(model().errorCount(1))
                .andExpect(model().attributeHasFieldErrorCode("userProfileDTO", "firstName", "NotBlank"))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("When user login then will be returned balance page")
    @WithMockUser(username = "igor@gmail.com", password = "123")
    public void displayBalancePageIT() throws Exception {
        mockMvc.perform(get("/balance"))

                .andExpect(status().isOk())
                .andExpect(view().name("/balance"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().size(2))
                .andExpect(model().attributeExists("user", "balanceDTO"))
                .andExpect(model().attribute("user", Matchers.hasProperty("balance", is(1400.37))))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("When logged user add money to own balance then result success")
    @WithMockUser(username = "igor@gmail.com", password = "123")
    public void addMoneyToBalancePageIT() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/balance/save")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("balance", String.valueOf(1400.37))
                .param("refillAmount", String.valueOf(100.00)))

                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/balance"))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("When user login then will be returned transaction page")
    @WithMockUser(username = "igor@gmail.com", password = "123")
    public void displayTransactionPageIT() throws Exception {
        mockMvc.perform(get("/transaction"))

                .andExpect(status().isOk())
                .andExpect(view().name("transaction"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().size(5))
                .andExpect(model().attributeExists("transactionDTO", "contactListLoggedUser", "transactionListPage", "pageNumber", "transactionListPageTotalPages"))
                .andExpect(model().attribute("contactListLoggedUser", Matchers.hasItem(Matchers.hasProperty("contactEmail", equalTo("john@gmail.com")))))
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("When logged user create a transaction then result success")
    @WithMockUser(username = "igor@gmail.com", password = "123")
    public void createTransactionIT() throws Exception {

        List<User> allUsers = contactService.getAllUsersThatDoNotBelongToLoggedUser();
        List<User> loggedUserList = userListRepository.findByEmail(securityService.getLoggedUser());

        User userSender = loggedUserList.stream().filter(user1 -> "igor@gmail.com".equals(user1.getEmail())).findAny().orElse(null);

        User userReceiver = allUsers.stream().filter(user1 -> "john@gmail.com".equals(user1.getEmail())).findAny().orElse(null);

        Transaction transaction = new Transaction();
        transaction.setUserReceiverEmail(userReceiver);
        transaction.setAmount(100.00);
        transaction.setDescription("Gift");
        transaction.setUserSenderEmail(userSender);

        mockMvc.perform(post("/transaction")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user("igor@gmail.com").password("123"))
                .param("userReceiver",transaction.getUserReceiverEmail().getEmail())
                .param("amount", String.valueOf(transaction.getAmount()))
                .param("description", transaction.getDescription())
                .param("password", transaction.getUserSenderEmail().getPassword(), transaction.getUserReceiverEmail().getPassword()))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/transaction"))
                .andExpect(model().hasNoErrors())
                .andDo(print());
    }
}
