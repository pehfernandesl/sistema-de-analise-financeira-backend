package jk.bsi.tcc.safi.domain.enumeration;

public enum TpBanco {
  SANTANDER(33L, "SANTANDER"),
  CAIXA(104l,"CAIXA");

  private TpBanco(Long id, String descricao) {
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
}
