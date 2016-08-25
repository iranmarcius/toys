package toys.utils;


import static toys.constants.LDAPConsts.*;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.SSLSocketFactory;

import com.unboundid.ldap.sdk.Entry;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldap.sdk.LDAPResult;
import com.unboundid.ldap.sdk.Modification;
import com.unboundid.ldap.sdk.ModificationType;
import com.unboundid.ldap.sdk.ResultCode;
import com.unboundid.ldap.sdk.SearchResult;
import com.unboundid.ldap.sdk.SearchResultEntry;
import com.unboundid.ldap.sdk.SearchScope;
import com.unboundid.util.ssl.SSLUtil;
import com.unboundid.util.ssl.TrustAllTrustManager;

import toys.constants.LDAPConsts;

/**
 * Classe utilitários para operações com o servidor LDAP.
 * @author Iran
 */
public class LDAPUtils {
	private Logger logger;
	private String host;
	private String bindDN;
	private String baseDN;
	private String password;

	/**
	 * Cria uma instância da classe utilizando os parâmetros informados.
	 * @param params Mapa de parâmetros. Deve conter valores das propriedades <code>ldap.host, ldap.bindDN, ldap.password, ldap.baseDN</code>.
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	public LDAPUtils(Map<?, ?> params) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
		this(
				(String)params.get("ldap.host"),
				(String)params.get("ldap.bindDN"),
				(String)params.get("ldap.password"),
				(String)params.get("ldap.baseDN"));
	}

	/**
	 * Cria uma instância da ferramenta setando as opções informadas.
	 * @param host Endereço do servidor LDAP.
	 * @param bindDN Nome distinto do usuário principal de autenticação.
	 * @param password Senha que será utilizada com o usuário principal.
	 * @param baseDN Nome distinto do objeto raiz para realização de pesquisas.
	 */
	public LDAPUtils(String host, String bindDN, String password, String baseDN) {
		super();
		logger= Logger.getLogger(getClass().getName());
		logger.fine(String.format("Inicializando utilitario LDAP. host=%s, bindDN=%s, baseDN=%s", host, bindDN, baseDN));
		this.host = host;
		this.bindDN = bindDN;
		this.password = password;
		this.baseDN = baseDN;
	}

	/**
	 * Retorna uma descrição das configurações da instância.
	 */
	@Override
	public synchronized String toString() {
		return String.format("[LDAPUtils] host: %s, bindDN=%s, baseDN=%s", host, bindDN, baseDN);
	}

	/**
	 * Pesquisa uma entrada pelo nome da conta.
	 * @param accountName Nome da conta.
	 * @return Retorna a entrada encontrada ou null caso nenhuma seja correspondente.
	 * @throws LDAPException
	 */
	public synchronized Entry pesquisar(String accountName) throws LDAPException {
		LDAPConnection conn = null;
		try {
			conn = new LDAPConnection(host, LDAP_PORT, bindDN, password);
			SearchResult result = conn.search(baseDN, SearchScope.SUB, String.format("(%s=%s)", LDAPConsts.LA_ACC_NAME, accountName));
			if (result.getEntryCount() == 1)
				return result.getSearchEntries().get(0);
			else if (result.getEntryCount() > 1)
				throw new RuntimeException(String.format("Foram encontrados %d resultados.", result.getEntryCount()));
			else
				return null;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * Tenta realizar autenticação e retorna se houve sucesso ou não.
	 * @param bindDN DN para conexão.
	 * @param password Senha de conexão.
	 * @return Retorna o código de erro da autenticação ou nullo caso tenha ocorrido com sucesso.
	 * @throws LDAPException
	 */
	public synchronized String autenticar(String bindDN, String password) throws LDAPException {
		LDAPConnection conn = null;
		try {
			conn = new LDAPConnection(host, LDAP_PORT, bindDN, password);
			return null;
		} catch (LDAPException e) {
			if (e.getResultCode().equals(ResultCode.INVALID_CREDENTIALS)) {
				if (e.getDiagnosticMessage() != null) {
					String[] ss = e.getDiagnosticMessage().split(" *, *");
					if (ss.length > 2 && ss[2].matches("^data .+$"))
						return ss[2].substring(5);
				}
			}
			throw e;
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	/**
	 * Altera a senha de uma conta para a nova senha.
	 * @param accountName Nome da conta.
	 * @param novaSenha Nova senha que será atribuida à conta.
	 * @param forcarTroca Flag indicando se o usuário deve trocar a senha no próximo login.
	 * @throws GeneralSecurityException
	 * @throws LDAPException
	 * @throws UnsupportedEncodingException
	 */
	public synchronized void alterarSenha(String accountName, String novaSenha, boolean forcarTroca) throws GeneralSecurityException, LDAPException, UnsupportedEncodingException {
		SSLUtil sslUtil = new SSLUtil(new TrustAllTrustManager());
		SSLSocketFactory sslSocketFactory = sslUtil.createSSLSocketFactory();
		LDAPConnection conn = null;
		try {
			conn = new LDAPConnection(sslSocketFactory);
			conn.connect(host, LDAPS_PORT);
			conn.bind(bindDN, password);
			SearchResult result = conn.search(baseDN, SearchScope.SUB, String.format("(%s=%s)", LA_ACC_NAME, accountName));
			if (result.getEntryCount() == 1) {
				List<SearchResultEntry> entries = result.getSearchEntries();
				SearchResultEntry entry = entries.get(0);
				String dn = entry.getAttributeValue(LA_DN);

				List<Modification> mods = new ArrayList<>();

				// Modificação de troca de senha
				byte[] b = ('"' + novaSenha + '"').getBytes("UTF-16LE");
				mods.add(new Modification(ModificationType.REPLACE, LA_UNICODE_PWD, b));

				// Modificação para forçar a troca de senha no próximo logon
				if (forcarTroca)
					mods.add(new Modification(ModificationType.REPLACE, LA_PWD_LAST_SET, "0"));

				LDAPResult ldpr = conn.modify(dn, mods);

				logger.info(String.format("Troca de senha realizada para a entrada %s. result=%s", accountName, ldpr.getResultString()));

			} else {
				throw new RuntimeException(result.getEntryCount() == 0 ?
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
	 * @throws GeneralSecurityException
	 * @throws UnsupportedEncodingException
	 * @throws LDAPException
	 * @see #alterarSenha(String, String, boolean)
	 */
	public synchronized void alterarSenha(String accountName, String novaSenha) throws LDAPException, UnsupportedEncodingException, GeneralSecurityException {
		alterarSenha(accountName, novaSenha, false);
	}

	/**
	 * Converte um timestamp LDAP para uma data Java.
	 */
	public synchronized Date ldapTimestamp2Date(long nanos) {
		long millis = nanos / 10000000;
		return new Date((millis - UNIXTS) * 1000l);
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

}
