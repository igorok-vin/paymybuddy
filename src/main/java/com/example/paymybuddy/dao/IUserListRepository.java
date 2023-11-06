package com.example.paymybuddy.dao;

import com.example.paymybuddy.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IUserListRepository extends JpaRepository<User, String> {
    List<User> findByEmail(String email);
}
