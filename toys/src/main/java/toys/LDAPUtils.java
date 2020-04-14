package toys;

import com.unboundid.ldap.sdk.*;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import toys.exceptions.ToysLDAPException;
import toys.exceptions.ToysLDAPNotFoundException;
import toys.exceptions.ToysRuntimeException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static toys.ToysConsts.*;

/**
 * Classe utilitários para operações com o servidor LDAP.
 *
 * @author Iran
 */
public class LDAPUtils implements Serializable {
    public static final String CFG_HOST = "host";
    public static final String CFG_BINDDN = "bindDN";
    public static final String CFG_CREDENTIALS = "password";
    public static final String CFG_BASEDN = "baseDN";
    public static final String CFG_SEARCH_EXPR = "searchExpr";
    private static final long serialVersionUID = -7100346347048600852L;
    private final transient Logger logger = LogManager.getFormatterLogger(getClass());
    private String host;
    private String bindDN;
    private String baseDN;
    private String password;
    private String defaultSearchExpr;

    /**
     * Cria uma instância da classe utilizando os parâmetros informados.
     *
     * @param params Mapa de parâmetros. Deve conter valores das propriedades <b>host, bindDN, password, baseDN</b>.
     */
    public LDAPUtils(Map<?, ?> params) {
        this(
            (String) params.get(CFG_HOST),
            (String) params.get(CFG_BINDDN),
            (String) params.get(CFG_CREDENTIALS),
            (String) params.get(CFG_BASEDN),
            (String) params.get(CFG_SEARCH_EXPR)
        );
    }

    /**
     * Cria uma instância da classe utilizando os parâmetros informados.
     *
     * @param props Objeto do tipo {@link Properties} contendo os valores <b>host, bindDN, password, baseDN</b>.
     */
    public LDAPUtils(Properties props) {
        this(
            props.getProperty(CFG_HOST),
            props.getProperty(CFG_BINDDN),
            props.getProperty(CFG_CREDENTIALS),
            props.getProperty(CFG_BASEDN),
            props.getProperty(CFG_SEARCH_EXPR)
        );
    }

    /**
     * Cria uma instância da ferramenta setando as opções informadas.
     *
     * @param host              Endereço do servidor LDAP.
     * @param bindDN            Nome distinto do usuário principal de autenticação.
     * @param password          Senha que será utilizada com o usuário principal.
     * @param baseDN            Nome distinto do objeto raiz para realização de pesquisas.
     * @param defaultSearchExpr Expressão que será utilizada na pesquisa de usuários. Caso não seja informada será utilizado o valor
     *                          <code>(sAMAccountName=%s)</code>, onde %s será substituído pelo nome do usuário pesquisado.
     */
    public LDAPUtils(String host, String bindDN, String password, String baseDN, String defaultSearchExpr) {
        super();
        this.host = host;
        this.bindDN = bindDN;
        this.baseDN = baseDN;
        this.defaultSearchExpr = StringUtils.defaultString(defaultSearchExpr, "(" + LA_ACC_NAME + "=%s)");
        try {
            this.password = Crypt.decode(password, ToysSecretKey.getInstance());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new ToysRuntimeException("Erro decodificando senha para acesso ao servidor LDAP.", e);
        }
    }

    /**
     * Retorna uma descrição das configurações da instância.
     */
    @Override
    public String toString() {
        return String.format("[LDAPUtils] host: %s, bindDN=%s, baseDN=%s, defaultSearchExpr=%s", host, bindDN, baseDN, defaultSearchExpr);
    }

    /**
     * Cria e retorna uma nova conexão com o servidor.
     */
    public synchronized LDAPConnection getConnection() throws LDAPException {
        return new LDAPConnection(host, LDAP_PORT, bindDN, password);
    }

    /**
     * Cria e retorna uma nova conexão segura com o servidor.
     */
    public synchronized LDAPConnection getSSLConnection() throws GeneralSecurityException, LDAPException {
        var sslUtil = new SSLUtil(new TrustAllTrustManager());
        var sslSocketFactory = sslUtil.createSSLSocketFactory();
        var conn = new LDAPConnection(sslSocketFactory);
        conn.connect(host, LDAPS_PORT);
        conn.bind(bindDN, password);
        return conn;
    }

    /**
     * Pesquisa uma entrada contendo o valor informado utilizando a expressão de pesquisa
     * informada em {@link #defaultSearchExpr}. Uma nova conexão será feita para realização da pesquisa.
     *
     * @param value Valor a ser pesquisado.
     * @return Retorna a entrada encontrada ou null caso nenhuma seja correspondente.
     */
    public synchronized Entry query(String value) throws LDAPException, ToysLDAPException {
        try (LDAPConnection conn = getConnection()) {
            return query(conn, value);
        }
    }

    /**
     * Pesquisa ma entrada contendo o valor informado utilizado a expressão ´de pesquisa
     * informda em {@link #defaultSearchExpr}.
     *
     * @param conn  Conexão que será utilizada para pesquisa.
     * @param value Valor a ser pesquisado.
     * @return Retorna a entrada enontrada ou null aso nenhuma seja correspondente.
     */
    public synchronized Entry query(LDAPConnection conn, String value) throws LDAPSearchException, ToysLDAPException {
        return query(conn, value, defaultSearchExpr);
    }

    /**
     * Pesquisa uma conta de usuário no servidor LDAP utilizando uma conexão previamente estabelecida.
     *
     * @param conn       Conexão com o servidor.
     * @param value      Valor a ser pesquisado.
     * @param searchExpr Expressão que será utilizada na pesquisa.
     * @return Retorna a entrada encontrada ou nulo.
     */
    public synchronized Entry query(LDAPConnection conn, String value, String searchExpr) throws LDAPSearchException, ToysLDAPException {
        String searchPattern = String.format(searchExpr, value);
        logger.debug("Pesquisando conta %s. host=%s, baseDN=%s, searchPattern=%s", value, host, baseDN, searchPattern);
        SearchResult result = conn.search(baseDN, SearchScope.SUB, searchPattern);
        if (result.getEntryCount() == 1) {
            var entry = result.getSearchEntries().get(0);
            if (!entry.getDN().contains("Deleted Objects"))
                return result.getSearchEntries().get(0);
            else
                return null;
        } else if (result.getEntryCount() > 1) {
            throw new ToysLDAPException("Foram encontrados %d resultados.", result.getEntryCount(), this);
        } else
            return null;
    }

    /**
     * Tenta realizar autenticação e retorna se houve sucesso ou não.
     *
     * @param bindDN   DN para conexão.
     * @param password Senha de conexão.
     * @return Retorna o código de erro da autenticação ou null caso tenha ocorrido com sucesso.
     */
    public synchronized String authenticate(String bindDN, String password) throws LDAPException {
        logger.debug("Tentando autenticacao: host=%s, bindDN=%s", host, bindDN);
        if (bindDN.contains("Deleted Objects"))
            return IC_USER_NOT_FOUND;
        try (var conn = new LDAPConnection(host, LDAP_PORT, bindDN, password)) {
            return null;
        } catch (LDAPException e) {
            if (e.getResultCode().equals(ResultCode.INVALID_CREDENTIALS) && e.getDiagnosticMessage() != null) {
                String[] ss = e.getDiagnosticMessage().split(" *, *");
                if (ss.length > 2 && ss[2].matches("^data .+$")) {
                    String errorCode = ss[2].substring(5);
                    logger.error("Autenticacao no servidor ldap retornou o erro %s. bindDN=%s", errorCode, bindDN);
                    return errorCode;
                }
            }
            throw e;
        }
    }

    /**
     * Método de conveniência para invocar o {@link #authenticate(String, String)} utilizando um {@link Entry}.
     *
     * @param entry    Dados da entrada para obter o nome de usuário.
     * @param password Senha.
     * @return Retorna o código de erro da autenticação ou null caso tenha ocorrido com sucesso.
     */
    public synchronized String authenticate(Entry entry, String password) throws LDAPException {
        return authenticate(entry.getDN(), password);
    }

    /**
     * Altera a senha de uma conta para a nova senha.
     *
     * @param entry       entrada da cnta cuja senha será trocada.
     * @param newPassword Nova senha que será atribuida à conta.
     * @param forceChange Flag indicando se o usuário deve trocar a senha no próximo login.
     */
    public synchronized void changePassword(Entry entry, String newPassword, boolean forceChange) throws GeneralSecurityException, LDAPException, ToysLDAPException {
        try (LDAPConnection conn = getSSLConnection()) {
            List<Modification> mods = new ArrayList<>();

            // Modificação de troca de senha
            byte[] b = ('"' + newPassword + '"').getBytes(StandardCharsets.UTF_16LE);
            mods.add(new Modification(ModificationType.REPLACE, ToysConsts.LA_UNICODE_PW, b));

            // Modificação para forçar a troca de senha no próximo logon
            int accControl = entry.getAttributeValueAsInteger(LA_USER_ACC_CONTROL);
            if (forceChange) {
                accControl |= LDAP_UACC_PASSWORD_EXPIRED;
                accControl &= ~LDAP_UACC_DONT_EXPIRE_PASSWORD;
                mods.add(new Modification(ModificationType.REPLACE, LA_PW_LAST_SET, "0"));
            } else {
                accControl &= ~LDAP_UACC_PASSWORD_EXPIRED;
                accControl |= LDAP_UACC_DONT_EXPIRE_PASSWORD;
            }
            mods.add(new Modification(ModificationType.REPLACE, LA_USER_ACC_CONTROL, Integer.toString(accControl)));

            LDAPResult ldpr = conn.modify(entry.getDN(), mods);
            if (!ldpr.getResultCode().equals(ResultCode.SUCCESS))
                throw new ToysLDAPException("Ocorreu um erro durante a alteracao da senha. result=%s", ldpr.getResultString(), this);
        }
    }

    /**
     * Realiza a troca de senha de uma entrada cujo nome da conta seja o fornecido.
     *
     * @param accountName Nome da conta. A entrada será pesquisada utilizadno a expressão de pesqusa definida em {@link #defaultSearchExpr}.
     * @param newPassword Nova senha.
     * @param forceChange Flag indicando se o usuário deve trocar a senha no próximo login.
     */
    public synchronized void changePassword(String accountName, String newPassword, boolean forceChange) throws LDAPException, GeneralSecurityException, ToysLDAPException {
        Entry entry = query(accountName);
        if (entry != null)
            changePassword(entry, newPassword, forceChange);
        else
            throw new ToysLDAPNotFoundException("Nenhuma entrada encontrada. value=%s", accountName, this);
    }

    /**
     * Método de conveniência para efetuar troca de senha sem ligar a flag para forçar troca no próximo logon.
     *
     * @see #changePassword(String, String, boolean)
     */
    public synchronized void changePassword(String accountName, String newPassword) throws LDAPException, GeneralSecurityException, ToysLDAPException {
        changePassword(accountName, newPassword, false);
    }

    public synchronized String getHost() {
        return host;
    }

    public synchronized String getBindDN() {
        return bindDN;
    }

    public synchronized String getBaseDN() {
        return baseDN;
    }

    public String getDefaultSearchExpr() {
        return defaultSearchExpr;
    }

}
