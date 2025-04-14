package com.example.paymybuddy.service;

import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.UserProfileDTO;
import com.example.paymybuddy.exception.PasswordException;
import com.example.paymybuddy.exception.UserExistInDBException;
import com.example.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecurityService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public User save(UserProfileDTO userProfileDTO) throws UserExistInDBException, PasswordException {
        User userExistDB = userRepository.findByEmail(userProfileDTO.getEmail());
        if(userExistDB != null) {
            System.out.println("check : This email already in use");
            throw new UserExistInDBException();
        }
        if(!userProfileDTO.getConfirmPassword().equals(userProfileDTO.getPassword())) {
            throw new PasswordException();
        }
        User newUser = new User();
        newUser.setFirstName(userProfileDTO.getFirstName());
        newUser.setLastName(userProfileDTO.getLastName());
        newUser.setEmail(userProfileDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userProfileDTO.getPassword()));
        newUser.setBalance(0.0);
        newUser.setRole("USER");
        return userRepository.save(newUser);
    }

    public String getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        }
        return null;
    }

    public boolean findEmail(String email) {
        User userExistDB = userRepository.findByEmail(email);
        return userExistDB == null;
    }
}
