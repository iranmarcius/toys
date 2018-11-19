package toys.security.spring;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.filter.OncePerRequestFilter;
import toys.utils.SecurityToys;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;

/**
 * Este filtro verifica a validade de tokens JWT.
 * @author Iran
 * @since 19/11/2018
 */
public class JWTValidationFilter extends OncePerRequestFilter {
    private Key key;

    public JWTValidationFilter(Key key) {
        this.key = key;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        logger.debug("Validando token JWT.");
        try {
            SecurityToys.getClaims(request, key); // se o token for decodificado com sucesso ele é válido.
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            logger.fatal("Token expirado");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Nao autorizado");
        }
    }

}
