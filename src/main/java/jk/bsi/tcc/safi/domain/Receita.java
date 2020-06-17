package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Receita entity.
 */
@Data
@Entity
@Table(name = "receita")
public class Receita implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @NotBlank
  @Column(name = "descricao", nullable = false, length = 180)
  private String descricao;

  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 10, fraction = 2)
  @Column(name = "valor", nullable = false)
  private BigDecimal valor;

  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;
}
