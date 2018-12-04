package toys.security.spring;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.Date;

/**
 * Este filtro é responsável por gerar um JWT e retorná-lo através do cabeçalho <code>Authorization</code>.
 * @author Iran
 * @since 13/11/2018
 */
public class JWTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private RequestCache requestCache = new HttpSessionRequestCache();
    private String issuer;
    private long ttl;
    private Key key;

    /**
     * construtor padrão.
     * @param issuer emissor do token.
     * @param ttl Tempo de vida em milissegundos.
     * @param key Chave utilizada na assinatura.
     */
    public JWTAuthenticationSuccessHandler(String issuer, long ttl, Key key) {
        super();
        this.issuer = issuer;
        this.ttl = ttl;
        this.key = key;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        String principal = SpringSecurityToys.getPrincipalName();
        if (principal != null) {
            logger.debug("Gerando token JWT para o principal autenticado.");

            JwtBuilder builder = Jwts.builder()
                .setId(String.format("%d", System.currentTimeMillis()))
                .setIssuedAt(new Date())
                .setSubject(principal);

            if (StringUtils.isNotBlank(issuer)) {
                logger.debug("Issuer: " + issuer);
                builder.setIssuer(issuer);
            }

            if (ttl > 0) {
                logger.debug("Tempo de vida em milissegundos: " + ttl);
                builder.setExpiration(new Date(System.currentTimeMillis() + ttl));
            }

            if (key != null)
                builder.signWith(SignatureAlgorithm.HS256, key);

            String token = builder.compact();
            logger.debug("Token gerado: " + token);
            response.setHeader("Authorization", token);
        } else {
            logger.error("Nenhum nome de usuario encontrado no contexto para gerar o token.");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Nenhum usuario autenticado no contexto.");
        }

        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            return;
        }

        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParam != null && StringUtils.isNotBlank(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            return;
        }

        clearAuthenticationAttributes(request);
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        this.ttl = ttl;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

}
