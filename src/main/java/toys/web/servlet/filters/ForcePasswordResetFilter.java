package toys.web.servlet.filters;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Este filtro verifica se o usuário logado está com as credenciais expiradas redirecinando-o para uma página de troca de senha.
 * @author Iran
 * @since 17/10/2018
 * TODO A classe ainda não está completamente funcional
 */
public class ForcePasswordResetFilter implements Filter {
    private String resetPasswordPage;

    @Override
    public void init(FilterConfig filterConfig) {
        resetPasswordPage = filterConfig.getInitParameter("resetPasswordPage");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse)resp;
        String uri = request.getRequestURI();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!uri.contains("javax.faces.resource") && !StringUtils.endsWith(uri, resetPasswordPage) && principal instanceof User) {
            User user = (User)principal;
            if (!user.isCredentialsNonExpired()) {
                if (StringUtils.isNotBlank(resetPasswordPage))
                    response.sendRedirect(String.format("%s%s", request.getContextPath(), resetPasswordPage));
                else
                    response.sendError(HttpServletResponse.SC_EXPECTATION_FAILED, "Pagina de troca de senha nao configurada.");
                return;
            }
        }
        chain.doFilter(req, resp);
    }

}
