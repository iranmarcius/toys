package toys.web.application;

import toys.security.spring.SpringSecurityToys;

import javax.servlet.http.HttpServletRequest;

/**
 * Esta classe é utilizada na criação de mensagens de log para aplicações de ambiente de web pois registra,
 * juntamente com a mensagem informada, as informações sobre o usuário e o IP de origem da requisição.
 * @author Iran Marcius
 */
public class WebAppUtils {

    private WebAppUtils() {
        super();
    }

    /**
     * Retorna uma string com os detalhes da requisição. A string irá conter o IP remoto, o usuário autenticado (caso exista),
     * o endereço invocado e o referer.
     * @param request Objeto com as informações da requisição.
     * @param incluirUri Flag indicando se a URI invocada deve constar nos detalhes.
     * @param incluirReferer Flag indicando se o referer deve constar nos detalhes.
     * @return <code>String</code>
     */
    public static synchronized String requestDetails(HttpServletRequest request, boolean incluirUri, boolean incluirReferer) {
        StringBuilder sb = new StringBuilder("Requisicao [")
            .append(request.getRemoteAddr()).append(", ");

        String principal = null;
        if (request.getUserPrincipal() != null)
            principal = request.getUserPrincipal().getName();
        else
            principal = SpringSecurityToys.getPrincipalName();
        if (principal != null)
            sb.append("username=").append(principal).append(", ");

        if (incluirUri)
            sb.append("uri=").append(request.getRequestURI()).append(", ");

        if (incluirReferer)
            sb.append("referer=").append(request.getHeader("Referer")).append(", ");

        sb.setLength(sb.length() - 2);
        sb.append("]");

        return sb.toString();
    }

    /**
     * Retorna uma mensagem padrão para logs de aplicações web.
     * @param request Requisição original
     * @param detalhes Se for especificado TRUE inclui URI e REFERER na texto gerado.
     * @param msg Mensagem que será criada
     * @param params Dados que serão utilizados na formatação da mensagem através do método {@link String#format(String, Object...)}.
     * @return Retorna uma mensagem para registro de logs no seguinte formato:
     * <code>username - IP - [uri - referer - ] mensagem</code>.
     */
    public static synchronized String logMsg(HttpServletRequest request, boolean detalhes, String msg, Object... params) {
        String s = params == null || params.length == 0 ? msg : String.format(msg, params);
        String username = SpringSecurityToys.getPrincipalName();
        if (username != null)
            s += ", username=" + username;
        s += ", ip=" + request.getRemoteAddr();
        if (detalhes)
            s += ", uri=" + request.getRequestURI() +", referer=" + request.getHeader("Referer");
        return s;
    }

}
