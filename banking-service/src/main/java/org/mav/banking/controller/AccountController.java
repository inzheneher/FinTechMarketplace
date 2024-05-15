package org.mav.banking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mav.banking.dto.AccountCreateRequest;
import org.mav.banking.dto.AccountResponse;
import org.mav.banking.dto.BalanceResponse;
import org.mav.banking.dto.DepositRequest;
import org.mav.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@Slf4j
@EnableMethodSecurity
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountCreateRequest request) {
        log.info("Creating account {}", request);
        AccountResponse accountResponse = accountService.createAccount(request);
        log.info("Created account {}", accountResponse);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountResponse);
    }

    @DeleteMapping("/delete/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit/{accountId}")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long accountId, @RequestBody DepositRequest depositRequest) {
        BigDecimal amount = depositRequest.amount();
        AccountResponse accountResponse = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/balance/{accountId}")
    @PreAuthorize("#accountId == authentication.principal")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long accountId) {
        BalanceResponse balanceResponse = accountService.getBalance(accountId);
        return ResponseEntity.ok(balanceResponse);
    }
}
