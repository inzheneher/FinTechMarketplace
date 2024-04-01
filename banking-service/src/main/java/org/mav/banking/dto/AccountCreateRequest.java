package org.mav.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateRequest {
    private String customerName;
    private String email;
    private String ssn;
}
