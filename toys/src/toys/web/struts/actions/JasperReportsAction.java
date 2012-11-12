/*
 * Departamento de Desenvolvimento - ISIC Brasil
 * Todos os direitos reservados
 * Criado em 17/05/2005 �s 14:16:16
 */

package toys.web.struts.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;

/**
 * Ação com a implementação básica para criação de relatórios utilizando o JasperReports.
 * A configuração desta ação deverá incluir obrigatoriamente o interceptor <code>Preparable</code>.
 * @author Iran
 */
public abstract class JasperReportsAction extends StreamAction {
	private static final long serialVersionUID = 8142952417314317307L;

	protected Map<String, Object> params;
	protected ServletContext sc;

	/**
	 * Inicializa a lista de parâmetros e a referência para o contexto da servlet.
	 */
	@Override
	public String execute() {
		sc = ServletActionContext.getServletContext();
		params = new HashMap<String, Object>();
		params.put("appRoot", sc.getRealPath("/"));
		return super.execute();
	}

}
