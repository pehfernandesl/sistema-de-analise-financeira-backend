package jk.bsi.tcc.safi.repository;

import jk.bsi.tcc.safi.domain.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data repository for the {@link jk.bsi.tcc.safi.domain.Transacao} entity.
 */
@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
