package org.mav.banking.controller;

import lombok.RequiredArgsConstructor;
import org.mav.banking.dto.TransactionResponse;
import org.mav.banking.dto.TransferRequest;
import org.mav.banking.entity.TransactionStatus;
import org.mav.banking.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@RequestBody TransferRequest request) {

        TransactionResponse transactionResponse = transactionService.transferFunds(request.fromAccountId(), request.toAccountId(), request.amount());

        if (TransactionStatus.SUCCESS.equals(transactionResponse.status()))
            return ResponseEntity.ok(transactionResponse);
        else return ResponseEntity.badRequest().body(transactionResponse);
    }
}
