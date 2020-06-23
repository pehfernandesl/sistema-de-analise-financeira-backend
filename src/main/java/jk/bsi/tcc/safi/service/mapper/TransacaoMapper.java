package jk.bsi.tcc.safi.service.mapper;

import jk.bsi.tcc.safi.domain.Despesa;
import jk.bsi.tcc.safi.domain.Transacao;
import jk.bsi.tcc.safi.service.dto.DespesaDto;
import jk.bsi.tcc.safi.service.dto.TransacaoDto;
import org.mapstruct.Mapper;

import java.util.Objects;

/**
 * Mapper for the entity {@link Despesa} and its DTO {@link DespesaDto}.
 */
@Mapper(componentModel = "spring")
public interface TransacaoMapper extends EntityMapper<TransacaoDto, Transacao> {

}
