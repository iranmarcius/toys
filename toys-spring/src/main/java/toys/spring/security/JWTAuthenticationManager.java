package toys.spring.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import toys.exceptions.ToysException;
import toys.servlet.SecurityToys;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Este gerenciador de autenticação retorna um objeto de autenticação obtido à partir de um token JWT contendo
 * o nome de usuário, as credenciais e a relação de privilégios. Esta classe será utilizada em maior parte
 * para autenticar serviços REST cujo acesso é controlado por tokens JWT.
 *
 * @author Iran
 * @since 03/2019
 */
public class JWTAuthenticationManager implements AuthenticationManager {
    private final SecretKey key;
    private final String issuer;

    public JWTAuthenticationManager(SecretKey key, String issuer) {
        super();
        this.key = key;
        this.issuer = issuer;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            String token = (String) authentication.getCredentials();
            Claims claims = SecurityToys.getClaims(token, key);
            String tokenIssuer = claims.getIssuer();
            if (!tokenIssuer.equals(issuer))
                throw new SecurityException(String.format("O emissor do token nao e valido: %s", tokenIssuer));
            Set<String> authorities = SecurityToys.extractAuthorities(claims);
            if (authorities != null) {
                List<GrantedAuthority> springAuthorities = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
                return new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), springAuthorities);
            } else {
                throw new ToysException("Autoridades nao foram encontradas no token.");
            }
        } catch (Exception e) {
            throw new PreAuthenticatedCredentialsNotFoundException("Erro extraindo informacoes de pre-autenticacao do token.", e);
        }
    }

}
