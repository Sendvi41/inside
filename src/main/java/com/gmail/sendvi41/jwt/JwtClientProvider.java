package com.gmail.sendvi41.jwt;

import com.gmail.sendvi41.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JwtClientProvider extends JwtBaseProvider {

    @Value("${jwt.expirationInSec:300}")
    private Integer expirationInSec;

    public String generateTokenForUser(UserEntity userEntity) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusSeconds(expirationInSec).toInstant());
        return Jwts.builder()
                .setHeaderParam("typ", Header.JWT_TYPE)
                .setHeaderParam("alg", "HS512")
                .setSubject(userEntity.getName())
                .claim("id", userEntity.getId())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, getJwtSecret())
                .compact();
    }


    public String getLoginFromToken(String token) {
        Optional<Jws<Claims>> optionalJws = parseToken(token);
        return optionalJws.map(jws -> jws.getBody().getSubject()).orElse("");
    }
}
