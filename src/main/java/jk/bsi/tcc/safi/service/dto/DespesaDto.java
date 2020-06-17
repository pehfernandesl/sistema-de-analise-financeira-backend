package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * A DTO for the {@link jk.bsi.tcc.safi.domain.Despesa} entity.
 */
@Data
public class DespesaDto implements Serializable {
  private Long id;

  @NotBlank
  private String descricao;

  @NotNull
  @DecimalMax(value = "0.0", inclusive = false)
  @Digits(integer = 10, fraction = 2)
  private BigDecimal valor;
}
