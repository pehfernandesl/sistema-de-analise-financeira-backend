package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

/**
 * InformacaoBancariaId -> InformacaoBancaria's PK.
 */
@Data
@Embeddable
public class InformacaoBancariaId implements Serializable {
  @Column(name = "mes_ano", nullable = false)
  @Temporal(TemporalType.DATE)
  private Date mesAno;

  @Column(name = "tp_banco", nullable = false)
  private Long tpBanco;

  @Column(name = "usuario_id")
  private Long usuarioId;
}
