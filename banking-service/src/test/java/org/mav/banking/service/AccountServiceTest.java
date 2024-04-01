package org.mav.banking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mav.banking.dto.AccountCreateRequest;
import org.mav.banking.dto.AccountResponse;
import org.mav.banking.entity.Account;
import org.mav.banking.repository.AccountRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount() {
        AccountCreateRequest request = new AccountCreateRequest("John Doe", "john.doe@example.com", "123-45-6789");
        Account savedAccount = new Account(1L, "John Doe", "john.doe@example.com", "123-45-6789", BigDecimal.ZERO);
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);

        AccountResponse response = accountService.createAccount(request);

        assertNotNull(response);
        assertEquals(savedAccount.getId(), response.getAccountId());
        assertEquals(savedAccount.getCustomerName(), response.getCustomerName());
        assertEquals(savedAccount.getEmail(), response.getEmail());
        assertEquals(0, response.getBalance().compareTo(BigDecimal.ZERO));

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void deposit() {
    }

    @Test
    void getBalance() {
    }
}