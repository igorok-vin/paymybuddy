package com.example.paymybuddy.UnitTest;

import com.example.paymybuddy.dao.ITransactionRepository;
import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.TransactionDTO;
import com.example.paymybuddy.exception.LowBalanceException;
import com.example.paymybuddy.model.Transaction;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.SecurityService;
import com.example.paymybuddy.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    ITransactionRepository transactionRepository;

    @Mock
    SecurityService securityService;

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    TransactionService transactionService;

    private List<Transaction> transactionList;

    private Pageable pageable;

    @BeforeEach
    public void setUp() {
        pageable = PageRequest.of(1,6);

        User userSender = new User();
        userSender.setEmail("user_sender@email");

        User userReceiver = new User();
        userReceiver.setEmail("user_receiver@email");

        transactionList = new ArrayList<>();
        Transaction transaction1 = new Transaction();
            transaction1.setTransactionId(1);
            transaction1.setAmount(15.0);
            transaction1.setDescription("breakfast");
            transaction1.setDate(LocalDateTime.now());
            transaction1.setUserSenderEmail(userSender);
            transaction1.setUserReceiverEmail(userReceiver);
        Transaction transaction2 = new Transaction();
            transaction2.setTransactionId(2);
            transaction2.setAmount(23.0);
            transaction2.setDescription("dinner");
            transaction2.setDate(LocalDateTime.now());
            transaction2.setUserSenderEmail(userSender);
            transaction2.setUserReceiverEmail(userReceiver);
        Transaction transaction3 = new Transaction();
            transaction3.setTransactionId(1);
            transaction3.setAmount(37.0);
            transaction3.setDescription("supper");
            transaction3.setDate(LocalDateTime.now());
            transaction3.setUserSenderEmail(userSender);
            transaction3.setUserReceiverEmail(userReceiver);
        Transaction transaction4 = new Transaction();
            transaction4.setTransactionId(1);
            transaction4.setAmount(241.0);
            transaction4.setDescription("gift");
            transaction4.setDate(LocalDateTime.now());
            transaction4.setUserSenderEmail(userSender);
            transaction4.setUserReceiverEmail(userReceiver);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        transactionList.add(transaction4);
    }

    @Test
    public void feeCalculationTest() {
        double result = transactionService.feeCalculation(500);
        assertThat(result).isEqualTo(2.5);
    }

    @Test
    public void getPaginatedTransactionListTest() {
        User user = new User();
        user.setFirstName("Joo");
        user.setLastName("Sim");
        user.setBalance(242.20);
        user.setEmail("user_sender@email");
        user.setPassword("pass");
        user.setRole("ROLE_USER");
        Page<Transaction> transactionPage = new PageImpl<>(transactionList);
        when(userRepository.findByEmail(any())).thenReturn(user);
        when(transactionRepository.findTransactionsByUserSenderEmail(any(),any())).thenReturn(transactionPage);

        Page<TransactionDTO> transactions =
                transactionService.getPaginatedTransactionList(pageable);

        verify(userRepository,times(5)).findByEmail(any());
        verify(transactionRepository,times(1)).findTransactionsByUserSenderEmail(any(),any());
        assertThat(transactions.stream().count()).isEqualTo(4);
        assertThat(transactions.getContent().get(0).getAmount()).isEqualTo(15);
        assertThat(transactions.getContent().get(2).getAmount()).isEqualTo(37);
        assertThat(transactions.getContent().get(3).getAmount()).isEqualTo(241);
        assertThat(transactions.getContent().get(3).getDescription()).isEqualTo("gift");
    }

    @Test
    public void createTransactionTest() throws LowBalanceException {
        User userSender = new User();
            userSender.setFirstName("Joo");
            userSender.setLastName("Sim");
            userSender.setBalance(255.20);
            userSender.setEmail("john@sim");
            userSender.setPassword("pass");
            userSender.setRole("ROLE_USER");

        User userReceiver = new User();
            userReceiver.setFirstName("Alice");
            userReceiver.setLastName("Brown");
            userReceiver.setBalance(200.20);
            userReceiver.setEmail("alice@brown");
            userReceiver.setPassword("pass");
            userReceiver.setRole("ROLE_USER");

        TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setUserReceiver(userReceiver.getEmail());
            transactionDTO.setAmount(55.0);
            transactionDTO.setDescription("bonus");

        Transaction transaction = new Transaction();
            transaction.setTransactionId(1);
            transaction.setUserSenderEmail(userSender);
            transaction.setUserReceiverEmail(userReceiver);
            transaction.setAmount(55.0);
            transaction.setDescription("bonus");
            transaction.setFee(0.275);
            transaction.setDate(LocalDateTime.now());

        when(userRepository.findByEmail(any())).thenReturn(userSender);
        when(userRepository.findByEmail(any())).thenReturn(userReceiver);
        when(transactionRepository.save(any())).thenReturn(transaction);

        Transaction createTransaction = transactionService.createTransaction(transactionDTO);

        verify(transactionRepository).save(isA(Transaction.class));
        assertThat(createTransaction.getTransactionId()).isEqualTo(1);
        assertThat(createTransaction.getAmount()).isEqualTo(55.0);
        assertThat(createTransaction.getDescription()).isEqualTo("bonus");
        assertThat(createTransaction.getFee()).isEqualTo(0.275);
        assertThat(createTransaction.getUserSenderEmail().getEmail()).isEqualTo("john@sim");
        assertThat(createTransaction.getUserReceiverEmail().getEmail()).isEqualTo("alice@brown");
    }
}
