package jk.bsi.tcc.safi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jk.bsi.tcc.safi.domain.Transacao;
import jk.bsi.tcc.safi.domain.enumeration.TpTransacao;
import jk.bsi.tcc.safi.repository.TransacaoRepository;
import jk.bsi.tcc.safi.service.dto.TransacaoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing {@link jk.bsi.tcc.safi.domain.Transacao}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class TransacaoService {
  private final TransacaoRepository transacaoRepository;
  private final ActiveUserService activeUserService;

  public List<TransacaoDto> getTransacoesForChart() {
    final List<Transacao> transacoes =
      transacaoRepository.findByExtratoDetalhado_Info_UsuarioEmail(activeUserService.getEmail());

    List<TransacaoDto> lista = new ArrayList<>();

    for (TpTransacao value : TpTransacao.values()) {
      final TransacaoDto transacaoComValorTotal = getTransacaoComValorTotal(transacoes, value);

      if (!(transacaoComValorTotal.getValor() == 0.0)) {
        lista.add(transacaoComValorTotal);
      }
    }

    return lista;
  }

  public TransacaoDto getTransacaoComValorTotal(List<Transacao> transacoes,
                                                TpTransacao tpTransacao) {
    final List<Transacao> lista = transacoes.stream().filter(transacao -> {
      return transacao.getTipo().equals(tpTransacao.getId());
    }).collect(Collectors.toList());

    final double somaTotal = lista.stream().mapToDouble(Transacao::getValor).sum();
    final TransacaoDto transacaoDto = new TransacaoDto();
    transacaoDto.setTipo(tpTransacao.getId());
    transacaoDto.setValor(somaTotal);
    return transacaoDto;
  }
}

