package jk.bsi.tcc.safi.service;

import java.io.IOException;
import java.util.*;
import javax.xml.bind.JAXBException;

import jk.bsi.tcc.safi.domain.*;
import jk.bsi.tcc.safi.domain.xml.ExtratoXML;
import jk.bsi.tcc.safi.repository.InformacaoBancariaRepository;
import jk.bsi.tcc.safi.service.dto.ExtratoDetalhadoDto;
import jk.bsi.tcc.safi.service.dto.InformacaoBancariaDto;
import jk.bsi.tcc.safi.service.dto.TransacaoDto;
import jk.bsi.tcc.safi.service.mapper.ExtratoDetalhadoMapper;
import jk.bsi.tcc.safi.service.mapper.InformacaoBancariaMapper;
import jk.bsi.tcc.safi.service.util.CoreUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implementation for managing {@link jk.bsi.tcc.safi.domain.InformacaoBancaria}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class InformacaoBancariaService {
  private final ActiveUserService activeUserService;
  private final InformacaoBancariaRepository informacaoBancariaRepository;
  private final InformacaoBancariaMapper informacaoBancariaMapper;
  private final ExtratoDetalhadoMapper extratoDetalhadoMapper;
  private final OfxParser ofxParser;

  public InformacaoBancariaDto save(InformacaoBancariaDto informacaoBancariaDto)
    throws IOException, JAXBException {
    final Usuario usuario = activeUserService.getUsuario();
    InformacaoBancaria informacaoBancaria =
      informacaoBancariaMapper.toEntity(informacaoBancariaDto);

    InformacaoBancariaId id = informacaoBancaria.getId();
    id.setUsuarioId(usuario.getId());
    informacaoBancaria.setId(id);
    informacaoBancaria.setUsuario(usuario);

    /**
     * Decoding Byte[]...
     */
    final byte[] decoded =
      Base64.getDecoder().decode(informacaoBancariaDto.getArquivoBase64());
    final ExtratoXML extratoXML = ofxParser.converterExtratoOFXtoExtratoXML(decoded);
    final ExtratoDetalhadoDto extratoDetalhadoDto =
      ofxParser.converterExtratoXMLemInformacoesBancarias(extratoXML);
    final ExtratoDetalhado extratoDetalhado = this.normalizeExtratoBancario(extratoDetalhadoDto);


    /**
     * Setting extratoDetalhado...
     */
    informacaoBancaria.setArquivoExtrato(decoded);
    informacaoBancaria.setExtratoDetalhado(extratoDetalhado);

    /**
     * Saving entity...
     */
    informacaoBancaria = informacaoBancariaRepository.save(informacaoBancaria);
    return informacaoBancariaMapper.toDto(informacaoBancaria);
  }

  public Page<InformacaoBancariaDto> findAll(Pageable pageable) {
    final String email = activeUserService.getEmail();

    Page<InformacaoBancariaDto> informacoesBancariasDto =
      informacaoBancariaRepository.findByUsuarioEmail(email, pageable)
        .map(informacaoBancariaMapper::toDto);

    return informacoesBancariasDto;
  }

  private ExtratoDetalhado normalizeExtratoBancario(ExtratoDetalhadoDto dto){
    ExtratoDetalhado entity = new ExtratoDetalhado();
    entity.setId(CoreUtil.isNotEmpty(dto.getId()) ? dto.getId() : null);
    entity.setDataInicial(CoreUtil.isNotEmpty(dto.getDataInicial()) ? dto.getDataInicial() : null);
    entity.setDataFinal(CoreUtil.isNotEmpty(dto.getDataFinal()) ? dto.getDataFinal() : null);
    entity.setSaldo(CoreUtil.isNotEmpty(dto.getSaldo()) ? dto.getSaldo() : null);

    if (CoreUtil.isNotEmpty(dto.getTransacoes())) {
      entity.setTransacoes(this.normalizeDtoParaEntity(dto.getTransacoes(), entity));
    }

    return entity;
  }

  private Set<Transacao> normalizeDtoParaEntity(Set<TransacaoDto> listaDTO, ExtratoDetalhado extDet) {
    Set<Transacao> lista = new HashSet<>();
    for (TransacaoDto dto : listaDTO) {
      Transacao e = new Transacao();
      e.setId(CoreUtil.isNotEmpty(dto.getId()) ? dto.getId() : null);
      e.setData(CoreUtil.isNotEmpty(dto.getData()) ? dto.getData() : null);
      e.setTipo(CoreUtil.isNotEmpty(dto.getTipo()) ? dto.getTipo() : null);
      e.setValor(CoreUtil.isNotEmpty(dto.getValor()) ? dto.getValor() : null);
      e.setExtratoDetalhado(extDet);

      lista.add(e);
    }
    return lista;
  }
}
