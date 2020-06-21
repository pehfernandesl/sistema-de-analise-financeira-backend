package jk.bsi.tcc.safi.service.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.xml.bind.ValidationException;

public class CoreUtil {

  public static <T> boolean isNotEmpty(T object) {
    return !(object == null || (object instanceof String && ((String) object).length() == 0)
      || (object instanceof List && ((List<?>) object).size() <= 0));
  }

  public static <T> void validarCampo(T campo, String name) throws ValidationException {
    if (!isNotEmpty(campo)) {
      throw new ValidationException("Atibuto '" + name.toUpperCase() + "' n�o pode estar vazio!");
    }
  }

  public static char isCpfOrCpnj(String cpfCpnj) {
    if (cpfCpnj.length() <= 11) {
      return 'F';
    } else {
      return 'J';
    }
  }

  public static Date retornarDataFormatada(String stringData) {
    Date date = null;
    try {
      SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
      Date data = formato.parse(stringData);
      formato.applyPattern("dd/MM/yyyy");
      SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
      date = formatter.parse(formato.format(data));
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date == null ? new Date() : date;
  }

  public static Date dateFromXmlString(String xmlDate) {
    xmlDate = xmlDate.substring(0, 8);
    xmlDate = xmlDate.substring(6, xmlDate.length()) + "/" + xmlDate.substring(4, 6) + "/"
      + xmlDate.substring(0, 4);
    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
    Date date = null;
    try {
      date = formato.parse(xmlDate);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return date;
  }

  public static byte[] decodeBase64(String base64) {
    return Base64.getDecoder().decode(base64.replace("data:application/octet-stream;base64,", ""));
  }

  public static String encodeBase64(byte[] arrayBytes) {
    return "data:application/octet-stream;base64," + Base64.getEncoder().encodeToString(arrayBytes);
  }

  public static <T> String joinList(List<T> lista, String separator) {
    return lista.stream()
      .map(i -> i.toString())
      .collect(Collectors.joining(separator));
  }

}
