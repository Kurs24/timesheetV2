package com.yasykur.timesheet.config;

import com.yasykur.timesheet.model.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    String secret;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date getTokenExpireTime(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean verifyToken(String token, UserDetails userDetails) {
        boolean isExpired = getTokenExpireTime(token).before(new Date());
        boolean isSameUser = extractUsername(token).equals(userDetails.getUsername());
        return !isExpired && isSameUser;
    }


    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(UserDetails userDetails, Employee employee) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("level", employee.getRole().getLevel());
        claims.put("role", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
        claims.put("name", employee.getFirstName() + " " + employee.getLastName());

        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .subject(username)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 5 * 60 * 60))
                .signWith(generateKey())
                .compact();
    }

    private SecretKey generateKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(decodedKey);
    }
}
