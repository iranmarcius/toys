package toys;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Classe utilitária com métodos relacionados ao protocolo HTTP.
 * @author Iran
 *
 */
public class HttpToys {

	/**
	 * Retorna o valor do cabeçalho <code>User-Agent</code> enviado na requisição.
	 * @param request Referência para a requisição.
	 * @return String
	 */
	public static String getUserAgent(HttpServletRequest request) {
		return StringUtils.defaultString(request.getHeader("User-Agent"));
	}

	/**
	 * Retorna se o cabeçalho User-Agent da requisição indica que ela está partindo de um dispositivo móvel.
	 * @param request Referência para a requisição.
	 * @return <code>boolean</code>
	 */
	public static boolean isMobile(HttpServletRequest request) {
		return getUserAgent(request).toLowerCase().indexOf("mobile") > -1;
	}

}
