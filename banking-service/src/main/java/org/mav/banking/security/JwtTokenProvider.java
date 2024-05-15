package org.mav.banking.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private SecretKey key;

    @Value("${jwt.secret}")
    public void setKey(String base64key) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64key));
    }

    public String generateToken(Long accountId, String email) {
        Claims claims = Jwts.claims().subject(email).add("accountId", accountId).build();

        long now = System.currentTimeMillis();
        long exp = now + 3600000;

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(exp))
                .signWith(key)
                .compact();
    }
}
