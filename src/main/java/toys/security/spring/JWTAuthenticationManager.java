package toys.security.spring;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import toys.ToysConsts;
import toys.exceptions.ToysException;
import toys.utils.SecurityToys;

import javax.crypto.SecretKey;
import java.util.List;
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
    private SecretKey key;

    public JWTAuthenticationManager(SecretKey key) {
        super();
        this.key = key;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) {
        try {
            String token = (String)authentication.getCredentials();
            Claims claims = SecurityToys.getClaims(token, key);
            if (claims != null) {
                var authorities = (List<String>) claims.get(ToysConsts.SECURITY_AUTHORITIES);
                List<GrantedAuthority> springAuthorities = authorities.stream()
                    .map(a -> new SimpleGrantedAuthority("ROLE_" + a))
                    .collect(Collectors.toList());
                return new PreAuthenticatedAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials(), springAuthorities);
            } else {
                throw new ToysException("Nenhuma claim do token.");
            }
        } catch (Exception e) {
            throw new PreAuthenticatedCredentialsNotFoundException("Erro extraindo informacoes de pre-autenticacao do token.", e);
        }
    }

}
