package jk.bsi.tcc.safi.domain.enumeration;

import java.util.Arrays;
import java.util.List;

public enum TpTransacao {
  SAQUE(1L, "SAQUE"),
  COMPRA(2L, "COMPRA COM CARTÃO"),
  PAGAMENTO(3L, "PAGAMENTOS"),
  TED(4L, "TED"),
  DOC(5L, "DOC"),
  TARIFA(6L, "TARIFA"),
  REMUNERACAO(7L, "REMUNERAÇÃO"),
  OUTROS(8L, "OUTROS"),
  DEPOSITOS(9L, "DEPOSITOS");


  private TpTransacao(Long id, String descricao) {
    this.id = id;
    this.descricao = descricao;
  }

  private Long id;
  private String descricao;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }


  public static Long retornaTipoTransacao(String descOperacao) {
    List<String> saques = Arrays.asList("SAQUE", "SQ");
    List<String> compras = Arrays.asList("COMPRA", "CP ELECTRO");
    List<String> pagamentos = Arrays.asList("PGTO", "PAGAMENTO", "PG", "PAG");
    List<String> teds = Arrays.asList("TED", "TRANSFERENCIA");
    List<String> docs = Arrays.asList("DOC");
    List<String> tarifas = Arrays.asList("TARIFA", "TAXA");
    List<String> remuneracoes = Arrays.asList("REMUNERACAO", "REM");
    List<String> depositos = Arrays.asList("DP");

    String op = descOperacao.toUpperCase();

    if (isInList(op, saques)) {
      return TpTransacao.SAQUE.getId();
    }
    if (isInList(op, compras)) {
      return TpTransacao.COMPRA.getId();
    }
    if (isInList(op, pagamentos)) {
      return TpTransacao.PAGAMENTO.getId();
    }
    if (isInList(op, teds)) {
      return TpTransacao.TED.getId();
    }
    if (isInList(op, docs)) {
      return TpTransacao.DOC.getId();
    }
    if (isInList(op, tarifas)) {
      return TpTransacao.TARIFA.getId();
    }
    if (isInList(op, remuneracoes)) {
      return TpTransacao.REMUNERACAO.getId();
    }
    if (isInList(op, depositos)) {
      return TpTransacao.REMUNERACAO.getId();
    }

    return TpTransacao.OUTROS.getId();
  }

  private static boolean isInList(String desc, List<String> lista) {
    for (String l : lista) {
      if (desc.contains(l)) {
        return true;
      }
    }
    return false;
  }
}
