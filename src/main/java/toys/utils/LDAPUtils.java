package toys.utils;

import com.unboundid.ldap.sdk.*;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;
import org.apache.commons.lang3.StringUtils;
import toys.ToysConsts;
import toys.ToysSecretKey;
import toys.exceptions.ToysRuntimeException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLSocketFactory;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static toys.ToysConsts.LDAP_PORT;

/**
 * Classe utilitários para operações com o servidor LDAP.
 *
 * @author Iran
 */
public class LDAPUtils {
    public static final String CFG_HOST = "host";
    public static final String CFG_BINDDN = "bindDN";
    public static final String CFG_CREDENTIALS = "password";
    public static final String CFG_BASEDN = "baseDN";
    public static final String CFG_SEARCH_EXPR = "searchExpr";
    private String host;
    private String bindDN;
    private String baseDN;
    private String password;
    private String searchExpr;

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
     * @param host       Endereço do servidor LDAP.
     * @param bindDN     Nome distinto do usuário principal de autenticação.
     * @param password   Senha que será utilizada com o usuário principal.
     * @param baseDN     Nome distinto do objeto raiz para realização de pesquisas.
     * @param searchExpr Expressão que será utilizada na pesquisa de usuários. Caso não seja informada será utilizado o valor
     *                   <code>(sAMAccountName=%s)</code>, onde %s será substituído pelo nome do usuário pesquisado.
     */
    public LDAPUtils(String host, String bindDN, String password, String baseDN, String searchExpr) {
        super();
        this.host = host;
        this.bindDN = bindDN;
        this.baseDN = baseDN;
        this.searchExpr = StringUtils.defaultString(searchExpr, "(" + ToysConsts.LA_ACC_NAME + "=%s)");
        try {
            this.password = Crypt.decode(password, ToysSecretKey.getInstance());
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            throw new ToysRuntimeException("Erro decodificando senha para acesso ao servidor LDAP.", e);
        }
    }

    /**
     * Retorna uma descrição das configurações da instância.
     */
    @Override
    public synchronized String toString() {
        return String.format("[LDAPUtils] host: %s, bindDN=%s, baseDN=%s, searchExpr=%s", host, bindDN, baseDN, searchExpr);
    }

    /**
     * Retorna uma conexão segura com o servidor LDAP utilizando os parâmetros.
     */
    public LDAPConnection getSSLConnection() throws GeneralSecurityException, LDAPException {
        SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
        SSLSocketFactory sslSocketFactory = sslUtil.createSSLSocketFactory();
        LDAPConnection conn = new LDAPConnection(sslSocketFactory);
        conn.connect(host, ToysConsts.LDAPS_PORT);
        conn.bind(bindDN, password);
        return conn;

    }

    /**
     * Pesquisa uma entrada pelo nome da conta.
     *
     * @param accountName Nome da conta.
     * @return Retorna a entrada encontrada ou null caso nenhuma seja correspondente.
     */
    public synchronized Entry pesquisar(String accountName) throws LDAPException {
        LDAPConnection conn = null;
        try {
            conn = new LDAPConnection(host, LDAP_PORT, bindDN, password);
            return pesquisar(conn, accountName);
        } finally {
            if (conn != null)
                conn.close();
        }
    }

    /**
     * Pesquisa uma conta de usuário no servidor LDAP utilizando uma conexão previamente estabelecida.
     *
     * @param conn Conexão com o servidor.
     * @return Retorna a entrada encontrada ou nulo.
     */
    public synchronized Entry pesquisar(LDAPConnection conn, String accountName) throws LDAPSearchException {
        SearchResult result = conn.search(baseDN, SearchScope.SUB, String.format(searchExpr, accountName));
        if (result.getEntryCount() == 1)
            return result.getSearchEntries().get(0);
        else if (result.getEntryCount() > 1)
            throw new ToysRuntimeException("Foram encontrados %d resultados.", result.getEntryCount());
        else
            return null;
    }

    /**
     * Tenta realizar autenticação e retorna se houve sucesso ou não.
     *
     * @param bindDN   DN para conexão.
     * @param password Senha de conexão.
     * @return Retorna o código de erro da autenticação ou null caso tenha ocorrido com sucesso.
     */
    public synchronized String autenticar(String bindDN, String password) throws LDAPException {
        LDAPConnection conn = null;
        try {
            conn = new LDAPConnection(host, LDAP_PORT, bindDN, password);
            return null;
        } catch (LDAPException e) {
            if (e.getResultCode().equals(ResultCode.INVALID_CREDENTIALS) && e.getDiagnosticMessage() != null) {
                String[] ss = e.getDiagnosticMessage().split(" *, *");
                if (ss.length > 2 && ss[2].matches("^data .+$"))
                    return ss[2].substring(5);
            }
            throw e;
        } finally {
            if (conn != null)
                conn.close();
        }
    }

    /**
     * Método de conveniência para invocar o {@link #autenticar(String, String)} utilizando um {@link Entry}.
     *
     * @param entry    Dados da entrada para obter o nome de usuário.
     * @param password Senha.
     * @return Retorna o código de erro da autenticação ou null caso tenha ocorrido com sucesso.
     */
    public synchronized String autenticar(Entry entry, String password) throws LDAPException {
        return autenticar(entry.getAttributeValue(ToysConsts.LA_DN), password);
    }

    /**
     * Altera a senha de uma conta para a nova senha.
     *
     * @param accountName Nome da conta.
     * @param novaSenha   Nova senha que será atribuida à conta.
     * @param forcarTroca Flag indicando se o usuário deve trocar a senha no próximo login.
     */
    public synchronized void alterarSenha(String accountName, String novaSenha, boolean forcarTroca) throws GeneralSecurityException, LDAPException {
        SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
        SSLSocketFactory sslSocketFactory = sslUtil.createSSLSocketFactory();
        LDAPConnection conn = null;
        try {
            conn = new LDAPConnection(sslSocketFactory);
            conn.connect(host, ToysConsts.LDAPS_PORT);
            conn.bind(bindDN, password);
            SearchResult result = conn.search(baseDN, SearchScope.SUB, String.format(searchExpr, accountName));
            if (result.getEntryCount() == 1) {
                List<SearchResultEntry> entries = result.getSearchEntries();
                SearchResultEntry entry = entries.get(0);
                String dn = entry.getAttributeValue(ToysConsts.LA_DN);

                List<Modification> mods = new ArrayList<>();

                // Modificação de troca de senha
                byte[] b = ('"' + novaSenha + '"').getBytes(StandardCharsets.UTF_16LE);
                mods.add(new Modification(ModificationType.REPLACE, ToysConsts.LA_UNICODE_PW, b));

                // Modificação para forçar a troca de senha no próximo logon
                if (forcarTroca)
                    mods.add(new Modification(ModificationType.REPLACE, ToysConsts.LA_PW_LAST_SET, "0"));

                LDAPResult ldpr = conn.modify(dn, mods);
                if (!ldpr.getResultCode().equals(ResultCode.SUCCESS))
                    throw new ToysRuntimeException("Ocorreu um erro durante a alteracao da senha. %s", ldpr.getResultString());

            } else {
                throw new ToysRuntimeException(result.getEntryCount() == 0 ?
                    String.format("Entrada nao encontrada. accountName=%s", accountName) :
                    String.format("Foram encontrados %d resultados. accountName=%s", result.getEntryCount(), accountName));
            }
        } finally {
            if (conn != null)
                conn.close();
        }
    }

    /**
     * Método de conveniência para efetuar troca de senha sem forçar troca.
     *
     * @see #alterarSenha(String, String, boolean)
     */
    public synchronized void alterarSenha(String accountName, String novaSenha) throws LDAPException, GeneralSecurityException {
        alterarSenha(accountName, novaSenha, false);
    }

    /**
     * Converte um timestamp LDAP para uma data Java.
     */
    public synchronized Date ldapTimestamp2Date(long nanos) {
        long millis = nanos / 10000000;
        return new Date((millis - ToysConsts.LDAP_UNIXTS) * 1000L);
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

    public String getSearchExpr() {
        return searchExpr;
    }

}
