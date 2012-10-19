/*
 * Criado em 27/02/2012 09:00:30 
 */

package toys.constants;

/**
 * Declaração de nomes de atributos de arquivos de configuração.
 * @author Iran
 */
public interface ConfigConsts {

	/**
	 * Algorítmo de criptografia utilizado.
	 */
	String SECURITY_ALGORITHM = "security.algorithm";
	
	/**
	 * Tamanho mínimo de nome de usuário.
	 */
	String SECURITY_USERNAME_MINLENGTH = "security.username.minLength";
	
	/**
	 * Tamanho mínimo de uma senha.
	 */
	String SECURITY_PASSWORD_MINLENGTH = "security.password.minLength";
	
	/**
	 * Endereço do servidor de SMTP.
	 */
	String EMAIL_SMTP_ADDRESS = "email.smtp.address";
	
	/**
	 * Nome do servidor de armazenamento de mensagens de e-mail (caixas postais).
	 */
	String EMAIL_STORE_ADDRESS = "email.store.address";
	
	/**
	 * Nome do protocolo do servidor de armazenamento de mensagens.
	 */
	String EMAIL_STORE_PROTOCOL = "email.store.protocol";
	
	/**
	 * Configuração da codificação de caracteres da aplicação.
	 */
	String CHARSET_ENCODING = "charset.encoding";
	
	/**
	 * Flag indicando se a aplicação está em modo de debug.
	 */
	String APPLICATION_DEBUG = "application.debug";

}
