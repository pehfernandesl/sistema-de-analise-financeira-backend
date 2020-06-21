package jk.bsi.tcc.safi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * A DTO for the {@link jk.bsi.tcc.safi.domain.ExtratoDetalhado} entity.
 */
@Data
public class ExtratoDetalhadoDto implements Serializable {
  private Long id;

  private Date dataInicial;

  private Date dataFinal;

  private Double saldo;

  private List<TransacaoDto> transacoes;

  @JsonIgnore
  private InformacaoBancariaDto info;
}
