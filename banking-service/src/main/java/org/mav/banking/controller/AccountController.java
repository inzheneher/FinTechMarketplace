package org.mav.banking.controller;

import org.mav.banking.dto.AccountCreateRequest;
import org.mav.banking.dto.AccountResponse;
import org.mav.banking.dto.BalanceResponse;
import org.mav.banking.dto.DepositRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody AccountCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new AccountResponse());
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String accountId) {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<BalanceResponse> deposit(@PathVariable String accountId, @RequestBody DepositRequest request) {
        return ResponseEntity.ok(new BalanceResponse());
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable String accountId) {
        return ResponseEntity.ok(new BalanceResponse());
    }
}
