package toys.security.spring;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import toys.SecurityToys;
import toys.utils.Crypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * <p>Classe abstrata com métodos e propriedades comuns de provedores de autneticação e autorização customizados.
 * Todas as configurações dos provedores de autenticação que herdem esta classe devem armazenar suas propriedades
 * no arquivo <code>security.properties</code>.</p>
 * <p>As configurações consideradas por este provedor são as seguintes:</p>
 * <ul>
 * <li><b>masterKey:</b> senha mestre para permitir a autenticação de qualquer usuário.</li>
 * </ul>
 *
 * @author Iran
 * @since 09/10/2018
 */
public abstract class ToysAuthenticationProvider implements AuthenticationProvider {
    protected final Logger logger = LogManager.getFormatterLogger(getClass());
    private String masterKey;

    /**
     * Construtor padrão responsável por carregar as propriedades do aruqivo security.properties.
     */
    public ToysAuthenticationProvider() {
        super();
        logger.info("Inicializando provedor de autenticacao.");
        masterKey = ToysSecurityConfig.getInstance().getProperty("masterKey");
    }

    /**
     * verifica que as credenciais tenham sido informadas gerando um erro em caso negativo.
     * @param authentication Objeto contendo as informações de autenticação.
     * @return Retorna um array de strings onde o primeiro e o segundo elemento serão respectivamente o username
     * e a senha informados.
     */
    protected String[] ensureCredentials(Authentication authentication) {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw new BadCredentialsException("Nome de usuario ou senha nao informados.");
        return new String[] {username, password};
    }

    /**
     * Realiza a autenticação pela senha mestre caso ela exista.
     *
     * @param password Senha a ser verificada.
     * @return <code>boolean</code>
     */
    protected boolean autenticarMasterKey(String password) {

        if (StringUtils.isNotBlank(masterKey) && StringUtils.isNotBlank(password)) {
            try {
                String mkDecoded = Crypt.decode(masterKey, SecurityToys.secretKey());
                if (password.equals(mkDecoded)) {
                    logger.debug("Autenticado por senha mestre.");
                    return true;
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
                throw new InternalAuthenticationServiceException("Erro tentando decodificar senha mestre.", e);
            }
        }

        return false;

    }

    /**
     * Retorna um array de strings contendo os nomes das roles que devem ser atribuídas pelo provedor de autenticação
     * no caso da autenticação ser bem sucedida. A implmentação padrão deste método retorna somente uma role com o nome
     * <code>AUTHENTICADED_USER</code>. Classes que herdarem esta poderão sobrepor esta implementação para retornar uma
     * relação de roles apropriadas.
     *
     * @return <code>String[]</code>
     */
    protected String[] getRoles(String username) {
        return new String[] {"AUTHENTICADED_USER"};
    }

    /**
     * Retorna o token de autenticação que será devolvido aos filtros do Spring Security, criado à partir
     * das informações de autenticação.
     *
     * @return {@link UsernamePasswordAuthenticationToken}
     */
    protected UsernamePasswordAuthenticationToken getAuthenticationToken(String username, String password, boolean credentialsNonExpired) {

        // Caso o usuário tenha sido autenticado com sucesso, adiciona as roles e finaliza o processo de autenticação.
        var roles = new ArrayList<GrantedAuthority>();
        String[] rolenames = getRoles(username);
        for (String rolename : rolenames) {
            logger.debug("Atribuindo role: %s", rolename);
            roles.add(new SimpleGrantedAuthority("ROLE_" + rolename));
        }

        logger.debug("Criando principal: ");
        UserDetails principal = new User(username, password, true, true, credentialsNonExpired, true, roles);

        return new UsernamePasswordAuthenticationToken(principal, password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
