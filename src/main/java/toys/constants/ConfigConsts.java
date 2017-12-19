/*
 * Criado em 27/02/2012 09:00:30
 */

package toys.constants;

/**
 * Declaração de nomes de atributos de arquivos de configuração.
 * @author Iran
 */
public class ConfigConsts {

	/**
	 * Algorítmo de criptografia utilizado.
	 */
	public static final String SECURITY_ALGORITHM = "security.algorithm";

	/**
	 * Tamanho mínimo de nome de usuário.
	 */
	public static final String SECURITY_USERNAME_MINLENGTH = "security.username.minLength";

	/**
	 * Tamanho mínimo de uma senha.
	 */
	public static final String SECURITY_PASSWORD_MINLENGTH = "security.password.minLength";

	/**
	 * Endereço do servidor de SMTP.
	 */
	public static final String EMAIL_SMTP_ADDRESS = "email.smtp.address";

	/**
	 * Nome do servidor de armazenamento de mensagens de e-mail (caixas postais).
	 */
	public static final String EMAIL_STORE_ADDRESS = "email.store.address";

	/**
	 * Nome do protocolo do servidor de armazenamento de mensagens.
	 */
	public static final String EMAIL_STORE_PROTOCOL = "email.store.protocol";

	/**
	 * Configuração da codificação de caracteres da aplicação.
	 */
	public static final String CHARSET_ENCODING = "charset.encoding";

	/**
	 * Flag indicando se a aplicação está em modo de debug.
	 */
	public static final String APPLICATION_DEBUG = "application.debug";

	private ConfigConsts() {
	}

}
