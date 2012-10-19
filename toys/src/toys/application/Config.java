package toys.application;

import java.io.Serializable;

import toys.constants.ConfigConsts;
import toys.extensions.ToysProperties;

/**
 * <p>Esta é a implementação básica utilizada para acessar as informações de um arquivo de
 * configurações de uma aplicação.</p>
 * <p>As configurações deverão estar no arquivo <code><b>config.properties</b></code> localizado
 * no raiz do classpath da aplicação.</p>
 * <p>A classe também define uma série de constantes com nomes de propriedades de configuração
 * que podem ser utilizadas.</p>
 * @author Iran Marcius
 * @deprecated Utilizar <b>commons-config</b>.
 */
public class Config implements Serializable {
	private static final long serialVersionUID = 2631466894592411895L;

	/**
	 * Classe para acesso ao arquivo de configuração.
	 * @see toys.extensions.ToysProperties
	 */
	protected final ToysProperties properties = new ToysProperties(Config.class.getClassLoader().getResourceAsStream("/config.properties"));

	/**
	 * Retorna a referência para a classe de acesso às propriedades.
	 * @see ToysProperties
	 */
	public ToysProperties getProperties() {
		return properties;
	}

	/**
	 * Retorna o nome do algorítmo que será utilizado para codificação de informações.<br/><br/>
	 * <strong>Valor padrão:</strong> MD5<br/>
	 * <strong>Constante:</strong> {@link ConfigConsts#SECURITY_ALGORITHM}
	 */
	public String getAlgorithm() {
		return properties.getProperty(ConfigConsts.SECURITY_ALGORITHM, "MD5");
	}

	/**
	 * Retorna o tamanho mínimo permitido para nomes de usuários.<br/><br/>
	 * <strong>Valor padrão:</strong> 4<br/>
	 * <strong>Constante:</strong> {@link ConfigConsts#SECURITY_USERNAME_MINLENGTH}
	 */
	public int getUsernameMinLength() {
		return properties.getInt(ConfigConsts.SECURITY_USERNAME_MINLENGTH, 4);
	}

	/**
	 * Retorna o tamanho mínimo permitido para senhas.<br/><br/>
	 * <strong>Valor padrão:</strong> 6<br/>
	 * <strong>Constante:</strong> {@link ConfigConsts#SECURITY_PASSWORD_MINLENGTH}
	 */
	public int getPasswordMinLength() {
		return properties.getInt(ConfigConsts.SECURITY_PASSWORD_MINLENGTH, 4);
	}

	/**
	 * Retorna o endereço do servidor de smtp que será utilizado no envio de e-mails.<br/><br/>
	 * <strong>Valor padrão:</strong> nenhum<br/>
	 * <strong>Constante:</strong> {@link ConfigConsts#EMAIL_SMTP_ADDRESS}
	 */
	public String getEmailSmtpAddress() {
		return properties.getProperty(ConfigConsts.EMAIL_SMTP_ADDRESS);
	}

	/**
	 * Retorna o endereço do servidor de armazenagem de e-mails.<br/><br/>
	 * <strong>Valor padrão:</strong> nenhum<br/>
	 * <strong>Constante:</strong> {@link ConfigConsts#EMAIL_STORE_ADDRESS}
	 */
	public String getEmailStoreAddress() {
		return properties.getProperty(ConfigConsts.EMAIL_STORE_ADDRESS);
	}

	/**
	 * Retorna o protocolo do servidor armazenamento de e-mails.
	 * @see ConfigurationKeys#EMAIL_STORE_PROTOCOL
	 * @return <code>String</code>
	 */
	public String getEmailStoreProtocol() {
		return properties.getProperty(ConfigConsts.EMAIL_STORE_PROTOCOL);
	}

	/**
	 * Retorna a codificação de caracteres da aplicação.<br/><br/>
	 * <strong>Valor padrão:</strong> UTF-8<br/>
	 * <strong>Constante:</strong> {@link ConfigConsts#CHARSET_ENCODING}
	 */
	public String getCharsetEncoding() {
		return properties.getProperty(ConfigConsts.CHARSET_ENCODING, "UTF-8");
	}

	/**
	 * Retorna se a aplicação está em modo de debug.<br/><br/>
	 * <strong>Valor padrão:</strong> false<br/>
	 * <strong>Constante:</strong> {@link ConfigConsts#APPLICATION_DEBUG}
	 */
	public boolean isDebugging() {
		return properties.getBoolean(ConfigConsts.APPLICATION_DEBUG, false);
	}

}
