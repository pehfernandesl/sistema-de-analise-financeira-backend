package jk.bsi.tcc.safi.service;

import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.repository.UsuarioRepository;
import jk.bsi.tcc.safi.service.dto.FormularioCadastroUsuarioDto;
import jk.bsi.tcc.safi.service.dto.UsuarioDto;
import jk.bsi.tcc.safi.service.mapper.UsuarioMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  /**
   * Save a Usuario.
   *
   * @param formularioCadastroUsuarioDto with data about the entity to save.
   * @return the persisted entity.
   */
  public UsuarioDto save(final FormularioCadastroUsuarioDto formularioCadastroUsuarioDto) {
    final Usuario usuario = new Usuario();

    usuario.setNome(formularioCadastroUsuarioDto.getNome());
    usuario.setDataNascimento(formularioCadastroUsuarioDto.getDataNascimento());
    usuario.setEmail(formularioCadastroUsuarioDto.getEmail());
    // TODO: Hash password before saving.
    usuario.setSenha(formularioCadastroUsuarioDto.getSenha());

    Usuario usuarioSalvo = usuarioRepository.save(usuario);

    return usuarioMapper.toDto(usuarioSalvo);
  }
}
