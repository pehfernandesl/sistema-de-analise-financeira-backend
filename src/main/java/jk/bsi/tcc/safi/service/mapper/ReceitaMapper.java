package jk.bsi.tcc.safi.service.mapper;

import java.util.Objects;
import jk.bsi.tcc.safi.domain.Receita;
import jk.bsi.tcc.safi.service.dto.ReceitaDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Receita} and its DTO {@link ReceitaDto}.
 */
@Mapper(componentModel = "spring")
public interface ReceitaMapper extends EntityMapper<ReceitaDto, Receita> {
  @Override
  ReceitaDto toDto(Receita entity);

  @Override
  Receita toEntity(ReceitaDto dto);

  /**
   * Instanciate entity from id.
   *
   * @param id desired id.
   * @return {@link Receita} instance with desired id.
   */
  default Receita fromId(Long id) {
    if (Objects.isNull(id)) {
      return null;
    }

    Receita receita = new Receita();
    receita.setId(id);
    return receita;
  }
}
