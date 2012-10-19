package toys.web.application;

import java.security.Principal;
import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

/**
 * Esta classe é utilizada na criação de mensagens de log para aplicações de
 * ambiente de web pois registra, juntamente com a mensagem informada, as informações
 * sobre o usuário e o IP de origem da requisição. Alguns métodos a classe
 * {@link toys.application.Messages Messages} para criação de mensagens padrão de
 * aplicação.
 * @author Iran Marcius
 */
public class WebAppMessages {

	/**
	 * Cria uma mensagem padrão para os logs, com nome de usuário e IP de origem da
	 * requisição.
	 * @param username Nome de usuário. Se for nulo não será incluído na mensagem.
	 * @param ip IP de origem da requisição
	 * @param msg Mensagem que será criada. Pode conter indicadores de parâmetros
	 * no formato especificado na classe {@link MessageFormat MessageFormat}, que
	 * será utilizada na construção a mensagem caso existam parâmetros.
	 * @param data Valores dos parâmetros da mensagem. Caso seja nulo, a mensagem
	 * ficará sem modificações.
	 * @return Retorna uma mensagem para registro de logs no seguinte formato:
	 * <code>username - IP - mensagem</code>.
	 */
	public static String createMessage(String username, String ip, String msg,
		Object... data) {
		StringBuffer sb = new StringBuffer();
		if (username != null)
			sb.append(username).append(" - ");
		sb.append(ip).append(" - ");
		if (data == null) {
			sb.append(msg);
		} else {
			sb.append(MessageFormat.format(msg, data));
		}
		return sb.toString();
	}

	/**
	 * Cria uma mensagem padrão para os logs, com nome de usuário e IP de origem da
	 * requisição.
	 * @param username Nome de usuário. Se for nulo não será incluído na mensagem.
	 * @param ip IP de origem da requisição
	 * @param msg Mensagem que será criada. Pode conter indicadores de parâmetros
	 * no formato especificado na classe {@link MessageFormat MessageFormat}, que
	 * será utilizada na construção a mensagem caso existam parâmetros.
	 * @return Retorna uma mensagem para registro de logs no seguinte formato:
	 * <code>username - IP - mensagem</code>.
	 */
	public static String createMessage(String username, String ip, String msg) {
		return createMessage(username, ip, msg, (Object[])null);
	}

	/**
	 * Cria uma mensagem padrão para os logs, com nome de usuário e IP de origem
	 * obtidos a partir da requisição passada.
	 * @param request Requisição original
	 * @param msg Mensagem que será criada
	 * @param data Parâmetros que serão acrescentados à mensagem (utilizado pelo
	 * {@link MessageFormat MessageFormat}).
	 * @return Retorna uma mensagem para registro de logs no seguinte formato:
	 * <code>username - IP - mensagem</code>.
	 */
	public static String createMessage(HttpServletRequest request, String msg,
		Object... data) {
		Principal p = request.getUserPrincipal();
		String username = null;
		if (p != null)
			username = p.getName();
		return createMessage(username, request.getRemoteAddr(), msg, data);
	}

	/**
	 * Cria uma mensagem padrão para os logs, com nome de usuário e IP de origem
	 * obtidos a partir da requisição passada.
	 * @param request Requisição original
	 * @param msg Mensagem que será criada
	 * @return Retorna uma mensagem para registro de logs no seguinte formato:
	 * <code>username - IP - mensagem</code>.
	 */
	public static String createMessage(HttpServletRequest request, String msg) {
		return createMessage(request, msg, (Object[])null);
	}

}
