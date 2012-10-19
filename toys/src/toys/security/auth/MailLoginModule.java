/*
 * Criado em 14/03/2011 09:12:43
 */

package toys.security.auth;

import java.security.Principal;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.commons.lang3.StringUtils;

import toys.constants.EmailConsts;

/**
 * Realiza autenticação em servidores de e-mail (POP3 ou IMAP). As seguintes opções podem ser informadas:
 * <ul>
 * 	<li><b>endereco:</b> endereço do servidor de e-mail (IP ou nome)</li>
 * 	<li><b>protocolo:</b> protocolo do servidor (POP3 ou IMAP)</li>
 * </ul>
 * @author Iran
 */
public class MailLoginModule extends BaseLoginModule {
	protected String endereco;
	protected String protocolo;

	private Properties properties;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		endereco = (String)options.get("endereco");
		protocolo = (String)options.get("protocolo");
	}

	@Override
	public boolean login() throws LoginException {

		// verifica se o endereço do servidor e o protocolo foram especificados
		if (StringUtils.isBlank(endereco) || StringUtils.isBlank(protocolo)) {
			log.error("As opcoes 'endereco' e 'protocolo' devem ser especificadas na configuracao do modulo.");
			return false;
		}

		// obtém as credenciais
		if (!getCredentials())
			return false;

		log.debug(String.format("Utilizando servidor de e-mail para autenticacao: endereco=%s, protocolo=%s.", endereco, protocolo));

		// define as propriedades e faz a autenticação no servidor de e-mail.
		if (properties == null) {
			properties = new Properties();
			properties.put(EmailConsts.HOST, endereco);
			properties.put(EmailConsts.STORE_PROTOCOL, protocolo);
		}

		Session session = Session.getDefaultInstance(properties);
		Store store = null;
		try {
			store = session.getStore();
			store.connect(username, password);
		} catch (Exception e) {
			log.error(String.format("Erro autenticando usuario %s no servidor %s.", username, endereco), e);
			return false;
		} finally {
			if (store != null)
				try {
					store.close();
				} catch (MessagingException e) {
					log.warn("Erro fechando conexao com o servidor de e-mail.", e);
				}
		}

		log.debug(String.format("Usuario %s autenticado com sucesso no servidor %s.", username, endereco));

		return true;
	}

	/**
	 * Cria o principal após a autenticação do usuário e adiciona-o ao subject.
	 */
	@Override
	public boolean commit() throws LoginException {
		Principal principal = criarPrincipal();
		if (!subject.getPrincipals().contains(principal))
			subject.getPrincipals().add(principal);
		log.debug(String.format("Autenticacao do usuario %s no servidor %s finalizada.", username, endereco));
		username = null;
		password = null;
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		log.debug(String.format("Autenticacao do usuario %s no servidor %s foi abortada.", username, endereco));
		username = null;
		password = null;
		principal = null;
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		log.debug(String.format("Fazendo logout do usuario %s.", username));
		return true;
	}

}
