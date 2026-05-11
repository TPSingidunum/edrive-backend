package rs.ac.singidunum.edrivebackend.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtProperties jwtProperties;
    private SecretKey accessSecretKey;
    private SecretKey refreshSecretKey;

    @PostConstruct
    public void init() {
        accessSecretKey = Keys.hmacShaKeyFor(jwtProperties.getAccessTokenSecret().getBytes(StandardCharsets.UTF_8));
        refreshSecretKey = Keys.hmacShaKeyFor(jwtProperties.getRefreshTokenSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject, Map<String, Object> claims) {
       return Jwts.builder()
               .subject(subject)
               .claims(claims)
               .issuedAt(Date.from(Instant.now()))
               .expiration(generateExpirationDate(claims.get("type").toString()))
               .signWith(claims.get("type").toString().equals("access") ? accessSecretKey : refreshSecretKey)
               .compact();
    }

    public Date generateExpirationDate(String tokenType) {
        return switch (tokenType) {
            case "access" -> Date.from(Instant.now().plus(jwtProperties.getAccessTokenDuration(), ChronoUnit.MINUTES));
            case "refresh" -> Date.from(Instant.now().plus(jwtProperties.getRefreshTokenDuration(), ChronoUnit.MINUTES));
            default -> null;
        };
    }

    public Claims extractClaimsFromToken(String token) {
        return Jwts.parser().verifyWith(accessSecretKey).build().parseSignedClaims(token).getPayload();
    }

}
