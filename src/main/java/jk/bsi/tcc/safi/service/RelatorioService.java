package jk.bsi.tcc.safi.service;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class RelatorioService {
  private final RelatorioRepository relatorioRepository;
  private final ActiveUserService activeUserService;

  public Object preencherRelatorio(Long mes) throws RuntimeException {
    return null;
  }

}

@RequiredArgsConstructor
@Component
@Transactional
class RelatorioRepository {
  private final EntityManager entityManager;

  private Object buscarDadosGerais(List<Long> ids) {
    return null;
  }

  private List<Object> buscarDadosOperacoes(List<Long> ids) {
    return null;
  }

  private List<Object> retornaQueryDadosGerais(List<Long> ids) {
    return null;
  }

  private List<Object> retornaQueryDadosOperacoes(List<Long> ids) {
    return null;
  }
}
