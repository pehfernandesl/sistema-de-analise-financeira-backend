package jk.bsi.tcc.safi.service;

import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.repository.UsuarioRepository;
import jk.bsi.tcc.safi.service.dto.FormularioCadastroUsuarioDto;
import jk.bsi.tcc.safi.service.dto.UsuarioDto;
import jk.bsi.tcc.safi.service.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing {@link jk.bsi.tcc.safi.domain.Usuario}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper;
  private final PasswordEncoder passwordEncoder;

  /**
   * Save a Usuario.
   *
   * @param formularioCadastroUsuarioDto with data about the entity to save.
   * @return the persisted entity.
   */
  public UsuarioDto save(final FormularioCadastroUsuarioDto formularioCadastroUsuarioDto) {
    log.debug("Request to save Usuario with data: {}", formularioCadastroUsuarioDto);

    final Usuario usuario = new Usuario();

    usuario.setNome(formularioCadastroUsuarioDto.getNome());
    usuario.setDataNascimento(formularioCadastroUsuarioDto.getDataNascimento());
    usuario.setEmail(formularioCadastroUsuarioDto.getEmail());
    usuario.setSenha(passwordEncoder.encode(formularioCadastroUsuarioDto.getSenha()));

    Usuario usuarioSalvo = usuarioRepository.save(usuario);

    return usuarioMapper.toDto(usuarioSalvo);
  }
}
