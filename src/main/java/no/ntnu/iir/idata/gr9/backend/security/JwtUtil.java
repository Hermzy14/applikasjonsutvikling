package no.ntnu.iir.idata.gr9.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Utility class for JWT (JSON Web Token) operations.
 */
@Component
public class JwtUtil {
  @Value("${jwt_secret_key}")
  private String secretKey;
  private static final String ROLE_KEY = "role";

  /**
   * Generates a JWT token for an authenticated user.
   *
   * @param userDetails the user details of the authenticated user
   * @return the generated JWT token
   */
  public String generateToken(UserDetails userDetails) {
    final long timeNow = System.currentTimeMillis();
    final long millisecondsInHour = 60 * 60 * 1000;
    final long timeAfterOneHour = timeNow + millisecondsInHour;

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .claim(ROLE_KEY, userDetails.getAuthorities())
        .issuedAt(new Date(timeNow))
        .expiration(new Date(timeAfterOneHour))
        .signWith(getSigningKey())
        .compact();
  }

  /**
   * Returns the signing key for JWT.
   *
   * @return the signing key
   */
  private SecretKey getSigningKey() {
    byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
    return new SecretKeySpec(keyBytes, 0, keyBytes.length, "HmacSHA256");
  }

  /**
   * Find the username from the token.
   *
   * @param token the JWT token
   * @return the username extracted from the token
   */
  public String extractUsername(String token) {
    return this.extractClaim(token, Claims::getSubject);
  }

  /**
   * Extracts a claim from the JWT token.
   *
   * @param token          the JWT token
   * @param claimsResolver the function to extract the claim
   * @return the extracted claim
   */
  private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = this.extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * Extracts all claims from the JWT token.
   *
   * @param token the JWT token
   * @return the claims extracted from the token
   */
  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
  }

  /**
   * Check if a token is valid for the given user.
   *
   * @param token       the JWT token
   * @param userDetails the user details of the authenticated user
   * @return @code{true} if the token is valid, @code{false} otherwise
   */
  public boolean validateToken(String token, UserDetails userDetails) throws JwtException {
    final String username = extractUsername(token);
    return userDetails != null
        && username.equals(userDetails.getUsername())
        && !this.isTokenExpired(token);
  }

  /**
   * Checks if the token is expired.
   *
   * @param token the JWT token
   * @return @code{true} if the token is expired, @code{false} otherwise
   */
  private Boolean isTokenExpired(String token) {
    return this.extractExpiration(token).before(new Date());
  }

  /**
   * Extracts the expiration date from the token.
   *
   * @param token the JWT token
   * @return the expiration date
   */
  private Date extractExpiration(String token) {
    return this.extractClaim(token, Claims::getExpiration);
  }
}
