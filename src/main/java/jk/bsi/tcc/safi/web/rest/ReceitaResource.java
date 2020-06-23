package jk.bsi.tcc.safi.web.rest;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import jk.bsi.tcc.safi.service.ReceitaService;
import jk.bsi.tcc.safi.service.dto.ReceitaDto;
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
 * REST controller for managing {@link jk.bsi.tcc.safi.domain.Receita}.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ReceitaResource {
  private final ReceitaService receitaService;

  /**
   * TODO: Write Documentation
   */
  @PostMapping("/receitas")
  public ResponseEntity<ReceitaDto> createReceita(@Valid @RequestBody ReceitaDto receitaDto) throws
    URISyntaxException {
    log.debug("REST request to save Receita : {}", receitaDto);
    receitaDto = receitaService.save(receitaDto);
    return ResponseEntity.created(new URI("/api/receitas/" + receitaDto.getId())).body(receitaDto);
  }

  /**
   * TODO: Write Documentation
   */
  @GetMapping("/receitas")
  public ResponseEntity<List<ReceitaDto>> getReceitas(Pageable pageable) {
    log.debug("REST request to get a page of Receitas");
    final Page<ReceitaDto> page = receitaService.findAll(pageable);

    HttpHeaders headers = PaginationUtil
      .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequestUri(), page);

    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * TODO: Write Documentation
   */
  @DeleteMapping("/receitas/{id}")
  public ResponseEntity<Void> deleteReceita(@PathVariable(name = "id") Long id) {
    log.debug("REST request to delete a Receita");
    receitaService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
