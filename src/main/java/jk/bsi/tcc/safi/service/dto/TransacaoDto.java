package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * A DTO for the {@link jk.bsi.tcc.safi.domain.Transacao} entity.
 */
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class TransacaoDto implements Serializable {
  private Long id;

  private Date data;

  private Long tipo;

  private Double valor;

  @JsonIgnore
  private ExtratoDetalhadoDto extratoDetalhado;
}
