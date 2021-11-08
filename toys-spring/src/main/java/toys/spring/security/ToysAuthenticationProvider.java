package toys.spring.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import toys.Crypt;
import toys.JNDIToys;
import toys.ToysSecretKey;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Classe abstrata com métodos e propriedades comuns de provedores de autneticação e autorização customizados.
 * Todas as configurações dos provedores de autenticação que herdem esta classe devem armazenar suas propriedades
 * no arquivo <code>security.properties</code>.</p>
 * <p>Uma vez que o provedor de autenticação é utilizado somente em ambiente web, as confgurações são lidas
 * do container JNDI onde a aplicação estiver sendo executada. As configurações consideradas são as seguintes:</p>
 * <ul>
 * <li><b>toys/seguranca/masterKey:</b> senha mestre para permitir a autenticação de qualquer usuário.</li>
 * </ul>
 *
 * @author Iran
 * @since 09/10/2018
 */
public abstract class ToysAuthenticationProvider implements AuthenticationProvider {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private String masterKey;

    /**
     * Construtor padrão responsável por carregar as propriedades do aruqivo security.properties.
     */
    public ToysAuthenticationProvider() {
        super();
        logger.info("Inicializando provedor de autenticacao.");
        try {
            masterKey = JNDIToys.getMasterKey();
        } catch (NameNotFoundException e) {
            masterKey = null;
        } catch (NamingException e) {
            logger.warn("Erro obtendo a senha mestre.", e);
        }
    }

    /**
     * Certifica que as credenciais tenham sido fornecidas. Em casio negativo um erro será gerado.
     *
     * @param authentication Objeto contendo as informações de autenticação.
     * @return Retorna um array de strings onde o primeiro e o segundo elemento serão respectivamente o username
     * e a senha informados.
     * @throws BadCredentialsException Caso as credenciais não tenham sido informadas.
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
                String mkDecoded = Crypt.decrypt(masterKey.getBytes(), ToysSecretKey.getInstance());
                if (password.equals(mkDecoded)) {
                    logger.debug("Autenticado por senha mestre.");
                    return true;
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
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
        List<GrantedAuthority> roles = new ArrayList<>();
        String[] rolenames = getRoles(username);
        for (String rolename : rolenames) {
            logger.debug("Atribuindo role: {}", rolename);
            roles.add(new SimpleGrantedAuthority(rolename));
        }

        UserDetails principal = new User(username, password, true, true, credentialsNonExpired, true, roles);
        logger.debug("Criando principal: {}", principal);

        return new UsernamePasswordAuthenticationToken(principal, password, roles);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
