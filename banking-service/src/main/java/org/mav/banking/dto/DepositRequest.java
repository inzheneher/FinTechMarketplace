package org.mav.banking.dto;

import java.math.BigDecimal;

public record DepositRequest(BigDecimal amount) {
}
