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

	/**
	 * Mensagem por módulo de autenticação.
	 */
	String LOGIN_MODULE_MESSAGE = "loginModule.message";

	/**
	 * Código de detalhe de erro de credenciais gerado por uma operação de autenticação num servidor LDAP.
	 */
	String LDAP_INVALID_CREDENTIAS_DETAIL = "ldap.invalidCredentials.errorCode";

	/**
	 * Especificação do caminho para a página de troca de senha.
	 */
	String PAGINA_TROCA_SENHA = "webapp.pagina.alteracaoSenha";

	/**
	 * Nome do atributo para armazenar a sessão.
	 */
	String CONTENT = "toys.web.servlet.content";

	/**
	 * Nome do atributo para armazenar o tipo de conteúdo.
	 */
	String CONTENT_TYPE = "toys.web.servlet.contentType";

	/**
	 * Nome do atributo para armazenar o nome do arquivo que será utilizado no conteúdo stream.
	 */
	String CONTENT_FILENAME = "toys.web.servlet.contentFilename";

}
