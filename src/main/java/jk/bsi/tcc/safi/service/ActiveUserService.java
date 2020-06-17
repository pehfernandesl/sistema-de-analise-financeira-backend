package jk.bsi.tcc.safi.service;

import javax.persistence.EntityNotFoundException;
import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class ActiveUserService {
  private final UsuarioRepository usuarioRepository;

  public String getEmail() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  public Usuario getUsuario() {
    return usuarioRepository.findOneByEmail(getEmail()).orElseThrow(EntityNotFoundException::new);
  }
}
