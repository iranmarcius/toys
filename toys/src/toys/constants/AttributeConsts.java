package toys.constants;

/**
 * Definições de constantes com nomes que podem ser utilizados para armazenamento
 * de atributos em sessões, requests ou no contexto da aplicação.
 * @author Iran Marcius
 */
public interface AttributeConsts {

	/**
	 * Objeto de preferências de usuário.
	 */
	String PREFERENCES = "session.user.preferences";

	/**
	 * Nome do usuário autenticado na sessão atual.
	 */
	String OWNER = "session.user.name";

	/**
	 * Referência para as informações do usuário autenticado.
	 */
	String USER = "session.user.authenticationData";

	/**
	 * Menu do sistema.
	 */
	String MENU = "application.menu";

	/**
	 * Código para página de erro
	 */
	String SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";

	/**
	 * Mensagem para a página de erro
	 */
	String SERVLET_ERROR_MESSAGE = "javax.servlet.error.message";

	/**
	 * Nome do atributo utilizado para armazenar mensagens numa sessão.
	 */
	String SESSION_MESSAGE = "toys.web.sessionMessage";

	/**
	 * Nome do atributo onde será armazenada a instância do XStream
	 */
	String XSTREAM = "application.xstream";

}
