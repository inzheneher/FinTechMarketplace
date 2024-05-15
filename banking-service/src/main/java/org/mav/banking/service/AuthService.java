package org.mav.banking.service;

import lombok.RequiredArgsConstructor;
import org.mav.banking.entity.Account;
import org.mav.banking.repository.AccountRepository;
import org.mav.banking.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String authenticateUser(String email, String password) {
        Account account = accountRepository.findByEmail(email);
        if (account != null && passwordEncoder.matches(password, account.getPassword())) {
            return jwtTokenProvider.generateToken(account.getId(), email);
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }

}
