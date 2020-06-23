package jk.bsi.tcc.safi.repository;

import jk.bsi.tcc.safi.domain.InformacaoBancaria;
import jk.bsi.tcc.safi.domain.InformacaoBancariaId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Spring Data repository for the {@link InformacaoBancaria} entity.
 */
@Repository
public interface InformacaoBancariaRepository
  extends JpaRepository<InformacaoBancaria, InformacaoBancariaId> {
  Page<InformacaoBancaria> findByUsuarioEmail(String email, Pageable pageable);

  List<InformacaoBancaria> findById_MesAno(Date mesAno);
}
