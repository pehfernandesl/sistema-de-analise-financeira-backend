package jk.bsi.tcc.safi.web.rest;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import jk.bsi.tcc.safi.domain.Credencial;
import jk.bsi.tcc.safi.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

  @PostMapping
  public ResponseEntity<Void> generateToken(@Valid @RequestBody Credencial credencial,
                                            final HttpServletResponse response) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(credencial.getEmail(),
        credencial.getSenha())
    );
    response.addHeader(tokenService.getAuthorizationHeader(),
      tokenService.generateTokenWithPrefixedValue(credencial.getEmail()));
    return ResponseEntity.noContent().build();
  }
}
