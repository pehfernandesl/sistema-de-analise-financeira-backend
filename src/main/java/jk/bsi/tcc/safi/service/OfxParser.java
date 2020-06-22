package jk.bsi.tcc.safi.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationException;
import jk.bsi.tcc.safi.domain.enumeration.TpTransacao;
import jk.bsi.tcc.safi.domain.xml.ExtratoXML;
import jk.bsi.tcc.safi.domain.xml.TransactionXML;
import jk.bsi.tcc.safi.service.dto.ExtratoDetalhadoDto;
import jk.bsi.tcc.safi.service.dto.TransacaoDto;
import jk.bsi.tcc.safi.service.util.CoreUtil;
import org.springframework.stereotype.Service;

@Service
public class OfxParser {
  private static final String TAG_OFX = "<OFX>";
  private static final String TAG_BANK_LIST = "<BANKTRANLIST>";
  private static final String TAG_LISTA_TRANSACAO = "<STMTTRNS>";
  private static final String TAG_TRANSACAO = "<STMTTRN>";
  private static final String TAG_DT_INICIO = "<DTSTART>";
  private static final String TAG_DT_FINAL = "<DTEND>";
  private static final String TAG_TIPO = "<TRNTYPE>";
  private static final String TAG_DATA_OP = "<DTPOSTED>";
  private static final String TAG_VALOR = "<TRNAMT>";
  private static final String TAG_MEMO = "<MEMO>";
  private static final String TAG_SALDO = "<BALAMT>";

  private static final String TAG_OFX_END = "</OFX>";
  private static final String TAG_BANK_LIST_END = "</BANKTRANLIST>";
  private static final String TAG_LISTA_TRANSACAO_END = "</STMTTRNS>";
  private static final String TAG_TRANSACAO_END = "</STMTTRN>";
  private static final String TAG_DT_INICIO_END = "</DTSTART>";
  private static final String TAG_DT_FINAL_END = "</DTEND>";
  private static final String TAG_TIPO_END = "</TRNTYPE>";
  private static final String TAG_DATA_OP_END = "</DTPOSTED>";
  private static final String TAG_VALOR_END = "</TRNAMT>";
  private static final String TAG_MEMO_END = "</MEMO>";
  private static final String TAG_SALDO_END = "</BALAMT>";

  private static int ident = 0;

  public static ExtratoXML converterExtratoOFXtoExtratoXML(byte[] arrayBytes)
    throws IOException, JAXBException {
    String aux =
      "C:\\Users\\faris\\DEV\\Workspaces\\java\\spring\\safi\\sistema-de-analise-financeira-backend\\resources\\tmp";
    ExtratoXML extratoXML = null;
    File temp = null;
    File xml = null;
    try {
      temp = new File(
        aux + "\\extratoTemp" +
          ident + ".ofx");

//      temp = new File("..\\resources\\temp\\extratoTemp" + ident + ".ofx");
      Files.write(Paths.get(temp.getPath()), arrayBytes);

      validaOFX(temp);

      String ofx = formataOFX(temp);

      String novoXml = new String();
      Files.write(Paths.get(temp.getPath()), ofx.getBytes());
      novoXml = converteOfxParaXml(temp);

      xml = new File(aux + "\\extrato" + ident + ".xml");
      Files.write(Paths.get(xml.getPath()), novoXml.getBytes());

      // Conversão para classe java
      JAXBContext context = JAXBContext.newInstance(ExtratoXML.class);
      Unmarshaller um = context.createUnmarshaller();
      Object obj = um.unmarshal(xml);
      extratoXML = (ExtratoXML) obj;

      ident++;

    } finally {
      if (temp != null && temp.exists()) {
        Files.delete(Paths.get(temp.getPath()));
      }
      if (xml != null && xml.exists()) {
        Files.delete(Paths.get(xml.getPath()));
      }
    }
    return extratoXML;
  }

  private static void validaOFX(File temp) throws IOException, ValidationException {
    BufferedReader br = null;
    boolean ofxInit = false, ofxEnd = false, bankListInit = false, bankListEnd = false,
      strmtrnInit = false,
      strmtrnEnd = false;
    try {
      br = new BufferedReader(new FileReader(temp));
      String line = br.readLine();

      while (line != null) {
        if (line.contains(TAG_OFX)) {
          ofxInit = true;
        }
        if (line.contains(TAG_OFX_END)) {
          ofxEnd = true;
        }
        if (line.contains(TAG_BANK_LIST)) {
          bankListInit = true;
        }
        if (line.contains(TAG_BANK_LIST_END)) {
          bankListEnd = true;
        }
        if (line.contains(TAG_TRANSACAO)) {
          strmtrnInit = true;
        }
        if (line.contains(TAG_TRANSACAO_END)) {
          strmtrnEnd = true;
        }
        line = br.readLine();
      }
      if (!ofxInit || !ofxEnd || !bankListInit || !bankListEnd || !strmtrnInit || !strmtrnEnd) {
        throw new ValidationException("Extrato OXF deve conter a estrutra de tags :" + TAG_OFX + " "
          + TAG_BANK_LIST + " " + TAG_TRANSACAO + " " + TAG_TRANSACAO_END + " " +
          TAG_BANK_LIST_END + " "
          + TAG_OFX_END);
      }
    } catch (IOException e) {
      throw e;
    } finally {
      if (br != null) {
        br.close();
      }
    }

  }

  // Add uma linha antes de cada char '<'
  private static String formataOFX(File temp) throws IOException {
    BufferedReader br = null;
    String ofx = "";
    try {
      br = new BufferedReader(new FileReader(temp));
      String line = br.readLine();
      ofx = "";

      while (line != null) {
        for (int i = 0; i < line.length(); i++) {
          if (line.charAt(i) == '<') {
            ofx += System.lineSeparator();
          }
          ofx += line.charAt(i);
        }

        line = br.readLine();
      }

    } catch (IOException e) {
      throw e;
    } finally {
      if (br != null) {
        br.close();
      }
    }
    return ofx;
  }

  // Converte ofx para xml
  private static String converteOfxParaXml(File temp) throws IOException {
    BufferedReader br = null;
    StringBuilder novoXml = new StringBuilder();
    try {
      br = new BufferedReader(new FileReader(temp));
      String line = br.readLine();

      while (line != null) {

        /**
         * TODO: Descobrir qual a diferença na implementação da api do JAXB entre safi antigo e safi novo.
         * TODO: Gambiarra aqui
         */
        line = line.trim();

        if (line.contains(TAG_BANK_LIST)) {
          novoXml.append(removeTabulacao(line));
          novoXml.append(System.lineSeparator());

        }

        if (line.contains(TAG_DT_INICIO)) {
          novoXml.append(removeTabulacao(line));
          novoXml.append(TAG_DT_INICIO_END);
          novoXml.append(System.lineSeparator());
        }
        if (line.contains(TAG_DT_FINAL)) {
          novoXml.append(removeTabulacao(line));
          novoXml.append(TAG_DT_FINAL_END);
          novoXml.append(System.lineSeparator());
          novoXml.append(TAG_LISTA_TRANSACAO);
          novoXml.append(System.lineSeparator());
        }
        if (line.contains(TAG_TRANSACAO)) {
          novoXml.append(removeTabulacao(line));
          novoXml.append(System.lineSeparator());
        }
        if (line.contains(TAG_TIPO)) {
          novoXml.append(removeTabulacao(line));
          novoXml.append(TAG_TIPO_END);
          novoXml.append(System.lineSeparator());
        }
        if (line.contains(TAG_DATA_OP)) {
          novoXml.append(removeTabulacao(line));
          novoXml.append(TAG_DATA_OP_END);
          novoXml.append(System.lineSeparator());
        }
        if (line.contains(TAG_VALOR)) {
          novoXml.append(removeTabulacao(line).replace(",", "."));
          novoXml.append(TAG_VALOR_END);
          novoXml.append(System.lineSeparator());
        }
        if (line.contains(TAG_MEMO)) {
          novoXml.append(removeTabulacao(line));
          novoXml.append(TAG_MEMO_END);
          novoXml.append(System.lineSeparator());
        }
        if (line.contains(TAG_TRANSACAO_END)) {
          line = removeTabulacao(line);
          novoXml.append(line);
          novoXml.append(System.lineSeparator());
        }

        if (line.contains(TAG_SALDO)) {
          novoXml.append(TAG_LISTA_TRANSACAO_END);
          novoXml.append(System.lineSeparator());
          novoXml.append(removeTabulacao(line).replace(",", "."));
          novoXml.append(TAG_SALDO_END);
          novoXml.append(System.lineSeparator());
          novoXml.append(TAG_BANK_LIST_END);
          novoXml.append(System.lineSeparator());
        }

        line = br.readLine();
      }

    } catch (IOException e) {
      throw e;
    } finally {
      if (br != null) {
        br.close();
      }
    }
    return novoXml.toString();
  }

  private static String removeTabulacao(String texto) {
    if (texto.contains("\t")) {
      texto = texto.replace("\t", "");
    }
    return texto;
  }

  public static ExtratoDetalhadoDto converterExtratoXMLemInformacoesBancarias(
    ExtratoXML extratoXml) {
    ExtratoDetalhadoDto extratoDto = new ExtratoDetalhadoDto();
    extratoDto.setDataInicial(CoreUtil.dateFromXmlString(extratoXml.getDtInicial()));
    extratoDto.setDataFinal((CoreUtil.dateFromXmlString(extratoXml.getDtfinal())));
    extratoDto.setSaldo(extratoXml.getSaldo());

    if (CoreUtil.isNotEmpty(extratoXml.getTransactions())) {
      Set<TransacaoDto> listaDTO = new HashSet<TransacaoDto>();
      for (TransactionXML tXml : extratoXml.getTransactions()) {
        TransacaoDto dto = new TransacaoDto();
        dto.setValor(tXml.getValor());
        dto.setData(CoreUtil.dateFromXmlString(tXml.getData()));
        dto.setTipo(TpTransacao.retornaTipoTransacao(tXml.getDescOperacao()));
        dto.setExtratoDetalhado(extratoDto);
        listaDTO.add(dto);
      }
      extratoDto.setTransacoes(listaDTO);
    }

    return extratoDto;

  }

  public static void main(String[] args) throws JAXBException, IOException {
    ExtratoXML extratoXML = converterExtratoOFXtoExtratoXML(null);
    System.out.println(extratoXML);
  }
}
