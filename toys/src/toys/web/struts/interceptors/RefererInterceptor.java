/*
 * Criado em 18/05/2007 16:22:20
 */

package toys.web.struts.interceptors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * <p>Verifica se o referer da requisição está na lista de referers permitidos não
 * permitindo a execução da ação caso não esteja. Caso o referer não seja permitido,
 * o interceptor irá adicionar um erro e retornar o resultado <code>Action.ERROR</code>.</p>
 * <p>Os seguintes parâmtros são permitidos:</p>
 * <ul>
 * 	<li><b>allowedReferers (obrigatório):</b> uma lista de regular expressions, separadas por vírgula,
 * 	com os referers que serão permitidos</li>
 * 	<li><b>message:</b> mensagem que será adicionada à lista de erros no caso do referer não ser permitido</li>
 * 	</ul>
 * @author Iran
 * @author Ednei Parmigiani Júnior
 */
public class RefererInterceptor extends AbstractInterceptor {
	private static final long serialVersionUID = -7381201686688889408L;

	private transient final Log log = LogFactory.getLog(RefererInterceptor.class);

	private String[] allowedReferers;
	private String message;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String referer = ServletActionContext.getRequest().getHeader("REFERER");
		boolean allowed = false;
		if (StringUtils.isNotBlank(referer)) {
			for (String re: allowedReferers) {
				allowed |= referer.matches(re);
			}
		}
		if (!allowed) {
			ActionSupport action = (ActionSupport)invocation.getAction();
			log.debug(String.format("Referer %s nao permitido para a acao %s", referer,
					invocation.getInvocationContext().getName()));
			action.addActionError(message);
			return Action.ERROR;
		}
		return invocation.invoke();
	}

	/**
	 * Recebe o parâmetro com os referers válidos e cria a lista para validação.
	 * @param allowedReferers Lista de referers válidos separados por quebra de linha
	 */
	public void setAllowedReferers(String allowedReferers) {
		log.debug("Setando referers permitidos");
		this.allowedReferers = allowedReferers.split(" *, *");
		log.debug(this.allowedReferers);
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
