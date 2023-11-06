package com.example.paymybuddy.service;

import com.example.paymybuddy.dao.IRefillBalanceRepository;
import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.RefillBalanceDTO;
import com.example.paymybuddy.model.RefillBalance;
import com.example.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RefillBalanceService {
    @Autowired
    IRefillBalanceRepository refillBalanceRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    IUserRepository userRepository;

    public RefillBalance addMoneyToBalance (RefillBalanceDTO balanceDTO) {
        User loggedUser = userRepository.findByEmail(securityService.getLoggedUser());

        RefillBalance balance = new RefillBalance();
        balance.setRefillAmount(balanceDTO.getRefillAmount());
        balance.setUser(loggedUser);
        loggedUser.setBalance(loggedUser.getBalance() + balanceDTO.getRefillAmount());

        return refillBalanceRepository.save(balance);
    }
}
