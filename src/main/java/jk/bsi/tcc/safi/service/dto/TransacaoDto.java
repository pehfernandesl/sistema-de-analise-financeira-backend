package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

/**
 * A DTO for the {@link jk.bsi.tcc.safi.domain.Transacao} entity.
 */
@Data
public class TransacaoDto implements Serializable {
  private Long id;

  private Date data;

  private Long tipo;

  private Double valor;

  private ExtratoDetalhadoDto extratoDetalhado;
}
