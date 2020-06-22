package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * ExtratoDetalhado entity.
 */
@Data
@Entity
@Table(name = "extrato_detalhado")
public class ExtratoDetalhado implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @Column(name = "data_inicial", nullable = false)
  private LocalDate dataInicial;

  @Column(name = "data_final", nullable = false)
  private LocalDate dataFinal;

  @Column(name = "saldo", nullable = false)
  private Double saldo;

  @OneToMany(mappedBy = "extratoDetalhado", fetch = FetchType.EAGER)
  private Set<Transacao> transacoes;

  @OneToOne(mappedBy = "extratoDetalhado")
  private InformacaoBancaria info;

  //sada
}
