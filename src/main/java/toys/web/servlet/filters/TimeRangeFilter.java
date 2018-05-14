package toys.web.servlet.filters;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * <p>Intercepta as requisições da aplicação verificando se a data de hoje está dentro de uma faixa especificada
 * e opcionalmente redirecionando o usuário para uma localidade.</p>
 * <p>O filtro aceita os seguintes parâmetros de inicialização:</p>
 * <ul>
 * 		<li><b>inicio:</b> data e hora iniciais no formato <code>dd/MM/yyyy hh:mm</code>. Este
 * 		parâmetro é opcional e caso não seja informado não será verificado.</li>
 * 		<li><b>termino:</b> data e hora finais no formato <code>dd/MM/yyyy hh:mm</code>. Este
 * 		parâmetro é opcional e caso não seja informado não será verificado.</li>
 * 		<li><b>redirect:</b> url para onde o usuário será redirecionado caso a data atual esteja
 * 		fora da faixa especificada. Caso não seja especificado será gerado um erro para o usuário.
 * 		Caso a uri seja especificada e nela conste a expressão <code>${cp}</code>, ela será substituída pelo
 * 		contexto da aplicação.</li>
 * </ul>
 * @author Iran
 */
public class TimeRangeFilter implements Filter {
    private static final Logger logger = LogManager.getFormatterLogger();

    private Date inicio;
    private Date termino;
    private String redirect;

    @Override
    public void init(FilterConfig fc) {
        logger.debug("Inicializando filtro de faixa de tempo.");
        String param = null;
        if (StringUtils.isNotBlank(param = fc.getInitParameter("inicio"))) {
            try {
                inicio = DateUtils.parseDate(param, "dd/MM/yyyy hh:mm");
                logger.debug("Data inicial: %s", param);
            } catch (ParseException e) {
                logger.error("Erro processando data inicial. %s", param);
            }
        }
        if (StringUtils.isNotBlank(param = fc.getInitParameter("termino"))) {
            try {
                termino = DateUtils.parseDate(param, "dd/MM/yyyy hh:mm");
                logger.debug("Data final: %s", param);
            } catch (ParseException e) {
                logger.error("Erro processando data final. %s", param);
            }
        }
        redirect = fc.getInitParameter("redirect");
        if (StringUtils.isNotBlank(redirect))
            logger.debug("Pagina de redirecionamento: %s", redirect);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if ((inicio != null && inicio.getTime() <= System.currentTimeMillis()) &&
            (termino != null && termino.getTime() >= System.currentTimeMillis())) {
            chain.doFilter(req, resp);
        } else {
            HttpServletRequest request = (HttpServletRequest)req;
            HttpServletResponse response = (HttpServletResponse)resp;
            logger.debug("Bloqueando requisicao: %s", request.getRequestURI());
            if (StringUtils.isNotBlank(redirect)) {
                response.sendRedirect(redirect.replaceAll("\\$\\{cp}", request.getContextPath()));
            } else {
                String msg = null;
                if (inicio != null && termino != null)
                    msg = String.format("Acesso aceito apenas entre %1$td/%1$tm/%1$tY, %1$tH:%1$tM e %2$td/%2$tm/%2$tY, %2$tH:%2$tM.", inicio, termino);
                else if (inicio != null)
                    msg = String.format("Acesso aceito apenas à partir de %1$td/%1$tm/%1$tY, %1$tH:%1$tM", inicio);
                else if (termino != null)
                    msg = String.format("Acesso aceito apenas até %1$td/%1$tm/%1$tY, %1$tH:%1$tM", termino);
                response.sendError(HttpServletResponse.SC_FORBIDDEN, msg);
            }
        }
    }

    @Override
    public void destroy() {
        // Nada acontece aqui.
    }

}
