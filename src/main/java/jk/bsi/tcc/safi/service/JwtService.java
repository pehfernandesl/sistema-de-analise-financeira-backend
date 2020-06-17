package jk.bsi.tcc.safi.service;

import static java.util.Date.from;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing user's jwts.
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class JwtService implements TokenService {
  private static final Integer JWT_INDEX_AFTER_SPLIT = 1;
  private static final String JWT_RESPONSE_HEADER = "Authorization";
  private static final String JWT_RESPONSE_HEADER_VALUE_PREFIX = "Bearer ";

  private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

  //  @Value("${jwt.secret:safi.default}")
  private final String secret = "safi.default";

  //  @Value("${jwt.expiration.in.seconds:3600}")
  private final Integer expiration = 3600;

  @Override
  public String extractSubject(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public <T> T extractClaim(String token,
                            Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public LocalDate extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration).toInstant().atZone(ZoneId.systemDefault())
      .toLocalDate();
  }

  @Override
  public Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
  }

  @Override
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).isBefore(LocalDate.now());
  }

  @Override
  public String generateToken(String email) {
    HashMap<String, Object> claims = new HashMap<>();
    return createToken(claims, email);
  }

  @Override
  public String createToken(Map<String, Object> claims, String subject) {
    return Jwts.builder().signWith(secretKey).setClaims(claims).setSubject(subject)
      .setIssuedAt(from(Instant.now())).setExpiration(from(Instant.now().plusSeconds(expiration)))
      .compact();
  }

  @Override
  public Boolean validateToken(String token,
                               UserDetails userDetails) {
    final String email = extractSubject(token);
    return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  @Override
  public String generateTokenWithPrefixedValue(String email) {
    return getAuthorizationHeaderValuePrefix() + generateToken(email);
  }

  @Override
  public String getAuthorizationHeader() {
    return JWT_RESPONSE_HEADER;
  }

  @Override
  public String getAuthorizationHeaderValuePrefix() {
    return JWT_RESPONSE_HEADER_VALUE_PREFIX;
  }

  @Override
  public Boolean isHeaderValid(String authorizationHeader) {
    return (Objects.nonNull(
      authorizationHeader) && authorizationHeader.startsWith(getAuthorizationHeaderValuePrefix()));
  }

  @Override
  public Integer getTokenIndex() {
    return JWT_INDEX_AFTER_SPLIT;
  }
}
