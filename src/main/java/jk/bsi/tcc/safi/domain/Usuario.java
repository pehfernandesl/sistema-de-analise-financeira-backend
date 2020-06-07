package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Usuario entity.
 */
@Data
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column
  private Long id;

  @NotBlank
  @Column(name = "nome", nullable = false, length = 180)
  private String nome;

  @NotNull
  @Column(name = "data_nascimento", nullable = false)
  private LocalDate dataNascimento;

  @NotBlank
  @Column(name = "email", nullable = false, length = 254, unique = true)
  private String email;

  @NotBlank
  @Column(name = "senha_hash", nullable = false, length = 500)
  private String senha;

  @Column(name = "token", length = 500)
  private String token;
}
