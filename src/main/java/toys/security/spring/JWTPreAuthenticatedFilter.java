package toys.security.spring;

import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import toys.utils.SecurityToys;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;

/**
 * Este filtro extrai e verifica a validade de um JWT provendo as informações necessárias para
 * posterior autorização.
 * @author Iran
 * @since 12/10/2018
 */
public class JWTPreAuthenticatedFilter extends AbstractPreAuthenticatedProcessingFilter {
    private Key key;

    /**
     * Construtor padrão.
     * @param authenticationManager {@link AuthenticationManager} que será utilizado para validar a autenticação.
     * @param key Chave utilizada no token.
     */
    public JWTPreAuthenticatedFilter(AuthenticationManager authenticationManager, Key key) {
        super();
        this.key = key;
        setAuthenticationManager(authenticationManager);
    }

    /**
     * Retorna a informação armazenada na claim <code>subject</code> do token.
     */
    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        Claims claims = SecurityToys.getClaims(request, key);
        if (claims != null) {
            String subject = claims.getSubject();
            logger.debug("Principal armazenado no token: " + subject);
            return subject;
        } else {
            return null;
        }
    }

    /**
     * Retorna o próprio token armazenado nos cabeçalhos da requisição para que a informação possa ser utilizada
     * posteriormente em outras operações.
     */
    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        logger.debug("Extraindo credenciais.");
        return SecurityToys.getJWT(request);
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

}
