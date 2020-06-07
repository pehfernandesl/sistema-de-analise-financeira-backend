package jk.bsi.tcc.safi.domain;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;

public class UsuarioTest {
  @Test
  public void dtoEqualsVerifier() {
    Usuario usuario1 = new Usuario();
    usuario1.setId(1L);

    Usuario usuario2 = new Usuario();
    usuario2.setId(2L);

    assertThat(usuario1).isNotEqualTo(usuario2);

    usuario2.setId(usuario1.getId());

    assertThat(usuario1).isEqualTo(usuario2);

    usuario2.setId(2L);

    assertThat(usuario1).isNotEqualTo(usuario2);

    usuario1.setId(null);

    assertThat(usuario1).isNotEqualTo(usuario2);
  }
}
