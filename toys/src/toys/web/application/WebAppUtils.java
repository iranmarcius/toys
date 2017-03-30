package toys.web.application;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

/**
 * Esta classe é utilizada na criação de mensagens de log para aplicações de
 * ambiente de web pois registra, juntamente com a mensagem informada, as informações
 * sobre o usuário e o IP de origem da requisição. Alguns métodos a classe
 * {@link toys.application.Messages Messages} para criação de mensagens padrão de
 * aplicação.
 * @author Iran Marcius
 */
public class WebAppUtils {

	private WebAppUtils() {
		super();
	}

	/**
	 * Cria uma mensagem padrão para os logs de aplicações de web.
	 * @param request Requisição original
	 * @param msg Mensagem que será criada
	 * @param detalhes Se for especificado TRUE inclui URI e REFERER na mensagem.
	 * @param data Dados que serão utilizados na formatação da mensagem através do método {@link String#format(String, Object...)}.
	 * @return Retorna uma mensagem para registro de logs no seguinte formato:
	 * <code>username - IP - mensagem</code>.
	 */
	public static synchronized String logMsg(HttpServletRequest request, String msg, Object... data) {
		String s = data == null || data.length == 0 ? msg : String.format(msg, data);
		Principal p = request.getUserPrincipal();
		if (p != null)
			s += ", username=" + p.getName();
		return s +
				", ip=" + request.getRemoteAddr() +
				", uri=" + request.getRequestURI() +
				", referer=" + request.getHeader("Referer");
	}

}
