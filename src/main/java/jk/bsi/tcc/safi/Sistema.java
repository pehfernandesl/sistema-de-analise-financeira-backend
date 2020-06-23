package jk.bsi.tcc.safi;

import java.time.LocalDate;
import java.time.Month;
import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Sistema {
  public static void main(String[] args) {
    SpringApplication.run(Sistema.class, args);
  }

  @Profile({"test", "dev"})
  @Bean
  CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository,
                                      PasswordEncoder passwordEncoder) {
    return args -> {
      Usuario usuario = new Usuario();

      usuario.setEmail("admin@safi.net");
      usuario.setSenha(passwordEncoder.encode("admin"));
      usuario.setDataNascimento(LocalDate.of(1998, Month.JUNE, 29));
      usuario.setNome("Administrador");

      usuarioRepository.save(usuario);
    };
  }
}
