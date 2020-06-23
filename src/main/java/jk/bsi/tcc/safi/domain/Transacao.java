package jk.bsi.tcc.safi.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.*;

/**
 * Transacao entity.
 */
@EqualsAndHashCode
@Entity
@Table(name = "transacao")
public class Transacao implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "data", nullable = false)
  private Date data;

  @Column(name = "tipo", nullable = true)
  private Long tipo;

  @Column(name = "valor", nullable = false)
  private Double valor;

  @ManyToOne
  @JoinColumn(name = "extrato_id")
  private ExtratoDetalhado extratoDetalhado;

  public Transacao() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public Long getTipo() {
    return tipo;
  }

  public void setTipo(Long tipo) {
    this.tipo = tipo;
  }

  public Double getValor() {
    return valor;
  }

  public void setValor(Double valor) {
    this.valor = valor;
  }

  public ExtratoDetalhado getExtratoDetalhado() {
    return extratoDetalhado;
  }

  public void setExtratoDetalhado(ExtratoDetalhado extratoDetalhado) {
    this.extratoDetalhado = extratoDetalhado;
  }

}

