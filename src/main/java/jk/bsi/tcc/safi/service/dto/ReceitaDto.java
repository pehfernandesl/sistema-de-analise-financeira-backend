package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * A DTO for the {@link jk.bsi.tcc.safi.domain.Receita} entity.
 */
@Data
public class ReceitaDto implements Serializable {
  private Long id;

  @NotBlank
  private String descricao;

  @NotNull
  @DecimalMin(value = "0.0", inclusive = false)
  @Digits(integer = 10, fraction = 2)
  private BigDecimal valor;
}
