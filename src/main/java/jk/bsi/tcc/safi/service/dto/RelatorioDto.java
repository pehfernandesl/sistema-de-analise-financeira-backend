package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * A DTO for the Relatorio.
 */
@Data
public class RelatorioDto implements Serializable {
  private Double total;
  private Double totalEntrada;
  private Double totalSaida;
  private List<RelatorioOperacoesDto> operacoes;
  private Date dataMaisMovimentoPositivo;
  private Date dataMaisMovimentoNegativo;
  private Date dataMaisMovimento;
}
