package toys;

import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toys.exceptions.ToysLDAPException;
import toys.exceptions.ToysLDAPNotFoundException;

import javax.naming.NamingException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import static toys.ToysConsts.IC_USER_NOT_FOUND;

/**
 * Esta classe concentra uma série de objetos {@link LDAPUtils} de forma a permitir pesquisa e troca de senha
 * de usuários em vários servidores.
 *
 * @author Iran
 * @since 04/2020
 */
public class LDAPUtilsHUB implements Serializable {
    private static final long serialVersionUID = 6586525207515328523L;
    private final transient Logger logger = LogManager.getFormatterLogger(getClass());
    private transient List<LDAPUtils> luList;

    /**
     * Construtor padrão.
     */
    public LDAPUtilsHUB() {
        luList = new ArrayList<>();
    }

    /**
     * Adiciona uma configuração de servidor a partir de valores armazenados no JNDI. As configurações serão lidas
     * através do método {@link JNDIToys#toProperties(String)}.
     *
     * @param basePash Caminho base para a leitira das configuraçoes.
     */
    public void addFromJNDI(String basePash) throws NamingException {
        var props = JNDIToys.toProperties(basePash);
        var lu = new LDAPUtils(props);
        luList.add(lu);
        logger.debug("Configuracao de servidor LDAP adicionada ao HUB: %s", lu);
    }

    /**
     * Pesquisa o valor informado em todos os servidores LDAP utilizado suas respectivas expressões de pesquisa.
     *
     * @param value Valor a ser pesquisado.
     * @return Retorna a entrada encontrada ou null caso não haja nenhuma correspondência.
     */
    public Entry query(String value) throws LDAPException, ToysLDAPException {
        for (LDAPUtils ldapUtils : luList) {
            var entry = ldapUtils.query(value);
            if (entry != null)
                return entry;
        }
        return null;
    }

    /**
     * Tenta realizar a autenticação em todos os servidores. A autenticação será considerada com sucesso
     * quando o primeiro servidor retornar nulo como resultado. Do contrário será retornado o código de erro
     * gerado pela tentativa de autenticação do último servidor.
     *
     * @param entry    Entrada cuja autenticação será tentada.
     * @param password Senha para autenticação.
     * @return Retorna nulo caso a autenticação tenha sucesso ou o código de erro de autenticação retornado
     * pelo último servidor tentado.
     */
    public String authenticate(Entry entry, String password) throws LDAPException {
        String result = null;
        for (LDAPUtils ldapUtils : luList) {
            result = ldapUtils.authenticate(entry, password);
            if (result == null || !result.equals(IC_USER_NOT_FOUND))
                return result;
        }
        return result;
    }

    /**
     * Realiza a troca de senha de uma conta em todos os servidores na qual ela for encontrada.
     *
     * @param accountName Nome da conta.
     * @param newPassword Nova senha.
     * @param forceChange Flag indicando se a senha deve ser trocada no próximo logon.
     */
    public void changePassword(String accountName, String newPassword, boolean forceChange) throws ToysLDAPNotFoundException {
        int t = 0;
        for (LDAPUtils ldapUtils : luList) {
            try {
                ldapUtils.changePassword(accountName, newPassword, forceChange);
                t++;
            } catch (LDAPException | GeneralSecurityException | ToysLDAPException e) {
                // Ignora onta não encontrada para que o loop de servidores continue sendo executado
            }
        }

        // Caso nenhuma troca tenha sido feita significa que a conta não foi encontrada em nenhum servdor
        if (t == 0)
            throw new ToysLDAPNotFoundException("Conta nao encontrada em nenhum dos servidores. accountName=%s", accountName);
    }

}