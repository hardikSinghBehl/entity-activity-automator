package com.behl.freezo.security.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.behl.freezo.entity.Doctor;
import com.behl.freezo.security.configuration.properties.JwtConfigurationProperties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;

@Component
@EnableConfigurationProperties(JwtConfigurationProperties.class)
@AllArgsConstructor
public class JwtUtils {

    private final JwtConfigurationProperties jwtConfigurationProperties;

    public String extractEmail(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UUID extractDoctorId(final String token) {
        return UUID.fromString((String) extractAllClaims(token).get("user_id"));
    }

    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(final String token) {
        return Jwts.parser().setSigningKey(jwtConfigurationProperties.getJwt().getSecretKey())
                .parseClaimsJws(token.replace("Bearer ", "")).getBody();
    }

    private Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(final Doctor doctor) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("doctor_id", doctor.getId());
        claims.put("email_id", doctor.getEmailId());
        claims.put("email_verified", true);
        claims.put("scope", "doctor");
        return createToken(claims, doctor.getEmailId());
    }

    private String createToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, jwtConfigurationProperties.getJwt().getSecretKey()).compact();
    }

    public Boolean validateToken(final String token, final UserDetails userDetails) {
        final String username = extractEmail(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}