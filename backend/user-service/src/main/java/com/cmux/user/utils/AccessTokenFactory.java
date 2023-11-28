package com.cmux.user.utils;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.ClaimsBuilder;
import io.jsonwebtoken.Jwts;
import com.cmux.user.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Value;
import java.util.Date;

@Component
public class AccessTokenFactory extends TokenFactory {


    public AccessTokenFactory(@Value("${JWT_SECRET_KEY}") String jwtSecret, @Value("${JWT_EXPIRATION_MS}") int jwtExpirationMs) {
        super(jwtSecret, jwtExpirationMs);
    }

    @Override
    public String createToken(CustomUserDetails userDetails) {
        ClaimsBuilder claims = Jwts.claims();
        claims.add("username", userDetails.getUsername());
        claims.add("userId", userDetails.getUserId());
        return Jwts.builder().claims(claims.build())
                // .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(getExpirationDate())
                .signWith(secretKey)
                .compact();
    }
}
