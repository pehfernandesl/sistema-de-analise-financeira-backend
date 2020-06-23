package jk.bsi.tcc.safi.service.mapper;

import jk.bsi.tcc.safi.domain.ExtratoDetalhado;
import jk.bsi.tcc.safi.service.dto.ExtratoDetalhadoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link jk.bsi.tcc.safi.domain.ExtratoDetalhado} and its DTO {@link jk.bsi.tcc.safi.service.dto.ExtratoDetalhadoDto}.
 */
@Mapper(componentModel = "spring")
public interface ExtratoDetalhadoMapper
  extends EntityMapper<ExtratoDetalhadoDto, ExtratoDetalhado> {
  @Override
  ExtratoDetalhado toEntity(ExtratoDetalhadoDto dto);

  @Override
  @Mapping(source = "info", target = "info", ignore = true)
  ExtratoDetalhadoDto toDto(ExtratoDetalhado entity);
}
