package jk.bsi.tcc.safi.service.mapper;

import jk.bsi.tcc.safi.domain.InformacaoBancariaId;
import jk.bsi.tcc.safi.service.dto.InformacaoBancariaIdDto;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link InformacaoBancariaId} and its DTO {@link InformacaoBancariaIdDto}.
 */
@Mapper(componentModel = "spring")
public interface InformacaoBancariaIdMapper
  extends EntityMapper<InformacaoBancariaIdDto, InformacaoBancariaId> {
}
