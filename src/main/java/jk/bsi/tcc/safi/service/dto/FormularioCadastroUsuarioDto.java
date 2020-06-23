package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * A DTO for creating {@link jk.bsi.tcc.safi.domain.Usuario} entity.
 */
@Data
public class FormularioCadastroUsuarioDto implements Serializable {
  @Size(min = 1, max = 180)
  @NotBlank
  private String nome;

  private LocalDate dataNascimento;

  @Size(min = 3, max = 254)
  @NotBlank
  private String email;

  @Size(min = 8, max = 255)
  @NotBlank
  private String senha;
}
