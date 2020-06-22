package jk.bsi.tcc.safi.service;

import java.io.IOException;
import java.util.Base64;
import javax.xml.bind.JAXBException;
import jk.bsi.tcc.safi.domain.ExtratoDetalhado;
import jk.bsi.tcc.safi.domain.xml.ExtratoXML;
import jk.bsi.tcc.safi.domain.InformacaoBancaria;
import jk.bsi.tcc.safi.domain.InformacaoBancariaId;
import jk.bsi.tcc.safi.domain.Usuario;
import jk.bsi.tcc.safi.repository.InformacaoBancariaRepository;
import jk.bsi.tcc.safi.service.dto.ExtratoDetalhadoDto;
import jk.bsi.tcc.safi.service.dto.InformacaoBancariaDto;
import jk.bsi.tcc.safi.service.mapper.ExtratoDetalhadoMapper;
import jk.bsi.tcc.safi.service.mapper.InformacaoBancariaMapper;
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
    final ExtratoDetalhado extratoDetalhado = extratoDetalhadoMapper.toEntity(extratoDetalhadoDto);

    /**
     * Setting extratoDetalhado...
     */
    informacaoBancaria.setExtratoDetalhado(extratoDetalhado);
    informacaoBancaria.setArquivoExtrato(decoded);

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
}
