package com.example.paymybuddy.dao;

import com.example.paymybuddy.model.Transaction;
import com.example.paymybuddy.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, Long> {

    public Page<Transaction> findTransactionsByUserSenderEmail(User userSenderEmail, Pageable pageable);
}
