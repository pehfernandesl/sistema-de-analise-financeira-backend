package jk.bsi.tcc.safi.service.mapper;

import java.util.Objects;
import jk.bsi.tcc.safi.domain.Despesa;
import jk.bsi.tcc.safi.service.dto.DespesaDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Despesa} and its DTO {@link DespesaDto}.
 */
@Mapper(componentModel = "spring")
public interface DespesaMapper extends EntityMapper<DespesaDto, Despesa> {
  /**
   * Instanciate entity from id.
   *
   * @param id desired id.
   * @return {@link Despesa} instance with desired id.
   */
  default Despesa fromId(Long id) {
    if (Objects.isNull(id)) {
      return null;
    }

    Despesa despesa = new Despesa();
    despesa.setId(id);
    return despesa;
  }
}
