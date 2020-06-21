package jk.bsi.tcc.safi.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;

/**
 * A DTO for the {@link jk.bsi.tcc.safi.domain.InformacaoBancaria} entity.
 */
@Data
public class InformacaoBancariaDto implements Serializable {
  private InformacaoBancariaIdDto id;

  private byte[] arquivoExtrato;

  private String arquivoBase64;

  private LocalDate dataInclusao;

  private ExtratoDetalhadoDto extratoDetalhado;

  private UsuarioDto usuario;
}
