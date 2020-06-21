package jk.bsi.tcc.safi.web.rest;

import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import javax.xml.bind.JAXBException;
import jk.bsi.tcc.safi.service.InformacaoBancariaService;
import jk.bsi.tcc.safi.service.dto.InformacaoBancariaDto;
import jk.bsi.tcc.safi.service.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link jk.bsi.tcc.safi.domain.InformacaoBancaria}.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class InformacaoBancariaResource {
  private final InformacaoBancariaService informacaoBancariaService;

  /**
   * TODO: Write Documentation
   */
  @PostMapping("/informacoes-bancarias")
  public ResponseEntity<InformacaoBancariaDto> createInformacaoBancaria(@Valid @RequestBody
                                                                          InformacaoBancariaDto informacaoBancariaDto)
    throws IOException, JAXBException {
    log.debug("REST request to save InformacaoBancaria : {}", informacaoBancariaDto);
    informacaoBancariaDto = informacaoBancariaService.save(informacaoBancariaDto);
    return ResponseEntity.ok(informacaoBancariaDto);
  }

  /**
   * TODO: Write Documentation
   */
  @GetMapping("/informacoes-bancarias")
  public ResponseEntity<List<InformacaoBancariaDto>> getInformacoesBancarias(Pageable pageable) {
    log.debug("REST request to get a page of InformacaoBancaria");
    final Page<InformacaoBancariaDto> page = informacaoBancariaService.findAll(pageable);

    HttpHeaders headers = PaginationUtil
      .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequestUri(), page);

    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }
}
