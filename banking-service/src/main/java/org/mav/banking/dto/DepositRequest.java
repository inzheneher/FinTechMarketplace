package org.mav.banking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepositRequest {
    private Double amount;
}
