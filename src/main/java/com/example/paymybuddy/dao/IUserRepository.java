package com.example.paymybuddy.dao;

import com.example.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, String>{

     User findByEmail(String email);
}
