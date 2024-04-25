package org.mav.banking.service;

import lombok.RequiredArgsConstructor;
import org.mav.banking.dto.AccountCreateRequest;
import org.mav.banking.dto.AccountResponse;
import org.mav.banking.dto.BalanceResponse;
import org.mav.banking.entity.Account;
import org.mav.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountResponse createAccount(AccountCreateRequest request) {
        if (request.getEmail() == null || request.getCustomerName() == null || request.getSsn() == null) {
            throw new IllegalArgumentException("Email, Customer Name, and SSN must be provided");
        }

        boolean existByEmail = accountRepository.existsByEmail(request.getEmail());
        boolean existBySsn = accountRepository.existsBySsn(request.getSsn());
        if (existByEmail || existBySsn) {
            throw new IllegalArgumentException("Account with given email or SSN already exists");
        }

        Account account = new Account();
        account.setCustomerName(request.getCustomerName());
        account.setEmail(request.getEmail());
        account.setSsn(request.getSsn());
        account.setBalance(BigDecimal.ZERO);

        account = accountRepository.save(account);

        return new AccountResponse(account.getId(), account.getCustomerName(), account.getEmail(), account.getBalance());
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
        return new AccountResponse(account.getId(), account.getCustomerName(), account.getEmail(), account.getBalance());
    }

    @Transactional(readOnly = true)
    public BalanceResponse getBalance(Long accountId) {
        Account account = accountRepository
                .findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account with given ID does not exist"));
        return new BalanceResponse(account.getBalance());
    }
}
