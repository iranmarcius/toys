package toys.servlet.filters;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import toys.ToysConsts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Este filtro analisa o cabeçalho User-Agent da requisição para determinar se o dispositivo de origem é
 * um dispositivo móvel e armazenar a informação na sessão corrente.
 *
 * @author Iran
 */
public class MobileFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("Filtro para deteccao de dispositivo movel iniciado.");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object o = session.getAttribute(ToysConsts.ATTR_MOBILE);
            if (!(o instanceof Boolean)) {
                String userAgent = request.getHeader("User-Agent");
                logger.debug("Dispositivo detectado: {}", userAgent);
                session.setAttribute(ToysConsts.ATTR_MOBILE, StringUtils.containsIgnoreCase(userAgent, "Mobile"));
            }
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        logger.info("Filtro para deteccao de dispositivo movel finalizado.");
    }

}
