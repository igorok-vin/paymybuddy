package com.example.paymybuddy.service;

import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.UserProfileDTO;
import com.example.paymybuddy.exception.UserExistInDBException;
import com.example.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class SecurityService {
    @Autowired
    IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public User save(UserProfileDTO userProfileDTO) throws UserExistInDBException {
        User userExistDB = userRepository.findByEmail(userProfileDTO.getEmail());
        if(userExistDB != null) {
            throw new UserExistInDBException("This email already in use");
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
}
