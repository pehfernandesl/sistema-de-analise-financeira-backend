package jk.bsi.tcc.safi.repository;

import jk.bsi.tcc.safi.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the {@link Usuario} entity.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
