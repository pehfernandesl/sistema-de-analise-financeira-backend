package jk.bsi.tcc.safi.domain.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionXML {

  @XmlElement(name = "TRNTYPE")
  private String tipo;
  @XmlElement(name = "DTPOSTED")
  private String data;
  @XmlElement(name = "TRNAMT")
  private Double valor;
  @XmlElement(name = "MEMO")
  private String descOperacao;

  public TransactionXML(String tipo, String data, Double valor, String descOperacao) {
    super();
    this.tipo = tipo;
    this.data = data;
    this.valor = valor;
    this.descOperacao = descOperacao;
  }

  public TransactionXML() {
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public String getDescOperacao() {
    return descOperacao;
  }

  public void setDescOperacao(String descOperacao) {
    this.descOperacao = descOperacao;
  }
}
