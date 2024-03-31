package org.mav.banking.controller;

import lombok.RequiredArgsConstructor;
import org.mav.banking.dto.AccountCreateRequest;
import org.mav.banking.dto.AccountResponse;
import org.mav.banking.dto.BalanceResponse;
import org.mav.banking.dto.DepositRequest;
import org.mav.banking.service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountCreateRequest request) {
        AccountResponse accountResponse = accountService.createAccount(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountResponse);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long accountId, @RequestBody DepositRequest depositRequest) {
        BigDecimal amount = depositRequest.getAmount();
        AccountResponse accountResponse = accountService.deposit(accountId, amount);
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable Long accountId) {
        BalanceResponse balanceResponse = accountService.getBalance(accountId);
        return ResponseEntity.ok(balanceResponse);
    }
}
