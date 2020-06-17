package jk.bsi.tcc.safi.web.error;

import java.io.Serializable;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * TODO: Write Documentation
 */
@Data
@Builder
@RequiredArgsConstructor
public class StandardError implements Serializable {
  private final Integer status;
  private final String message;
  private final Long timestamp;
}
