/*
 * Criado em 11/04/2005
 */

package toys.web.servlet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * <p>Este filtro permite ou bloqueia requisições através da verificação do cabeçalho <b>REFERER</b> do
 * protocolo HTTP. Os os referers permitidos devem ser declarados na configuração do filtro no
 * arquivo web.xml como no exemplo abaixo:</p>
 *
 * <pre>
 * &lt;filter&gt;
 *     &lt;filter-name&gt;referer&lt;/filter-name&gt;
 *     &lt;filter-class&gt;toys.servlet.filters.RefererFilter&lt;/filter-class&gt;
 *     &lt;init-param&gt;
 *         &lt;param-name&gt;allowedReferers&lt;/param-name&gt;
 *         &lt;param-value&gt;<i>regular expressions</i>&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 *     &lt;init-param&gt;
 *         &lt;param-name&gt;ignored&lt;/param-name&gt;
 *         &lt;param-value&gt;<i>regular expressions</i>&lt;/param-value&gt;
 *     &lt;/init-param&gt;
 * &lt;/filter&gt;
 * </pre>
 *
 * <p>Parâmetros do filtro:
 * <ul>
 * 	<li><b>allowedReferers:</b> Regular expression com a lista de referers permitidos separados por uma
 * quebra de linha</li>
 * <li><b>ignoredURLs:</b> Regular expressions com a lista de URLs que serão ignoradas na verificação.</li>
 * </ul>
 * </p>
 * @author Iran Marcius
 */
public class RefererFilter implements Filter {
    private final Logger logger = LogManager.getFormatterLogger();

    private String[] allowedReferers;
    private String[] ignoredURLs;

    /**
     * Inicialização do filtro.
     * @param config Configuração do filtro
     */
    public void init(FilterConfig config) throws ServletException {
        ServletContext sc = config.getServletContext();
        sc.log("Iniciando filtro verificador de referers");
        logger.info("Inicializando filtro para verificaco de referers.");

        // cria a lista de referers permitidos para a requisição
        String allowed = config.getInitParameter("allowedReferers");
        if (StringUtils.isNotBlank(allowed)) {
            allowedReferers = allowed.split("\\n");
            for (int i = 0; i < allowedReferers.length; i++) {
                allowedReferers[i] = allowedReferers[i].trim();
                logger.debug("Padrao de referer permitido: %s", allowedReferers[i]);
            }
        }

        // cria a lista de urls ignoradas na verificação
        String ignored = config.getInitParameter("ignoredURLs");
        if (StringUtils.isNotBlank(ignored)) {
            ignoredURLs = ignored.split("\\n");
            for (int i = 0; i < ignoredURLs.length; i++) {
                ignoredURLs[i] = ignoredURLs[i].trim();
                logger.debug("Padrao de URL ignorada: ", ignoredURLs[i]);
            }
        }
    }

    /**
     * Verifica se o referer recebido está dentro da lista de referers válidos.
     * @param request Requisição
     * @param response Resposta
     * @param fc Cadeia de filtros
     */
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain fc) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;

        // verifica se a URL está na lista de URLs ignoradas
        if ((ignoredURLs != null) && (ignoredURLs.length > 0)) {
            String requestURL = req.getRequestURL().toString();
            for (String ignored: ignoredURLs) {
                if (requestURL.matches(ignored)) {
                    logger.debug("URL ignorada: %s (%s)", requestURL, ignored);
                    fc.doFilter(req, resp);
                    return;
                }
            }
        }

        // verifica se o referer recebido é permitido
        if ((allowedReferers != null) && (allowedReferers.length > 0)) {
            boolean isAllowed = false;
            String referer = req.getHeader("REFERER");
            if (StringUtils.isNotBlank(referer)) {
                for (String allowed: allowedReferers) {
                    isAllowed = isAllowed || referer.matches(allowed);
                }
            }
            if (isAllowed) {
                logger.debug("Referer %s permitido para URL %s", referer, req.getRequestURL());
            } else {
                logger.debug("Referer %s bloqueado para URL %s", referer, req.getRequestURL());
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Requisicao nao permitida");
                return;
            }
        }

        fc.doFilter(request, response);
    }

    public void destroy() {
        // Nada acontece aqui.
    }

}
