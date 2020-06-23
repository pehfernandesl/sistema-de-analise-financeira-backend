package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import lombok.Data;

/**
 * A DTO for the RelatorioOperacoes.
 */
@Data
public class RelatorioOperacoesDto implements Serializable {
  private Long tpOperacao;
  private Double valor;
  private Double percent;
}
