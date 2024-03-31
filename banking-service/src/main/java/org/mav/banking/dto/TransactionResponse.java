package org.mav.banking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private Double amount;
    private String fromAccountId;
    private String toAccountId;
    private String status;
}
