package toys.spring.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import toys.CollectionToys;
import toys.Crypt;
import toys.spring.ToysSpringUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import static toys.ToysConsts.*;

/**
 * Este handler é responsável por gerar um JWT e retorná-lo através do cabeçalho <code>Authorization</code>.
 * Além das informações comuns no token, serão armazenados também os privilégios do usuário.
 *
 * @author Iran
 * @since 11/2018
 */
public class JWTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    protected final Logger localLogger = LogManager.getFormatterLogger(getClass());
    private RequestCache requestCache = new HttpSessionRequestCache();
    private String issuer;
    private long ttl;
    private Key key;

    /**
     * Construtor padrão.
     *
     * @param issuer Emissor do token.
     * @param ttl    Tempo de vida em milissegundos.
     * @param key    Chave utilizada na assinatura.
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

        String principal = ToysSpringUtils.getPrincipalName();
        if (principal != null) {
            localLogger.debug("Gerando token JWT para o principal autenticado.");

            JwtBuilder builder = Jwts.builder()
                .setId(String.format("%d", System.currentTimeMillis()))
                .setIssuedAt(new Date())
                .setSubject(principal);

            var authorities = ToysSpringUtils.getAuthorities();
            var authoritiesExpr = CollectionToys.asString(authorities, ";");
            localLogger.debug("Privilegios: %s", authoritiesExpr);
            try {
                var encodedAuthoritiesExpr = Crypt.encode(authoritiesExpr, key);
                localLogger.debug("Privilegios codificados: %s", encodedAuthoritiesExpr);
                builder.claim(SECURITY_AUTHORITIES, encodedAuthoritiesExpr);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
                localLogger.fatal("Erro codificando privilegios.", e);
                throw new IOException(e);
            }

            if (StringUtils.isNotBlank(issuer)) {
                localLogger.debug("Issuer: " + issuer);
                builder.setIssuer(issuer);
            }

            if (ttl > 0) {
                localLogger.debug("Tempo de vida em milissegundos: " + ttl);
                builder.setExpiration(new Date(System.currentTimeMillis() + ttl));
            }

            setExtraTokenData(builder, authentication);

            if (key != null)
                builder.signWith(SignatureAlgorithm.HS256, key);

            String token = builder.compact();
            localLogger.debug("Token gerado: " + token);

            response.setHeader(HTTP_HEADER_ACCESS_TOKEN, token);

            // Registra a autenticação no log
            StringBuilder sb = new StringBuilder()
                .append(principal).append(" - ")
                .append("ip=").append(request.getRemoteAddr());
            String referer = request.getHeader(HTTP_HEADER_REFERER);
            if (referer != null)
                sb.append(" - ").append(HTTP_HEADER_REFERER).append("=").append(referer);
            String userAgent = request.getHeader(HTTP_HEADER_USER_AGENT);
            if (userAgent != null)
                sb.append(" - ").append(HTTP_HEADER_USER_AGENT).append("=").append(userAgent);
            String xForwardedFor = request.getHeader(HTTP_HEADER_X_FORWARDED_FOR);
            if (xForwardedFor != null)
                sb.append(" - ").append(HTTP_HEADER_X_FORWARDED_FOR).append("=").append(xForwardedFor);
            LogManager.getLogger("auth").info(sb);

        } else {
            localLogger.error("Nenhum nome de usuario encontrado no contexto para gerar o token.");
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

    /**
     * Seta dados extras para o token. Por padrão nenhuma informação é adicionada, mas este método pode
     * ser redefinido em outras implementações.
     *
     * @param builder        Builder do token
     * @param authentication Dados da autenticação.
     */
    protected void setExtraTokenData(JwtBuilder builder, Authentication authentication) {
        // Nenhuma operação
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
