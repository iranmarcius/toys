/*
 * Criado em 19/06/2009 16:54:42
 */

package toys.web.servlet.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * Esta é uma implementação básica de filtro que realiza acrescenta funcionalidades de log e
 * inicializa algumas referências comumente utilizadas em filtros.
 * @author Iran
 */
public abstract class BaseFilter implements Filter {
	protected Logger logger;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext application;
	protected String uri;
	protected boolean uriIgnorada;

	/**
	 * Este método inicializa o logger e a lista de URIs que são ignoradas pelo processamento do filtro.
	 */
	@Override
	public void init(FilterConfig fc) throws ServletException {
		logger = Logger.getLogger(getClass());
		logger.debug("Inicializando");
	}

	/**
	 * Este método verifica por padrão se a URI da requisição está na lista de
	 * URIs ignoradas. Caso não esteja, inicializa as referências para o
	 * request, response, session e application. O método <b>não</b> realiza a
	 * chamada {@link #doFilter(ServletRequest, ServletResponse, FilterChain)},
	 * que deve ser feita pelas classes que herdarem esta implementação.
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException {

		// inicializa as referências
		request = (HttpServletRequest)req;
		response = (HttpServletResponse)resp;
		session = request.getSession();
		application = session.getServletContext();
		uri = request.getRequestURI();

		// verifica se a URI deve ser ignorada
		uriIgnorada = ignorarURI();
		if (uriIgnorada) {
			logger.debug(String.format("Ignorando URI %s", uri));
			return;
		}

		// loga o processamento da URI enviada na requisição
		logger.debug(String.format("Processando URI %s", uri));
	}

	/**
	 * Este método retorna se uma URI deve ser desconsiderada do processamento do filtro.
	 * Por padrão sempre retornará <code>false</code>. Implementações que herdarem esta
	 * deverão reescrever este método caso haja necessidade.
	 */
	protected boolean ignorarURI() {
		return false;
	}

}
