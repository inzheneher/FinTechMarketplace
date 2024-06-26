package org.mav.banking.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mav.banking.dto.AccountCreateRequest;
import org.mav.banking.dto.AccountResponse;
import org.mav.banking.dto.BalanceResponse;
import org.mav.banking.entity.Account;
import org.mav.banking.repository.AccountRepository;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createAccount() {
        AccountCreateRequest request = new AccountCreateRequest(
                "John Doe",
                "john.doe@example.com",
                "$ecretpa$$w0rd",
                "123-45-6789");
        Account savedAccount = new Account(
                1L,
                "John Doe",
                "john.doe@example.com",
                "$ecretpa$$w0rd",
                "123-45-6789",
                BigDecimal.ZERO,
                Set.of());
        when(accountRepository.save(any(Account.class))).thenReturn(savedAccount);
        when(passwordEncoder.encode(anyString())).thenReturn("$ecretpa$$w0rd");

        AccountResponse response = accountService.createAccount(request);

        assertNotNull(response);
        assertEquals(savedAccount.getId(), response.accountId());
        assertEquals(savedAccount.getUsername(), response.username());
        assertEquals(savedAccount.getEmail(), response.email());
        assertEquals(0, response.balance().compareTo(BigDecimal.ZERO));

        verify(accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    void deleteAccount() {
        Long accountId = 1L;
        when(accountRepository.existsById(accountId)).thenReturn(true);
        accountService.deleteAccount(accountId);
        verify(accountRepository, times(1)).deleteById(accountId);
    }

    @Test
    void deposit() {

        Long accountId = 1L;
        BigDecimal initAmount = BigDecimal.valueOf(2);
        BigDecimal depositAmount = BigDecimal.valueOf(100);
        BigDecimal expectedAmount = initAmount.add(depositAmount);

        Account initAccount = new Account(
                accountId,
                "John Doe",
                "john.doe@example.com",
                "$ecretpa$$w0rd",
                "123-45-6789",
                initAmount,
                Set.of());
        when(accountRepository.findById(eq(accountId))).thenReturn(Optional.of(initAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        AccountResponse response = accountService.deposit(accountId, depositAmount);

        assertNotNull(response);
        assertEquals(accountId, response.accountId());
        assertEquals(0, expectedAmount.compareTo(response.balance()));

        verify(accountRepository, times(1)).save(any(Account.class));

        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accountCaptor.capture());
        Account captoredAccount = accountCaptor.getValue();
        assertEquals(expectedAmount, captoredAccount.getBalance());
    }

    @Test
    void depositWhenAccountDoesNotExist() {
        Long nonExistentAccountId = 1L;
        BigDecimal depositAmount = BigDecimal.valueOf(100);

        when(accountRepository.findById(eq(nonExistentAccountId))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountService.deposit(nonExistentAccountId, depositAmount));

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void getBalance() {
        Long accountId = 1L;
        BigDecimal depositAmount = BigDecimal.valueOf(100);
        Account account = new Account(
                accountId,
                "John Doe",
                "john.doe@example.com",
                "$ecretpa$$w0rd",
                "123-45-6789",
                depositAmount,
                Set.of());

        when(accountRepository.findById(eq(accountId))).thenReturn(Optional.of(account));

        BalanceResponse balanceResponse = accountService.getBalance(accountId);

        assertNotNull(balanceResponse);
        assertEquals(0, depositAmount.compareTo(balanceResponse.balance()));
        verify(accountRepository, times(1)).findById(eq(accountId));
    }

    @Test
    void getBalanceWhenAccountDoesNotExist() {
        Long nonExistentAccountId = 1L;

        when(accountRepository.findById(eq(nonExistentAccountId))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> accountService.getBalance(nonExistentAccountId));

        verify(accountRepository, times(1)).findById(eq(nonExistentAccountId));
    }
}