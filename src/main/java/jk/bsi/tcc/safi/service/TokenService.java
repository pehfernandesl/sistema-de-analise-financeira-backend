package jk.bsi.tcc.safi.service;

import io.jsonwebtoken.Claims;
import java.time.LocalDate;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Contract for managing user's auth tokens.
 */
public interface TokenService {
  String extractSubject(String token);

  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

  LocalDate extractExpiration(String token);

  Claims extractAllClaims(String token);

  Boolean isTokenExpired(String token);

  String generateToken(String email);

  String generateTokenWithPrefixedValue(String email);

  String createToken(Map<String, Object> claims, String subject);

  Boolean validateToken(String token, UserDetails userDetails);

  String getAuthorizationHeader();

  String getAuthorizationHeaderValuePrefix();

  Boolean isHeaderValid(String authorizationHeader);

  Integer getTokenIndex();
}
