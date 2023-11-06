package com.example.paymybuddy.security;

import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       final User userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        UserDetails user = org.springframework.security.core.userdetails.User.withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                        .authorities("USER").build();
        return user;
    }
}
