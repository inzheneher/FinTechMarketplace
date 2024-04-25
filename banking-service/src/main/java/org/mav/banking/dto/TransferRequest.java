package org.mav.banking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TransferRequest {
    private Long fromAccountId;
    private Long toAccountId;
    private BigDecimal amount;
}
