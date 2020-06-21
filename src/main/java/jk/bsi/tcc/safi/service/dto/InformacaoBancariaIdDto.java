package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * A DTO for the {@link jk.bsi.tcc.safi.domain.InformacaoBancariaId} entity.
 */
@Data
public class InformacaoBancariaIdDto implements Serializable {
  private Date mesAno;

  private Long tpBanco;

  private Long usuarioId;
}
