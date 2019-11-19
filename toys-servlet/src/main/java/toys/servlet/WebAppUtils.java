package toys.servlet;

import javax.servlet.http.HttpServletRequest;

/**
 * Esta classe é utilizada na criação de mensagens de log para aplicações de ambiente de web pois registra,
 * juntamente com a mensagem informada, as informações sobre o usuário e o IP de origem da requisição.
 *
 * @author Iran Marcius
 */
public class WebAppUtils {

    private WebAppUtils() {
    }

    /**
     * Retorna uma string com os detalhes da requisição informada.
     *
     * @param request        Objeto com as informações da requisição.
     * @param principal      Nome do usuário autenticado caso existir ou nulo. Este parâmetro é informado separadamente para permitir
     *                       que seja extraído de qualquer fonte (ex, requisição, contexto de segurança do spring Security, etc).
     * @param incluirUri     Flag indicando se a URI invocada deve ser incluída.
     * @param incluirReferer Flag indicando se o referer deve ser incluído.
     * @return <code>String</code>
     */
    public static synchronized String requestDetails(HttpServletRequest request, String principal, boolean incluirUri, boolean incluirReferer) {
        StringBuilder sb = new StringBuilder("Requisicao [")
            .append(request.getRemoteAddr()).append(", ");
        if (principal != null)
            sb.append("username=").append(principal).append(", ");
        if (incluirUri)
            sb.append("uri=").append(request.getRequestURI()).append(", ");
        sb.append("ip_origem=").append(request.getRemoteAddr()).append(", ");
        if (incluirReferer)
            sb.append("referer=").append(request.getHeader("Referer")).append(", ");
        sb.setLength(sb.length() - 2);
        sb.append("]");
        return sb.toString();
    }

    /**
     * Método de conveniência para gerar a string da requisição utilizando o principal existente na requisição.
     *
     * @see #requestDetails(HttpServletRequest, String, boolean, boolean)
     */
    public static synchronized String requestDetails(HttpServletRequest request, boolean incluirUri, boolean incluirReferer) {
        return requestDetails(request, request.getUserPrincipal().getName(), incluirUri, incluirReferer);
    }

    /**
     * Retorna uma mensagem padrão para logs de aplicações web.
     *
     * @param request   Requisição original
     * @param principal Nome do usuário autenticado caso existir ou nulo. Este parâmetro é informado separadamente para permitir
     *                  que seja extraído de qualquer fonte (ex, requisição, contexto de segurança do spring Security, etc).
     * @param detalhes  Se for especificado TRUE inclui URI e REFERER na texto gerado.
     * @param msg       Mensagem que será criada
     * @param params    Dados que serão utilizados na formatação da mensagem através do método {@link String#format(String, Object...)}.
     * @return Retorna uma mensagem para registro de logs no seguinte formato:
     * <code>username - IP - [uri - referer - ] mensagem</code>.
     */
    public static synchronized String logMsg(HttpServletRequest request, String principal, boolean detalhes, String msg, Object... params) {
        String s = params == null || params.length == 0 ? msg : String.format(msg, params);
        if (principal != null)
            s += ", username=" + principal;
        s += ", ip=" + request.getRemoteAddr();
        if (detalhes)
            s += ", uri=" + request.getRequestURI() + ", referer=" + request.getHeader("Referer");
        return s;
    }

    /**
     * Método de conveniência para criar a mensagem utilizando o principal existente na requisição.
     *
     * @see #logMsg(HttpServletRequest, String, boolean, String, Object...)
     */
    public static synchronized String logMsg(HttpServletRequest request, boolean detalhes, String msg, Object... params) {
        return logMsg(request, request.getUserPrincipal().getName(), detalhes, msg, params);
    }

}
