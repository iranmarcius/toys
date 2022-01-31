package toys.spring.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import toys.CollectionToys;
import toys.servlet.RequestDetailsBuilder;
import toys.servlet.SecurityToys;
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

import static toys.ToysConsts.HTTP_HEADER_ACCESS_TOKEN;
import static toys.ToysConsts.LOGGER_AUTH;

/**
 * Este handler é responsável por gerar um JWT e retorná-lo através do cabeçalho <code>Authorization</code>.
 * Além das informações comuns no token, serão armazenados também os privilégios do usuário.
 *
 * @author Iran
 * @since 11/2018
 */
public class JWTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private RequestCache requestCache = new HttpSessionRequestCache();
  private final String issuer;
  private final long ttl;
  private final Key key;
  private final boolean forbidOnNoAuthorities;

  /**
   * Construtor padrão.
   *
   * @param issuer                Emissor do token.
   * @param ttl                   Tempo de vida em milissegundos.
   * @param key                   Chave utilizada na assinatura.
   * @param forbidOnNoAuthorities Proíbe o acesso se o usuário autenticado não possuir nenhuma autoridade.
   */
  public JWTAuthenticationSuccessHandler(String issuer, long ttl, Key key, boolean forbidOnNoAuthorities) {
    super();
    this.issuer = issuer;
    this.ttl = ttl;
    this.key = key;
    this.forbidOnNoAuthorities = forbidOnNoAuthorities;
  }

  /**
   * Construtor de conveniência para invocar o construtor default informando o parâmetro
   * {@link #forbidOnNoAuthorities} com o valor <code>FALSE</code>.
   */
  public JWTAuthenticationSuccessHandler(String issuer, long ttl, Key key) {
    this(issuer, ttl, key, false);
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
    SavedRequest savedRequest = requestCache.getRequest(request, response);

    String principal = ToysSpringUtils.getPrincipalName();
    if (principal != null) {
      logger.debug("Gerando token JWT para o principal autenticado.");

      JwtBuilder builder = Jwts.builder()
        .setId(String.format("%d", System.currentTimeMillis()))
        .setIssuedAt(new Date())
        .setSubject(principal);

      try {
        var authorities = ToysSpringUtils.getAuthorities();
        if (authorities.isEmpty() && forbidOnNoAuthorities) {
          response.sendError(HttpServletResponse.SC_FORBIDDEN, "Usuário não possui nenhuma autoridade.");
          return;
        }

        if (logger.isDebugEnabled())
          logger.debug(String.format("Relacao de privilegios: %s", CollectionToys.asString(authorities, ";")));
        SecurityToys.setAuthorities(builder, authorities);
      } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
        logger.fatal("Erro codificando privilegios.", e);
        throw new IOException(e);
      }

      if (StringUtils.isNotBlank(issuer)) {
        logger.debug("Issuer: " + issuer);
        builder.setIssuer(issuer);
      }

      if (ttl > 0) {
        logger.debug("Tempo de vida em milissegundos: " + ttl);
        builder.setExpiration(new Date(System.currentTimeMillis() + ttl));
      }

      // Insere eventuais dados adicionais ao token.
      setExtraTokenData(builder, authentication);

      if (key != null)
        builder.signWith(key);

      String token = builder.compact();
      logger.debug("Token gerado: " + token);

      response.setHeader(HTTP_HEADER_ACCESS_TOKEN, token);

      LoggerFactory.getLogger(LOGGER_AUTH).info("Autenticacao bem sucedida - {}",
        RequestDetailsBuilder.builder(request).withAll(principal).build());

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

  public long getTtl() {
    return ttl;
  }

  public Key getKey() {
    return key;
  }

}
