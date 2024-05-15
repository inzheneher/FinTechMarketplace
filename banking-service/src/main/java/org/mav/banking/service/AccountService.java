package org.mav.banking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mav.banking.dto.AccountCreateRequest;
import org.mav.banking.dto.AccountResponse;
import org.mav.banking.dto.BalanceResponse;
import org.mav.banking.entity.Account;
import org.mav.banking.repository.AccountRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountResponse createAccount(AccountCreateRequest request) {
        if (request.email() == null || request.username() == null || request.ssn() == null) {
            log.error("Email, Customer Name, and SSN must be provided");
            throw new IllegalArgumentException("Email, Customer Name, and SSN must be provided");
        }

        boolean existByEmail = accountRepository.existsByEmail(request.email());
        boolean existBySsn = accountRepository.existsBySsn(request.ssn());
        if (existByEmail || existBySsn) {
            throw new IllegalArgumentException("Account with given email or SSN already exists");
        }

        Account account = new Account();
        account.setUsername(request.username());
        account.setEmail(request.email());
        account.setPassword(passwordEncoder.encode(request.password()));
        account.setSsn(request.ssn());
        account.setBalance(BigDecimal.ZERO);

        account = accountRepository.save(account);

        return new AccountResponse(account.getId(), account.getUsername(), account.getEmail(), account.getBalance());
    }

    public void deleteAccount(Long accountId) {
        if (!accountRepository.existsById(accountId)) {
            throw new IllegalArgumentException("Account with given ID does not exist");
        }
        accountRepository.deleteById(accountId);
    }

    public AccountResponse deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with given ID does not exist"));
        account.setBalance(account.getBalance().add(amount));
        account = accountRepository.save(account);
        return new AccountResponse(account.getId(), account.getUsername(), account.getEmail(), account.getBalance());
    }

    @Transactional(readOnly = true)
    public BalanceResponse getBalance(Long accountId) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with given ID does not exist"));
        return new BalanceResponse(account.getBalance());
    }
}
