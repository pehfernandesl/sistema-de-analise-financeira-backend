package jk.bsi.tcc.safi.web.rest;

import java.util.List;
import jk.bsi.tcc.safi.service.TransacaoService;
import jk.bsi.tcc.safi.service.dto.TransacaoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TransacaoResource {
  private final TransacaoService transacaoService;

  @GetMapping("/transacoes")
  public ResponseEntity<List<TransacaoDto>> getTransacoes() {
    final List<TransacaoDto> transacoes = transacaoService.getTransacoesForChart();
    return ResponseEntity.ok(transacoes);
  }
}
