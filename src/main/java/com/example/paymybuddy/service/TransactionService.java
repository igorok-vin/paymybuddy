package com.example.paymybuddy.service;

import com.example.paymybuddy.dao.ITransactionRepository;
import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.TransactionDTO;
import com.example.paymybuddy.exception.LowBalanceException;
import com.example.paymybuddy.model.Transaction;
import com.example.paymybuddy.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Transactional
public class TransactionService {

    @Autowired
    ITransactionRepository transactionRepository;

    @Autowired
    SecurityService securityService;

    @Autowired
    IUserRepository userRepository;

    public Page<TransactionDTO> getPaginatedTransactionList(Pageable pageable) {

        User loggedUser = userRepository.findByEmail(securityService.getLoggedUser());

        Page<Transaction> transactionsListLoggedUser =
                transactionRepository.findTransactionsByUserSenderEmail(loggedUser,pageable);

        long totalitems = transactionsListLoggedUser.getTotalElements();

        return new PageImpl<>(transactionsListLoggedUser.stream().map(transaction -> {
            User user = userRepository.findByEmail(transaction.getUserReceiverEmail().getEmail());
            return new TransactionDTO(user.getFirstName(), user.getEmail(), transaction.getDescription(),
                    transaction.getAmount(),transaction.getFee());
        }).collect(Collectors.toList()), pageable, totalitems);
    }

    @Transactional
    public Transaction createTransaction (TransactionDTO transactionDTO) throws LowBalanceException {
        User userSender = userRepository.findByEmail(securityService.getLoggedUser());
        User userReceiver = userRepository.findByEmail(transactionDTO.getUserReceiver());

        double fee = feeCalculation(transactionDTO.getAmount());

        if(transactionDTO.getAmount()+fee > userSender.getBalance()){
            throw new LowBalanceException("There are not enough funds on the balance to complete the " +
                    "transaction. Current balance account is: " + userSender.getBalance()+"$");
        }

        double updatedBalanceUserSender = (userSender.getBalance())-(transactionDTO.getAmount()+feeCalculation(transactionDTO.getAmount()));
        userSender.setBalance(updatedBalanceUserSender);

        double updatedBalanceUserReceiver = (userReceiver.getBalance())+(transactionDTO.getAmount());
        userReceiver.setBalance(updatedBalanceUserReceiver);

        Transaction transaction = new Transaction();
        transaction.setDate(LocalDateTime.now());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setDescription(transactionDTO.getDescription());
        transaction.setUserSenderEmail(userSender);
        transaction.setUserReceiverEmail(userReceiver);
        transaction.setFee(fee);

        return transactionRepository.save(transaction);
    }

    public double feeCalculation (double amount){
        double feeRate = 0.5;
        return (amount * feeRate)/100;
    }

}
