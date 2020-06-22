package jk.bsi.tcc.safi.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import jk.bsi.tcc.safi.service.dto.RelatorioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.StringUtils;
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

  public RelatorioDto preencherRelatorio(Long mes) throws RuntimeException {
    final RelatorioDto relatorioDto =
      this.relatorioRepository.buscarDadosGerais(Arrays.asList(1L, 213L));
    return relatorioDto;
  }

}

@RequiredArgsConstructor
@Component
@Transactional
class RelatorioRepository {
  private static final String QUERY_RELATORIO_GERAL =
    "select (select cast(sum(valor)as decimal (7,2)) as total from transacao where extrato_id in ({})),\n" +
      "(select cast(sum(valor)as decimal (7,2)) as total_pos from transacao where\textrato_id in ({}) and cast(valor as text) not like '%-%'),\n" +
      "(select cast(sum(valor)as decimal (7,2)) as total_neg from transacao where\textrato_id in ({}) and cast(valor as text) like '%-%'),\n" +
      "(select res.data as data_maior_mov from (select distinct data,count(*) from transacao where extrato_id in ({}) group by data order by count(*) desc limit 1) as res),\n" +
      "(select res.data as data_maior_mov_pos from (select distinct data , count(*) from transacao where extrato_id in ({}) and cast(valor as text) like '%-%' group by data order by count(*) DESC LIMIT 1) as res),\n" +
      "(select res.data as data_maior_mov_neg from (select distinct data , count(*) from transacao where extrato_id in ({}) and cast(valor as text) not like '%-%' group by data order by count(*) DESC LIMIT 1) as res)";

  private final EntityManager entityManager;

  public RelatorioDto buscarDadosGerais(List<Long> ids) {
    RelatorioDto relatorio = new RelatorioDto();
    final Query query = retornaQueryDadosGerais(ids);
    relatorio = (RelatorioDto) query.getSingleResult();

    return relatorio;
  }

  private List<Object> buscarDadosOperacoes(List<Long> ids) {
    return null;
  }

  private Query retornaQueryDadosGerais(List<Long> ids) {
    final String listaIds =
      StringUtils.join(ids.stream().map(id -> id.toString()).collect(Collectors.toList()), ',');
    return entityManager
      .createNativeQuery(QUERY_RELATORIO_GERAL.replace("{}", listaIds), RelatorioDto.class);
  }

  private List<Object> retornaQueryDadosOperacoes(List<Long> ids) {
    return null;
  }
}
