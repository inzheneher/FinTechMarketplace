package org.mav.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mav.banking.entity.TransactionStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Long transactionId;
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
    private TransactionStatus status;
    private String message;
}
