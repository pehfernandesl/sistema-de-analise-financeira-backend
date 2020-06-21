package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Despesa entity.
 */
@Data
@Entity
@Table(name = "despesa")
public class Despesa implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @NotBlank
  @Column(name = "descricao", nullable = false, length = 180)
  private String descricao;

  @NotNull
  @DecimalMax(value = "0.0", inclusive = false)
  @Digits(integer = 10, fraction = 2)
  @Column(name = "valor", nullable = false)
  private BigDecimal valor;

  private LocalDate dataLancamento;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;
}
