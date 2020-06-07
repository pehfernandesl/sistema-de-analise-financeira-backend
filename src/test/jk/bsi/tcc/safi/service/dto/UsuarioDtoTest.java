package jk.bsi.tcc.safi.service.dto;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;

public class UsuarioDtoTest {
  @Test
  public void dtoEqualsVerifier() {
    UsuarioDto usuarioDto1 = new UsuarioDto();
    usuarioDto1.setId(1L);

    UsuarioDto usuarioDto2 = new UsuarioDto();
    usuarioDto2.setId(2L);

    assertThat(usuarioDto1).isNotEqualTo(usuarioDto2);

    usuarioDto2.setId(usuarioDto1.getId());

    assertThat(usuarioDto1).isEqualTo(usuarioDto2);

    usuarioDto2.setId(2L);

    assertThat(usuarioDto1).isNotEqualTo(usuarioDto2);

    usuarioDto1.setId(null);

    assertThat(usuarioDto1).isNotEqualTo(usuarioDto2);
  }
}
