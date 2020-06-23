package jk.bsi.tcc.safi.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import jk.bsi.tcc.safi.service.DespesaService;
import jk.bsi.tcc.safi.service.dto.DespesaDto;
import jk.bsi.tcc.safi.service.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link jk.bsi.tcc.safi.domain.Despesa}.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class DespesaResource {
  private final DespesaService despesaService;

  /**
   * TODO: Write Documentation
   */
  @PostMapping("/despesas")
  public ResponseEntity<DespesaDto> createDespesa(@Valid @RequestBody DespesaDto despesaDto) throws
    URISyntaxException {
    log.debug("REST request to save Despesa : {}", despesaDto);
    despesaDto = despesaService.save(despesaDto);
    return ResponseEntity.created(new URI("/api/receitas/" + despesaDto.getId())).body(despesaDto);
  }

  /**
   * TODO: Write Documentation
   */
  @GetMapping("/despesas")
  public ResponseEntity<List<DespesaDto>> getDespesas(Pageable pageable) {
    log.debug("REST request to get a page of Despesas");
    final Page<DespesaDto> page = despesaService.findAll(pageable);

    HttpHeaders headers = PaginationUtil
      .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequestUri(), page);

    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * TODO: Write Documentation
   */
  @DeleteMapping("/despesas/{id}")
  public ResponseEntity<Void> deleteDespesa(@PathVariable(name = "id") Long id) {
    log.debug("REST request to delete a Despesa");
    despesaService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
