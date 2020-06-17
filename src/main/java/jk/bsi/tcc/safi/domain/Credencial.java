package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Credencial implements Serializable {
  @Email
  @NotBlank
  private final String email;

  @NotBlank
  private final String senha;
}
