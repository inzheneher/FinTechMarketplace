package org.mav.banking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountResponse {
    private String accountId;
    private String customerName;
    private String email;
    private Double balance;
}
