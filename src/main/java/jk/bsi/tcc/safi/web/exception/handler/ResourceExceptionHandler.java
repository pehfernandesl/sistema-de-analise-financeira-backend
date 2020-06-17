package jk.bsi.tcc.safi.web.exception.handler;

import javax.servlet.http.HttpServletRequest;
import jk.bsi.tcc.safi.web.error.StandardError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Handler for managing exceptions for REST controllers.
 */
@ControllerAdvice
public class ResourceExceptionHandler {
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<StandardError> authenticationException(AuthenticationException e,
                                                               HttpServletRequest request) {
    final StandardError err = StandardError
      .builder()
      .message(e.getMessage())
      .status(HttpStatus.FORBIDDEN.value())
      .timestamp(System.currentTimeMillis())
      .build();

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(err);
  }

  /*
   * TODO: URISyntaxException Handler
   */
}
