package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.*;

/**
 * ExtratoDetalhado entity.
 */
@Entity
@Table(name = "extrato_detalhado")
public class ExtratoDetalhado implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @Column(name = "data_inicial", nullable = false)
  private Date dataInicial;

  @Column(name = "data_final", nullable = false)
  private Date dataFinal;

  @Column(name = "saldo", nullable = false)
  private Double saldo;

  @OneToMany(mappedBy = "extratoDetalhado", fetch = FetchType.EAGER)
  private Set<Transacao> transacoes;

  @OneToOne(mappedBy = "extratoDetalhado")
  private InformacaoBancaria info;

  public ExtratoDetalhado() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getDataInicial() {
    return dataInicial;
  }

  public void setDataInicial(Date dataInicial) {
    this.dataInicial = dataInicial;
  }

  public Date getDataFinal() {
    return dataFinal;
  }

  public void setDataFinal(Date dataFinal) {
    this.dataFinal = dataFinal;
  }

  public Double getSaldo() {
    return saldo;
  }

  public void setSaldo(Double saldo) {
    this.saldo = saldo;
  }

  public Set<Transacao> getTransacoes() {
    return transacoes;
  }

  public void setTransacoes(Set<Transacao> transacoes) {
    this.transacoes = transacoes;
  }

  public InformacaoBancaria getInfo() {
    return info;
  }

  public void setInfo(InformacaoBancaria info) {
    this.info = info;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ExtratoDetalhado that = (ExtratoDetalhado) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
