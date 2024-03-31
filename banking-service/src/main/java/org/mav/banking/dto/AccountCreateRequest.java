package org.mav.banking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountCreateRequest {
    private String customerName;
    private String email;
    private String ssn;
}
