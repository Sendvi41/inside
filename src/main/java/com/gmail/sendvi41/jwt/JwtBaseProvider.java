package com.gmail.sendvi41.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;

@Getter
@Slf4j
public class JwtBaseProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public boolean validateToken(String token) {
        return parseToken(token).isPresent();
    }

    public Optional<Jws<Claims>> parseToken(String token) {
        try {
            Jws<Claims> jwt = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return Optional.of(jwt);
        } catch (JwtException | IllegalArgumentException ex) {
            log.warn("Invalid token [error={}]", ex.getMessage());
        }
        return Optional.empty();
    }
}
