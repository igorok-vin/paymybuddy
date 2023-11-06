package com.example.paymybuddy.UnitTest;

import com.example.paymybuddy.dao.IRefillBalanceRepository;
import com.example.paymybuddy.dao.IUserRepository;
import com.example.paymybuddy.dto.RefillBalanceDTO;
import com.example.paymybuddy.model.RefillBalance;
import com.example.paymybuddy.model.User;
import com.example.paymybuddy.service.RefillBalanceService;
import com.example.paymybuddy.service.SecurityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RefillBalanceServiceTest {

    @Mock
    IRefillBalanceRepository refillBalanceRepository;

    @Mock
    SecurityService securityService;

    @Mock
    IUserRepository userRepository;

    @InjectMocks
    RefillBalanceService refillBalanceService;

    @Test
    public void addMoneyToBalanceTest() {
        String loggedUser = "john@simons";

        User userBeforeUpdate = new User();
        userBeforeUpdate.setFirstName("John");
        userBeforeUpdate.setLastName("Simons");
        userBeforeUpdate.setBalance(242.20);
        userBeforeUpdate.setEmail(loggedUser);
        userBeforeUpdate.setPassword("pass");
        userBeforeUpdate.setRole("ROLE_USER");

        RefillBalanceDTO refillBalanceDTO = new RefillBalanceDTO();
        refillBalanceDTO.setRefillId(2);
        refillBalanceDTO.setRefillAmount(100.00);

        User userUpdated = new User();
        userUpdated.setFirstName("John");
        userUpdated.setLastName("Simons");
        userUpdated.setBalance(342.20);
        userUpdated.setEmail(loggedUser);
        userUpdated.setPassword("pass");
        userUpdated.setRole("ROLE_USER");

        RefillBalance refillBalance = new RefillBalance();
        refillBalance.setRefillAmount(100.00);
        refillBalance.setRefillId(2);
        refillBalance.setUser(userBeforeUpdate);

        when(userRepository.findByEmail(any())).thenReturn(userBeforeUpdate);
        when(refillBalanceRepository.save(isA(RefillBalance.class))).thenReturn(refillBalance);

        RefillBalance balanceResult = refillBalanceService.addMoneyToBalance(refillBalanceDTO);

        assertThat(balanceResult.getRefillAmount()).isEqualTo(100);
        assertThat(balanceResult.getRefillId()).isEqualTo(2);
        assertThat(balanceResult.getUser().getBalance()).isEqualTo(342.20);
        assertThat(balanceResult.getUser().getEmail()).isEqualTo(loggedUser);
        verify(refillBalanceRepository).save(isA(RefillBalance.class));
    }

}
