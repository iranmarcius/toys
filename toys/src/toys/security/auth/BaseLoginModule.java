/*
 * Criado em 14/03/2011 09:08:30
 */

package toys.security.auth;

import java.security.Principal;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import toys.utils.NumberToys;

import com.sun.security.auth.UserPrincipal;

/**
 * Implementação básica para módulos de autenticação.
 * @author Iran
 */
public abstract class BaseLoginModule implements LoginModule {
	protected Log log;
	protected Subject subject;
	protected CallbackHandler callbackHandler;
	protected Map<String, Object> sharedState;
	protected Map<String, Object> options;
	protected String username;
	protected String password;
	protected UserPrincipal principal;
	protected boolean loginOk;
	protected boolean commitOk;

	public BaseLoginModule() {
		super();
		log = LogFactory.getLog(getClass());
		loginOk = false;
		commitOk = false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = (Map<String, Object>)sharedState;
		this.options = (Map<String, Object>)options;
	}

	/**
	 * Este método obtém as credenciais de autenticação e armazena os campos {@link #username} e {@link #password}
	 * utilizando o callbackHandler informado.
	 */
	protected boolean getCredentials() {

		log.debug("Obtendo nome de usuario e senha.");

		if (callbackHandler == null) {
			log.error("Nenhum callback handler foi informado.");
			return false;
		}

		NameCallback nc = new NameCallback("username");
		PasswordCallback pc = new PasswordCallback("password", false);
		try {
			callbackHandler.handle(new Callback[] {nc, pc});
		} catch (Exception e) {
			log.error("Erro executando callbacks para obtencao de usuario e senha.", e);
			return false;
		}

		username = nc.getName();
		password = new String(pc.getPassword());
		pc.clearPassword();

		return true;
	}

	/**
	 * Retorna o {@link Principal} que será adicionado ao subject na fase do commit. Caso haja necessidade
	 * este método pode ser sobrecarregado para retornar um outro objeto.
	 * @param nome Nome do usuário.
	 * @return {@link Principal}
	 */
	protected Principal criarPrincipal() {
		return new UserPrincipal(username);
	}

	/**
	 * Retorna o valor de uma opção como inteiro. Caso não exista uma opção
	 * com o nome informado, retorna null.
	 * @param nome Nome da opção.
	 * @param valorDefault Valor que será retornado caso a opção não exista.
	 * @return <code>Integer</code>
	 */
	protected Integer getIntOption(String nome, Integer valorDefault) {
		Object o = options.get(nome);
		if (o != null)
			return NumberToys.toInt((String)o, null);
		return null;
	}

	/**
	 * Retorna o valor de uma opção como boolean. Caso não exista nenhuma opção com o nome informado
	 * será retornado <code>FALSE</code>.
	 * @param nome Nome da opção.
	 * @return <code>boolean</code>
	 */
	protected boolean getBooleanOption(String nome) {
		Object o = options.get(nome);
		if (o == null)
			return false;
		return Boolean.valueOf((String)o).booleanValue();
	}

	@Override
	public abstract boolean login() throws LoginException;

	@Override
	public abstract boolean commit() throws LoginException;

	@Override
	public abstract boolean abort() throws LoginException;

	@Override
	public abstract boolean logout() throws LoginException;

}
