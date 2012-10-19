package toys.web.struts.actions;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Esta ação possui implementações básicas comuns.
 * @author Iran Marcius
 */
public abstract class StandardAction extends ActionSupport {
	private static final long serialVersionUID = 3153776682129766507L;

	public static final String ERRO_INTERNO = "erroInterno";
	public static final String ERRO_COMMIT_LOG = "erroCommit.log";
	public static final String ERRO_ROLLBACK_LOG = "erroRollback.log";

	public static final String STATUS_ERROR_ACTION = "erro.action";
	public static final String STATUS_ERROR_FIELDS = "erro.fields";

	/**
	 * Referência para o logger da classe.
	 */
	protected transient Logger logger;

	/**
	 * Armazena o valor do token de transação para ações que utilizarem este
	 * recurso.
	 */
	protected String token;

	/**
	 * Construtor default.
	 */
	public StandardAction() {
		super();
		logger = Logger.getLogger(getClass());
	}

	/**
	 * Seta o valor do token de transação.
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Retorna o valor do token de transação.
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Salva um valor na sessão.
	 * @param attrName Nome do atributo sob o quel será salvo o valor
	 * @param value Valor a ser salvo
	 */
	protected void sessionPut(String attrName, Object value) {
		ActionContext.getContext().getSession().put(attrName, value);
	}

	/**
	 * Restaura um valor salvo na sessão.
	 * @param attrName Nome do atributo a ser acessado
	 */
	protected Object sessionGet(String attrName) {
		return ActionContext.getContext().getSession().get(attrName);
	}

	/**
	 * Remove um atributo armazenado na sessão.
	 * @param attrName Nome do atributo a ser removido
	 */
	protected void sessionRemove(String attrName) {
		ActionContext.getContext().getSession().remove(attrName);
	}

	/**
	 * Método de conveniência para retornar a referência para o objeto da requisição.
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Método de conveniência para retornar o objeto do usuário autenticado.
	 * @return {@link Principal}
	 */
	protected Principal getUserPrincipal() {
		return getRequest().getUserPrincipal();
	}

	/**
	 * Método de conveniência para retornar o nome do usuário uautenticado.
	 * @return <code>String</code>
	 */
	protected String getUserPrincipalName() {
		return getUserPrincipal().getName();
	}

}
