package com.example.Task.jwt;

import com.example.Task.exception.UnauthorizedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

import static com.example.Task.util.ConstantsHolder.SECRET;

@Component
public class JwtUtils {

    public static final String BEARER_PREFIX = "Bearer ";

    public static String generateToken(String username, String secret, int expirationTime) {
        return Jwts.builder()
            .setSubject(username)
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + expirationTime))
            .signWith(SignatureAlgorithm.HS512, secret)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody().getSubject();
    }

    public void validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Authentication token is expired.");
        }
    }

    public String parseJwt(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        } else {
            throw new UnauthorizedException("Invalid token.");
        }
    }
}
