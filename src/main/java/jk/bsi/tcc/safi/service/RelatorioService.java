package jk.bsi.tcc.safi.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import jk.bsi.tcc.safi.domain.InformacaoBancaria;
import jk.bsi.tcc.safi.repository.InformacaoBancariaRepository;
import jk.bsi.tcc.safi.service.dto.RelatorioDto;
import jk.bsi.tcc.safi.service.dto.RelatorioOperacoesDto;
import jk.bsi.tcc.safi.service.util.CoreUtil;
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
  private final InformacaoBancariaRepository informacaoBancariaRepository;
  private final ActiveUserService activeUserService;

  public RelatorioDto preencherRelatorio(LocalDate mes) throws RuntimeException {
    List<InformacaoBancaria> listaInfo = informacaoBancariaRepository
      .findById_MesAnoAndUsuarioEmail(
        Date.from(mes.atStartOfDay(ZoneId.systemDefault()).toInstant()),
        activeUserService.getEmail());

    final List<Long> ids = new ArrayList<>();
    listaInfo.forEach((info) -> {
      ids.add(info.getExtratoDetalhado().getId());
    });


    final RelatorioDto relatorioDto =
      this.relatorioRepository.buscarDadosGerais(ids);
    relatorioDto.setOperacoes(relatorioRepository.buscarDadosOperacoes(ids));

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

  private static final String QUERY_DADOS_OPERACOES =
    "select tipo, cast(sum(valor)as DECIMAL(7,2)),cast(sum(valor) * 100 /\n" +
      "(select sum(valor)from transacao where extrato_id in ({}) and cast(valor as text)  like '%-%')\n" +
      "as DECIMAL(7,2))\n" +
      "from transacao where extrato_id in ({}) and cast(valor as text)  like '%-%' group by tipo order by tipo";

  private final EntityManager entityManager;

  public RelatorioDto buscarDadosGerais(List<Long> ids) {
    RelatorioDto relatorio = new RelatorioDto();
    final Query query = retornaQueryDadosGerais(ids);
    Object[] obj = (Object[]) query.getSingleResult();

    if (obj[0] != null) {
      relatorio.setTotal(new Double(obj[0].toString()));
    }
    if (obj[1] != null) {
      relatorio.setTotalEntrada(new Double(obj[1].toString()));
    }
    if (obj[2] != null) {
      relatorio.setTotalSaida(new Double(obj[2].toString()));
    }
    if (obj[3] != null) {
      relatorio.setDataMaisMovimento(CoreUtil.retornarDataFormatada(obj[3].toString()));
    }
    /**
     * Checkpoint: Original (Com erro) -> 4 , 5 | Novo (sem erro?) -> 5, 4
     */
    if (obj[5] != null) {
      relatorio.setDataMaisMovimentoPositivo(CoreUtil.retornarDataFormatada(obj[5].toString()));
    }
    if (obj[4] != null) {
      relatorio.setDataMaisMovimentoNegativo(CoreUtil.retornarDataFormatada(obj[4].toString()));
    }

    return relatorio;
  }

  public List<RelatorioOperacoesDto> buscarDadosOperacoes(List<Long> ids) {
    final List<RelatorioOperacoesDto> lista =
      new ArrayList<RelatorioOperacoesDto>();
    final Query query = retornaQueryDadosOperacoes(ids);
    final List<Object[]> result = query.getResultList();

    for (Object[] obj : result) {
      RelatorioOperacoesDto op = new RelatorioOperacoesDto();
      if (obj[0] != null) {
        op.setTpOperacao(Long.valueOf(obj[0].toString()));
      }
      if (obj[1] != null) {
        op.setValor(Double.valueOf(obj[1].toString()));
      }
      if (obj[2] != null) {
        op.setPercent(Double.valueOf(obj[2].toString()));
      }
      lista.add(op);
    }

    return lista;
  }

  private Query retornaQueryDadosGerais(List<Long> ids) {
    final String listaIds =
      StringUtils.join(ids.stream().map(id -> id.toString()).collect(Collectors.toList()), ',');
    return entityManager
      .createNativeQuery(QUERY_RELATORIO_GERAL.replace("{}", listaIds));
  }

  private Query retornaQueryDadosOperacoes(List<Long> ids) {
    final String listaIds =
      StringUtils.join(ids.stream().map(id -> id.toString()).collect(Collectors.toList()), ',');
    return entityManager
      .createNativeQuery(QUERY_DADOS_OPERACOES.replace("{}", listaIds));
  }
}
