package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
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

  @Email
  @NotBlank
  @Column(name = "email", nullable = false, length = 254, unique = true)
  private String email;

  @NotBlank
  @Column(name = "senha_hash", nullable = false, length = 500)
  private String senha;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
  private Set<Receita> receitas = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
  private Set<Despesa> despesas = new HashSet<>();
}
