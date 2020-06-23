package jk.bsi.tcc.safi.service;

import jk.bsi.tcc.safi.domain.Receita;
import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.repository.ReceitaRepository;
import jk.bsi.tcc.safi.service.dto.ReceitaDto;
import jk.bsi.tcc.safi.service.mapper.ReceitaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing {@link jk.bsi.tcc.safi.domain.Receita}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReceitaService {
  private final ActiveUserService activeUserService;
  private final ReceitaRepository receitaRepository;
  private final ReceitaMapper receitaMapper;

  /**
   * Save a Receita.
   *
   * @param receitaDto the entity to save.
   * @return the persisted entity.
   */
  public ReceitaDto save(ReceitaDto receitaDto) {
    log.debug("({}) Request to save Receita: {}", activeUserService.getEmail(), receitaDto);
    final Usuario usuario = activeUserService.getUsuario();
    Receita receita = receitaMapper.toEntity(receitaDto);
    receita.setUsuario(usuario);
    receita = receitaRepository.save(receita);
    return receitaMapper.toDto(receita);
  }

  /**
   * TODO: Write Documentation
   */
  public Page<ReceitaDto> findAll(Pageable pageable) {
    String email = activeUserService.getEmail();
    return receitaRepository.findByUsuarioEmail(email, pageable).map(receitaMapper::toDto);
  }

  /**
   * TODO: Write Documentation
   */
  public void delete(Long id) {
    receitaRepository.deleteById(id);
  }
}
