package jk.bsi.tcc.safi.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import javax.persistence.EntityManager;
import jk.bsi.tcc.safi.Sistema;
import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.repository.UsuarioRepository;
import jk.bsi.tcc.safi.service.UsuarioService;
import jk.bsi.tcc.safi.service.dto.FormularioCadastroUsuarioDto;
import jk.bsi.tcc.safi.service.mapper.UsuarioMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link Usuario} REST controller.
 */
@AutoConfigureMockMvc
@WithMockUser
@SpringBootTest(classes = Sistema.class)
public class UsuarioResourceIT {

  private static final String DEFAULT_NOME = "Vincent Vega";
  private static final String UPDATED_NOME = "Carl Johnson";

  private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
  private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

  private static final String DEFAULT_EMAIL = "vincent.vega@mail.net";
  private static final String UPDATED_EMAIL = "carl.johnson@mail.net";

  private static final String DEFAULT_SENHA =
    "$2y$12$s9eIp51Si7yVd3j3KT2i2umKbNrzPPkIL34NXhxZcHQiR/4R9grY6";
  private static final String UPDATED_SENHA =
    "$2y$12$7ajrYVg8u/vk/v4hvHUx.OzgGiwtpRq//qkFLC6XG1ql7R9pIthTq";

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private UsuarioMapper usuarioMapper;

  @Autowired
  private MockMvc mockMvc;

  /**
   * Create an entity for this test.
   * <p>
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Usuario createEntity() {
    Usuario usuario = new Usuario();

    usuario.setNome(DEFAULT_NOME);
    usuario.setEmail(DEFAULT_EMAIL);
    usuario.setDataNascimento(DEFAULT_DATA_NASCIMENTO);
    usuario.setSenha(DEFAULT_SENHA);

    return usuario;
  }

  /**
   * Create an updated entity for this test.
   * <p>
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Usuario createUpdatedEntity() {
    Usuario usuario = new Usuario();

    usuario.setNome(UPDATED_NOME);
    usuario.setEmail(UPDATED_EMAIL);
    usuario.setDataNascimento(UPDATED_DATA_NASCIMENTO);
    usuario.setSenha(UPDATED_SENHA);

    return usuario;
  }

  @Test
  @Transactional
  public void createUsuario() throws Exception {
    int databaseSizeBeforeCreate = usuarioRepository.findAll().size();

    FormularioCadastroUsuarioDto formularioCadastroUsuarioDto = new FormularioCadastroUsuarioDto();

    formularioCadastroUsuarioDto.setNome(DEFAULT_NOME);
    formularioCadastroUsuarioDto.setDataNascimento(DEFAULT_DATA_NASCIMENTO);
    formularioCadastroUsuarioDto.setSenha(DEFAULT_SENHA);
    formularioCadastroUsuarioDto.setEmail(DEFAULT_EMAIL);

    mockMvc.perform(
      post("/api/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.convertObjectToJsonBytes(formularioCadastroUsuarioDto))
    ).andExpect(status().isCreated());

    List<Usuario> usuariosList = usuarioRepository.findAll();
    assertThat(usuariosList).hasSize(databaseSizeBeforeCreate + 1);
    Usuario usuarioTest = usuariosList.get(usuariosList.size() - 1);

    assertThat(usuarioTest.getNome()).isEqualTo(DEFAULT_NOME);
    assertThat(usuarioTest.getEmail()).isEqualTo(DEFAULT_EMAIL);
    assertThat(usuarioTest.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
    assertThat(usuarioTest.getSenha()).isEqualTo(DEFAULT_SENHA);
  }
}
