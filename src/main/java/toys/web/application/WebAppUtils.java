package toys.web.application;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

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
        Principal p = request.getUserPrincipal();
        if (p != null)
            s += ", username=" + p.getName();
        s += ", ip=" + request.getRemoteAddr();
        if (detalhes)
            s += ", uri=" + request.getRequestURI() +", referer=" + request.getHeader("Referer");
        return s;
    }

}
