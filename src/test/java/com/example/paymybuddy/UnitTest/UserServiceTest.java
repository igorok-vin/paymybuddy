package com.example.paymybuddy.UnitTest;

import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.UserProfileDTO;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.SecurityService;
import com.example.paymybuddy.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@DisplayName("UnitTest")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    SecurityService securityService;

    @Mock
    IUserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("If new contact created then result was success")
    public void getLoggedUserbyEmailTest() {

        User userForUpdate = new User();
        userForUpdate.setFirstName("Joo");
        userForUpdate.setLastName("Sim");
        userForUpdate.setBalance(242.20);
        userForUpdate.setEmail("john@simons");
        userForUpdate.setPassword("pass");
        userForUpdate.setRole("ROLE_USER");

        when(userRepository.findByEmail(any())).thenReturn(userForUpdate);

        User test = userService.getLoggedUserbyEmail();

        assertEquals("john@simons",test.getEmail());
        assertEquals("pass",test.getPassword());
        assertEquals("Joo",test.getFirstName());
        assertEquals("Sim",test.getLastName());
        assertEquals("ROLE_USER",test.getRole());
    }

    @Test
    @DisplayName("When profile updated then result was success")
    public void updateProfileTest() {
        UserProfileDTO userDTO = new UserProfileDTO();
        userDTO.setEmail("john@simons");
        userDTO.setFirstName("John");
        userDTO.setLastName("Simons");
        userDTO.setPassword("pass");
        userDTO.setConfirmPassword("pass");

        User userForUpdate = new User();
        userForUpdate.setFirstName("Joo");
        userForUpdate.setLastName("Sim");
        userForUpdate.setBalance(242.20);
        userForUpdate.setEmail("john@simons");
        userForUpdate.setPassword("pass");
        userForUpdate.setRole("ROLE_USER");

            User userUpdated = new User();
        userUpdated.setFirstName("John");
        userUpdated.setLastName("Simons");
        userUpdated.setBalance(242.20);
        userUpdated.setEmail("john@simons");
        userUpdated.setPassword("pass");
        userUpdated.setRole("ROLE_USER");;

        when(userRepository.findByEmail(any())).thenReturn(userForUpdate);
        when(passwordEncoder.encode(any())).thenReturn(userUpdated.getPassword());

        when(userRepository.save(any())).thenReturn(userUpdated);

        User test = userService.updateProfile(userDTO);

        assertEquals("john@simons",test.getEmail());
    }

    @Test
    @DisplayName("User constractor test1")
    public void userConstructor1Test(){
        try{
            new User("zack@gmailcom","132");
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Test
    @DisplayName("User constractor test2")
    public void userConstructor2Test(){
        try{
            new User("zack@gmailcom","zack","walker","132",1500.0,"User");
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
    @Test
    @DisplayName("User constractor test3")
    public void userConstructor3Test(){
        try{
            new User("zack","walker","zack@gmailcom","132");
        }catch (Exception e){
            fail(e.getMessage());
        }
    }
}