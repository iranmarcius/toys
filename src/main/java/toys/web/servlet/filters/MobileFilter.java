package toys.web.servlet.filters;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static toys.ToysConsts.ATTR_MOBILE;

/**
 * Este filtro analisa o cabeçalho User-Agent da requisição para determinar se o dispositivo de origem é
 * um dispositivo móvel e armazenar a informação na sessão corrente.
 */
public class MobileFilter implements Filter {
    private final Logger logger = LogManager.getFormatterLogger();

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("Filtro para deteccao de dispositivo movel iniciado.");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object o = session.getAttribute(ATTR_MOBILE);
            if (!(o instanceof Boolean)) {
                String userAgent = request.getHeader("User-Agent");
                logger.debug("Dispositivo detectado: %s", userAgent);
                session.setAttribute(ATTR_MOBILE, StringUtils.containsIgnoreCase(userAgent, "Mobile"));
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        logger.info("Filtro para deteccao de dispositivo movel finalizado.");
    }

}
