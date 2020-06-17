package jk.bsi.tcc.safi.domain;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * InformacaoBancaria entity.
 */
@Data
@Entity
@Table(name = "informacao_bancaria")
public class InformacaoBancaria {

  @EmbeddedId
  private InformacaoBancariaId id;

  @Column(name = "arquivo", nullable = false)
  private byte[] arquivoExtrato;

  @Column(name = "data_inclusao")
  @CreationTimestamp
  private LocalDate dataInclusao;

  /**
   * TODO: Implement -> ExtratoDetalhado
   */
//  @OneToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "extrato_detalhado_id", referencedColumnName = "id")
//  private ExtratoDetalhado extratoDetalhado;

  @ManyToOne
  @JoinColumn(name = "usuario_id", nullable = false, insertable = false, updatable = false)
  private Usuario usuario;
}
