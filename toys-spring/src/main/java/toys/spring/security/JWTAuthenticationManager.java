package toys.spring.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
import toys.Crypt;
import toys.ToysConsts;
import toys.ToysSecretKey;
import toys.exceptions.ToysException;
import toys.servlet.SecurityToys;

import javax.crypto.SecretKey;
import java.util.Arrays;
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
    private final SecretKey key;

    public JWTAuthenticationManager(SecretKey key) {
        super();
        this.key = key;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        try {
            String token = (String) authentication.getCredentials();
            Claims claims = SecurityToys.getClaims(token, key);
            if (claims != null) {
                String encodedAuthoritiesExpr = (String) claims.get(ToysConsts.JWT_CLAIM_AUTHORITIES);
                String decodedAuthorities = Crypt.decode(encodedAuthoritiesExpr, ToysSecretKey.getInstance());
                String[] authorities = decodedAuthorities.split(";");
                List<GrantedAuthority> springAuthorities = Arrays.stream(authorities)
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
