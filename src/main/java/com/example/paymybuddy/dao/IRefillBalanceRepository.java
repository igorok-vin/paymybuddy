package com.example.paymybuddy.dao;

import com.example.paymybuddy.model.RefillBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRefillBalanceRepository extends JpaRepository<RefillBalance, Long> {
}
