package jk.bsi.tcc.safi.repository;

import jk.bsi.tcc.safi.domain.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the {@link Receita} entity.
 */
@Repository
public interface ReceitaRepository extends JpaRepository<Receita, Long> {
}
