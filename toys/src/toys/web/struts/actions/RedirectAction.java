package toys.web.struts.actions;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Esta ação promove o redirecionamento para a página indicada pelo atributo referer
 * armazenado na sessão ou o parâmetro de nome referer. Caso o nome do parâmetro seja
 * outro, este será indicado no campo <code>refererField</code>.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 */
public class RedirectAction extends ActionSupport {
	private static final long serialVersionUID = 3256728368329078578L;

	@Override
	public String execute() throws Exception {

		// determina o nome do atributo ou parâmetro de onde será obtido
		// o referer
		HttpServletRequest request = ServletActionContext.getRequest();
		String redirectParam = request.getParameter("redirectParam");
		if (StringUtils.isBlank(redirectParam)) {
			redirectParam = "redirect";
		}

		// verifica se existe o parâmetro de referer na requisição e caso não exista
		// utiliza o da sessão.
		String redirect = request.getParameter(redirectParam);
		if (StringUtils.isBlank(redirect)) {
			redirect = (String)request.getSession().getAttribute(redirectParam);
		}

		// faz o redirecionamento
		ServletActionContext.getResponse().sendRedirect(URLEncoder.encode(redirect, "utf-8"));

		return NONE;
	}

}
