package jk.bsi.tcc.safi.web.rest;

import jk.bsi.tcc.safi.service.RelatorioService;
import jk.bsi.tcc.safi.service.dto.RelatorioDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class RelatorioResource {
  private final RelatorioService relatorioService;

  @GetMapping("/relatorios")
  public ResponseEntity<RelatorioDto> getRelatorio(@RequestParam(value = "mes", required = true) Long mes) {
    final RelatorioDto relatorioDto = relatorioService.preencherRelatorio(mes);
    return ResponseEntity.ok(relatorioDto);
  }
}
