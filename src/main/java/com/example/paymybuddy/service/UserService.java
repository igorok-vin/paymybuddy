package com.example.paymybuddy.service;

import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.UserProfileDTO;
import com.example.paymybuddy.exception.PasswordException;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.security.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public User getLoggedUserbyEmail() {
        User user = userRepository.findByEmail(securityService.getLoggedUser());
        return user;
    }

    public User updateProfile (UserProfileDTO userProfileDTO) {
        User user = userRepository.findByEmail(securityService.getLoggedUser());
        user.setFirstName(userProfileDTO.getFirstName());
        user.setLastName(userProfileDTO.getLastName());
        user.setEmail(securityService.getLoggedUser());
        if(!userProfileDTO.getConfirmPassword().equals(userProfileDTO.getPassword())){
            throw new PasswordException("Password confirmation not match");
        }
        user.setPassword(passwordEncoder.encode(userProfileDTO.getPassword()));
        return userRepository.save(user);
    }
}
