package org.mav.banking.dto;

public record AccountCreateRequest(String username, String email, String password, String ssn) {
}
