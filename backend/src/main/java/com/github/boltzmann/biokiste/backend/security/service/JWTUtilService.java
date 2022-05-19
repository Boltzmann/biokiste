package com.github.boltzmann.biokiste.backend.security.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

@Service
public class JWTUtilService {
    @Value("${piphi.biokisteapp.jwt.secret}")
    private String JWTSECRET;

    public String createToken(String username){
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(Duration.ofHours(6))))
                .signWith(SignatureAlgorithm.HS256, JWTSECRET)
                .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser()
                .setSigningKey(JWTSECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
