package toys.constants;

import toys.utils.FreemarkerUtils;
import toys.web.servlet.listeners.FreemarkerInitListener;

/**
 * Definições de constantes com nomes que podem ser utilizados para armazenamento
 * de atributos em sessões, requests ou no contexto da aplicação.
 * @author Iran Marcius
 */
public class AttributeConsts {

	/**
	 * Objeto de preferências de usuário.
	 */
	public static final String PREFERENCES = "session.user.preferences";

	/**
	 * Nome do usuário autenticado na sessão atual.
	 */
	public static final String OWNER = "session.user.name";

	/**
	 * Referência para as informações do usuário autenticado.
	 */
	public static final String USER = "session.user.authenticationData";

	/**
	 * Menu do sistema.
	 */
	public static final String MENU = "application.menu";

	/**
	 * Código para página de erro
	 */
	public static final String SERVLET_ERROR_STATUS_CODE = "javax.servlet.error.status_code";

	/**
	 * Mensagem para a página de erro
	 */
	public static final String SERVLET_ERROR_MESSAGE = "javax.servlet.error.message";

	/**
	 * Nome do atributo utilizado para armazenar mensagens numa sessão.
	 */
	public static final String SESSION_MESSAGE = "toys.web.sessionMessage";

	/**
	 * Nome do atributo onde será armazenada a instância do XStream
	 */
	public static final String XSTREAM = "application.xstream";

	/**
	 * Especificação do caminho para a página de troca de senha.
	 */
	public static final String PAGINA_TROCA_SENHA = "webapp.pagina.alteracaoSenha";

	/**
	 * Nome do atributo para armazenar a sessão.
	 */
	public static final String CONTENT = "toys.web.servlet.content";

	/**
	 * Nome do atributo para armazenar o tipo de conteúdo.
	 */
	public static final String CONTENT_TYPE = "toys.web.servlet.contentType";

	/**
	 * Nome do atributo para armazenar o nome do arquivo que será utilizado no conteúdo stream.
	 */
	public static final String CONTENT_FILENAME = "toys.web.servlet.contentFilename";

	/**
	 * Nome do atributo sob o qual será armazenada a instância do {@link FreemarkerUtils} através do {@link FreemarkerInitListener}.
	 */
	public static final String FREEMARKER_UTILS = "toys.utils.freemarkerUtils";

	private AttributeConsts() {
	}

}
