package jk.bsi.tcc.safi.service.mapper;

import jk.bsi.tcc.safi.domain.InformacaoBancaria;
import jk.bsi.tcc.safi.service.dto.InformacaoBancariaDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link jk.bsi.tcc.safi.domain.InformacaoBancaria} and its DTO {@link jk.bsi.tcc.safi.service.dto.InformacaoBancariaDto}.
 */
@Mapper(componentModel = "spring", uses = {InformacaoBancariaIdMapper.class})
public interface InformacaoBancariaMapper
  extends EntityMapper<InformacaoBancariaDto, InformacaoBancaria> {

  @Override
  InformacaoBancaria toEntity(InformacaoBancariaDto dto);

  @Override
  @Mapping(source = "extratoDetalhado.info", target = "extratoDetalhado.info", ignore = true)
  InformacaoBancariaDto toDto(InformacaoBancaria entity);
}
