package jk.bsi.tcc.safi.service;

import jk.bsi.tcc.safi.domain.Despesa;
import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.repository.DespesaRepository;
import jk.bsi.tcc.safi.service.dto.DespesaDto;
import jk.bsi.tcc.safi.service.mapper.DespesaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing {@link jk.bsi.tcc.safi.domain.Despesa}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class DespesaService {
  private final ActiveUserService activeUserService;
  private final DespesaRepository despesaRepository;
  private final DespesaMapper despesaMapper;

  /**
   * Save a Despesa.
   *
   * @param despesaDto the entity to save.
   * @return the persisted entity.
   */
  public DespesaDto save(DespesaDto despesaDto) {
    log.debug("({}) Request to save Receita: {}", activeUserService.getEmail(), despesaDto);
    final Usuario usuario = activeUserService.getUsuario();
    Despesa despesa = despesaMapper.toEntity(despesaDto);
    despesa.setUsuario(usuario);
    despesa = despesaRepository.save(despesa);
    return despesaMapper.toDto(despesa);
  }

  /**
   * TODO: Write Documentation
   */
  public Page<DespesaDto> findAll(Pageable pageable) {
    String email = activeUserService.getEmail();
    return despesaRepository.findByUsuarioEmail(email, pageable).map(despesaMapper::toDto);
  }
}
