package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

/**
 * Transacao entity.
 */
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "transacao")
public class Transacao implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "data", nullable = false)
  private Date data;

  @Column(name = "tipo", nullable = true)
  private Long tipo;

  @Column(name = "valor", nullable = false)
  private Double valor;

  @ManyToOne
  @JoinColumn(name = "extrato_id")
  private ExtratoDetalhado extratoDetalhado;
}

