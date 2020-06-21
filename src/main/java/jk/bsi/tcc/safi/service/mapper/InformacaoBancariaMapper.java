package jk.bsi.tcc.safi.service.mapper;

import jk.bsi.tcc.safi.domain.InformacaoBancaria;
import jk.bsi.tcc.safi.service.dto.InformacaoBancariaDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link jk.bsi.tcc.safi.domain.InformacaoBancaria} and its DTO {@link jk.bsi.tcc.safi.service.dto.InformacaoBancariaDto}.
 */
@Mapper(componentModel = "spring", uses = {InformacaoBancariaIdMapper.class})
public interface InformacaoBancariaMapper
  extends EntityMapper<InformacaoBancariaDto, InformacaoBancaria> {

  @Override
  InformacaoBancaria toEntity(InformacaoBancariaDto dto);

  @Override
  InformacaoBancariaDto toDto(InformacaoBancaria entity);
}
