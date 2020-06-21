package jk.bsi.tcc.safi.domain.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "BANKTRANLIST")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExtratoXML {

  @XmlElement(name = "DTSTART")
  private String dtInicial;

  @XmlElement(name = "DTEND")
  private String dtfinal;

  @XmlElementWrapper(name = "STMTTRNS")
  @XmlElement(name = "STMTTRN")
  private List<TransactionXML> transactions;

  @XmlElement(name = "BALAMT", type = Double.class)
  private Double saldo;
}
