package jk.bsi.tcc.safi.web.rest;

import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import jk.bsi.tcc.safi.domain.Credencial;
import jk.bsi.tcc.safi.service.SafiUserDetailsService;
import jk.bsi.tcc.safi.service.TokenService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing {@link jk.bsi.tcc.safi.domain.Usuario}.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/authenticate")
public class AuthenticationResource {
  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;
  private final SafiUserDetailsService userDetailsService;

  @GetMapping
  public ResponseEntity<TokenValidationResponse> tokenIsValid(HttpServletRequest request) {
    final String authorizationHeader = request.getHeader(tokenService.getAuthorizationHeader());
    final String token = authorizationHeader.split(" ")[tokenService.getTokenIndex()];
    final String email = tokenService.extractSubject(token);
    final Boolean tokenIsValid =
      tokenService.validateToken(token, userDetailsService.loadUserByUsername(email));
    return ResponseEntity.ok(new TokenValidationResponse(tokenIsValid));
  }

  @PostMapping
  public ResponseEntity<TokenResponse> generateToken(@Valid @RequestBody Credencial credencial,
                                                     final HttpServletResponse response) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(credencial.getEmail(),
        credencial.getSenha())
    );

    final String token = tokenService.generateTokenWithPrefixedValue(credencial.getEmail());
    return ResponseEntity.ok(new TokenResponse(credencial.getEmail(), token));
  }

  @Data
  @RequiredArgsConstructor
  public static class TokenResponse implements Serializable {
    private final String email;
    private final String token;
  }

  @Data
  @RequiredArgsConstructor
  public static class TokenValidationResponse implements Serializable {
    private final Boolean isValid;
  }
}
