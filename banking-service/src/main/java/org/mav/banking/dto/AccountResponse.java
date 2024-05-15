package org.mav.banking.dto;

import java.math.BigDecimal;

public record AccountResponse(Long accountId, String username, String email, BigDecimal balance) {
}
