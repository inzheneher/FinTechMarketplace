package org.mav.banking.dto;

import org.mav.banking.entity.TransactionStatus;

import java.math.BigDecimal;

public record TransactionResponse(Long transactionId,
                                  Long fromAccountId,
                                  Long toAccountId,
                                  BigDecimal amount,
                                  TransactionStatus status,
                                  String message) {
}
