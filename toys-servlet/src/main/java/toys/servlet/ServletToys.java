package toys.servlet;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import static toys.ToysConsts.HTTP_HEADER_X_FORWARDED_FOR;

/**
 * Classe utilitária com métodos relacionados ao protocolo HTTP.
 *
 * @author Iran
 */
public class ServletToys {

  private ServletToys() {
  }

  /**
   * Retorna o ip de origem de uma requisição considerando a existência considerando a existência do cabeçalho
   * <code>X-Forwarded-For</code>, que estará presente quando a requisição tiver sido direcionada a partir de um
   * servidor proxy.
   *
   * @param request OObjeto com os dados da requisição.
   * @return <code>String</code>
   */
  public static String getRemoteAddr(HttpServletRequest request) {
    var fwForAddr = request.getHeader(HTTP_HEADER_X_FORWARDED_FOR);
    return StringUtils.isNotBlank(fwForAddr) ? fwForAddr : request.getRemoteAddr();
  }

  /**
   * Retorna o valor do cabeçalho <code>User-Agent</code> enviado na requisição.
   *
   * @param request Referência para a requisição.
   * @return String
   */
  public static String getUserAgent(HttpServletRequest request) {
    return StringUtils.defaultString(request.getHeader("User-Agent"));
  }

  /**
   * Retorna se o cabeçalho User-Agent da requisição indica que ela está partindo de um dispositivo móvel.
   *
   * @param request Referência para a requisição.
   * @return <code>boolean</code>
   */
  public static boolean isMobile(HttpServletRequest request) {
    return getUserAgent(request).toLowerCase().contains("mobile");
  }


}
