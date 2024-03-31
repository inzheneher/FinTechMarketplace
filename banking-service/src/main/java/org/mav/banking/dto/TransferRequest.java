package org.mav.banking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferRequest {
    private String fromAccountId;
    private String toAccountId;
    private Double amount;
}
