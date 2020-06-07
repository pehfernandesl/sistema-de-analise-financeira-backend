package jk.bsi.tcc.safi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import jk.bsi.tcc.safi.service.UsuarioService;
import jk.bsi.tcc.safi.service.dto.FormularioCadastroUsuarioDto;
import jk.bsi.tcc.safi.service.dto.UsuarioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api")
public class UsuarioResource {
  private final UsuarioService usuarioService;

  /**
   * {@code POST /usuarios} : Create a new Usuario.
   *
   * @param formularioCadastroUsuarioDto with data about usuario entity to create.
   * @return the {@link ResponseEntity}
   * with status {@code 201 (Created)} and with body the new usuarioDto.
   * @throws URISyntaxException if the location URI syntax is incorrect.
   */
  @PostMapping("/usuarios")
  public ResponseEntity<UsuarioDto> createUsuario(
    @Valid @RequestBody FormularioCadastroUsuarioDto formularioCadastroUsuarioDto
  ) throws URISyntaxException {
    log.debug("REST request to create Usuario with data: {}", formularioCadastroUsuarioDto);
    UsuarioDto usuarioDto = usuarioService.save(formularioCadastroUsuarioDto);
    return ResponseEntity
      .created(new URI("/api/usuarios/" + usuarioDto.getId()))
      .body(usuarioDto);
  }
}
