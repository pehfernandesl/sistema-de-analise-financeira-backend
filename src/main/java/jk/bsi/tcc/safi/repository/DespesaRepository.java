package jk.bsi.tcc.safi.repository;

import jk.bsi.tcc.safi.domain.Despesa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the {@link Despesa} entity.
 */
@Repository
public interface DespesaRepository extends JpaRepository<Despesa, Long> {
  Page<Despesa> findByUsuarioEmail(String email, Pageable pageable);
}
