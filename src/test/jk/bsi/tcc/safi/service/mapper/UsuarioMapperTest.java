package jk.bsi.tcc.safi.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsuarioMapperTest {

  private UsuarioMapper usuarioMapper;

  @BeforeEach
  public void setUp() {
    usuarioMapper = new UsuarioMapperImpl();
  }

  @Test
  public void testEntityFromId() {
    Long id = 1L;

    assertThat(usuarioMapper.fromId(id).getId()).isEqualTo(id);
    assertThat(usuarioMapper.fromId(null)).isNull();
  }

}
