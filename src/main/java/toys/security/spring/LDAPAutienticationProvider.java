package toys.security.spring;

import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import toys.SecurityToys;
import toys.ToysConsts;
import toys.exceptions.ToysRuntimeException;
import toys.utils.LDAPUtils;

import javax.crypto.SecretKey;

import static toys.ToysConsts.*;

/**
 * Provedor de autenticação customizado para servidores LDAP baseado no provedor {@link ToysAuthenticationProvider}.
 * Além das configurações consideradas pelo {@link ToysAuthenticationProvider}, também serão considerados as seguintes:
 * <ul>
 * <li><b>ldap.host:</b> endereço do servidor LDAP que será utilizado no processo de autenticação.</li>
 * <li><b>ldap.bindDN:</b> DistinguishedName de um usuário com permissões para realizar pesquisas e trocas de senha.</li>
 * <li><b>ldap.password</b>: Senha do usuário especificado no parâmetro <b>bindDN</b>. Este valor deve ter sido codificado com o
 * método {@link toys.utils.Crypt#encode(String, SecretKey)} utilizando a chave fornecida por {@link SecurityToys#secretKey()}, do contrário
 * a autenticação não será possível.</li>
 * <li><b>ldap.baseDN:</b> DistinguishedName do local à partir de onde as pesquisas serão realizadas.</li>
 * <li><b>ldap.searchExpr:</b> expressão que será utilizada nas pesquisas de contas. O valor <b>%s</b> da expressão será substituído
 * pelo nome de usuário. Caso nenhum valor seja fornecido será utilizada a expressão <b>(sAMAccountName=%s)</b>.</li>
 * </ul>
 *
 * @author Iran
 * @since 09/10/2018
 */
@Component
public class LDAPAutienticationProvider extends ToysAuthenticationProvider {
    private LDAPUtils ldapUtils;

    public LDAPAutienticationProvider() {
        super();
        try {
            logger.info("Configurando provedor de autenticacao.");
            ldapUtils = new LDAPUtils(props);
            logger.info("Inicializado com sucesso: %s", ldapUtils);
        } catch (Exception e) {
            throw new ToysRuntimeException("Erro inicializando provedor de autenticacao LDAP.", e);
        }
    }

    /**
     * Tenta realizar a autenticação do usuário e em caso de sucesso, atribui suas roles.
     */
    @Override
    public Authentication authenticate(Authentication authentication) {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            throw new BadCredentialsException("Senha ou nome de usuario nao informados.");

        Entry entry = null;
        try {
            entry = ldapUtils.pesquisar(username);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("Erro pesquisando conta no servidor LDAP.", e);
        }

        // Verifica se a conta foi encontrada.
        if (entry == null)
            throw new UsernameNotFoundException(String.format("Nome de usuario nao encontrado. %s", username));

        // Verifica se a autenticação foi possível pela senha mestre e caso não seja realiza a autenticação
        // no servidor LDAP.
        if (!autenticarMasterKey(password)) {
            String accountDN = entry.getAttributeValue(ToysConsts.LA_DN);
            try {
                String error = ldapUtils.autenticar(accountDN, password);
                if (IC_ACCOUNT_DISABLED.equals(error))
                    throw new DisabledException(String.format("Conta desabilitada. error=%s", error));
                else if (IC_ACCOUNT_EXPIRED.equals(error))
                    throw new AccountExpiredException(String.format("Conta expirada. error=%s", error));
                else if (IC_AD_INVALID_CREDENTIALS.equals(error))
                    throw new BadCredentialsException(String.format("Credenciais invalidas. error=%s", error));
                else if (IC_USER_NOT_FOUND.equals(error))
                    throw new UsernameNotFoundException(String.format("Nome de usuario nao encontrado. error=%s", error));
                else if (IC_USER_MUST_RESET_CREDENTIAL.equals(error) || IC_CREDENTIAL_EXPIRED.equals(error))
                    throw new CredentialsExpiredException(String.format("A senha da conta deve ser trocada. error=%s", error));
            } catch (LDAPException e) {
                throw new InternalAuthenticationServiceException(String.format("Erro realizando autenticacao no servidor LDAP. ldap=%s, accountDN=%s", ldapUtils, accountDN));
            }
        }

        logger.debug("Autenticado pelo servidor LDAP.");


        // Retorna o token de autenticação
        return getAuthenticationToken(username, password);
    }

}
