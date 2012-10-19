package toys.web.struts.actions;

import org.apache.struts2.ServletActionContext;

/**
 * Esta ação invalida a sessão do usuário atual e retorna o resultado SUCCESS.
 * Ela deve ser utilizada apenas em aplicações para ambiante WEB.
 * @author Iran Marcius
 */
public class ExitAction extends StandardAction {
	private static final long serialVersionUID = 547718760676033972L;

	@Override
	public String execute() {
		ServletActionContext.getRequest().getSession().invalidate();
		return SUCCESS;
	}

}
