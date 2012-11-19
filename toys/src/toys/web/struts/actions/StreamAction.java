/*
 * Departamento de Desenvolvimento - ISIC Brasil
 * Todos os direitos reservados
 * Criado em 17/03/2005
 */

package toys.web.struts.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import toys.utils.DateToys;
import toys.utils.SystemToys;

/**
 * Implementação básica para ações que trabalham com stream de dados (download de
 * arquivos, geração de imagens, etc). Esta classe pode cachear opcionalmente os
 * dados do stream e renová-los apenas quando o tempo de expiração estiver decorrido.
 * @author Iran Marcius
 * @author Ednei Parmigiani Júnior
 */
public abstract class StreamAction extends StandardAction {
	private static final long serialVersionUID = -6672314823640871273L;

	/**
	 * Tipo MIME do dado que será gerado no stream.
	 */
	protected String contentType;

	/**
	 * Quantidade de bytes que serão entregues pelo stream.
	 */
	protected int contentLength;

	/**
	 * Forma como o conteúdo será entregue ao navegador.
	 */
	protected String contentDisposition;

	/**
	 * Nome do arquivo para download. Caso a propriedade {@link #filename} a propriedade {@link #contentDisposition}
	 * será automativamente setada para <code><b>attachment; filename=&lt;nome do arquivo&gt;</b></code> caso
	 * esteja vazia.
	 */
	protected String filename;

	/**
	 * Stream de dados.
	 */
	protected InputStream inputStream;

	/**
	 * Tempo de expiração do cache do stream em milissegundos. Caso seja um valor
	 * maior que zero, o stream será gerado apenas quando o cache expirar.
	 */
	protected String expires;

	/**
	 * Nome do arquivo de cache. Caso não seja especificado será utilizado o
	 * nome default que será composto pelo nome da classe mais a string
	 * <b>.tmp</b>.
	 */
	protected String cacheFilename;

	/**
	 * Esta propriedade indica qual resultado deve ser retornado quando ocorrer um erro.
	 * O resultado padrão é <code>error</code>.
	 */
	protected String resultOnError = ERROR;

	/**
	 * Execução da ação.
	 */
	@Override
	public String execute() {
		try {
			buildStream();
			if (inputStream != null)
				contentLength = inputStream.available();
			if (StringUtils.isNotBlank(filename) && StringUtils.isBlank(contentDisposition))
				contentDisposition = "attachment; filename=${filename}";
		} catch (Exception e) {
			logger.error("Erro obtendo o stream de dados", e);
		}
		return !hasErrors() ? SUCCESS : resultOnError;
	}

	/**
	 * Constrói o stream de dados verificando se ele deve ser criado ou obtido do
	 * cache caso este esteja sendo utilizado.
	 */
	private void buildStream() throws Exception {

		// se um tempo de expiração tiver sido determinado, verifica se o stream
		// vai ser obtido do cache ou criado
		if (StringUtils.isNotBlank(expires)) {

			// caso o nome do cache não tenha sido definido, assume um nome padrão
			if (StringUtils.isBlank(cacheFilename)) {
				cacheFilename = getClass().getName().concat(".tmp");
			}

			// obtém o nome do contexto da aplicação evitar conflito de nomes com arquivos
			// gerados por outras aplicações
			String contextName = ServletActionContext.getRequest().getContextPath().substring(1);

			// define a referência para o arquivo de cache
			File cacheFile = new File(new StringBuffer()
				.append(SystemToys.getTempDir())
				.append(SystemToys.getPathSeparator())
				.append(contextName)
				.append("_")
				.append(cacheFilename)
				.toString());

			// se o arquivo não existir ou sua idade estiver acima do limite definido,
			// gera os dados novamente.
			long maxAge = DateToys.timeStr2ms(expires);
			long fileAge = 0;
			if (cacheFile.exists()) {
				fileAge = System.currentTimeMillis() - cacheFile.lastModified();
			}

			// se o cache de dados não existir ou estiver expirado,
			// invoca o método para criação do stream e cria o cache
			if (!cacheFile.exists() || (fileAge > maxAge)) {
				logger.debug("Gerando arquivo de cache " + cacheFile);
				createStream();
				byte[] b = new byte[inputStream.available()];
				inputStream.read(b);
				inputStream.reset();
				FileOutputStream out = new FileOutputStream(cacheFile);
				try {
					out.write(b);
				} finally {
					out.close();
				}

			// caso o cache de dados existir e ainda não estiver expirado,
			// utilizado para criar o stream de dados
			} else {
				inputStream = new FileInputStream(cacheFile);
			}

		// caso nenhum tempo de expiração tenha sido definido, invoca o método
		// para criação do stream
		} else {
			createStream();
		}

	}

	/**
	 * Cria o stream de dados que será utilizado.
	 */
	protected abstract void createStream() throws Exception;

	/*
	 * Acessors
	 */

	public int getContentLength() {
		return contentLength;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFilename() {
		return filename;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String  getExpires() {
		return expires;
	}

	public void setExpires(String expiration) {
		this.expires = expiration;
	}

	public String getCacheFilename() {
		return cacheFilename;
	}

	public void setCacheFilename(String cacheFilename) {
		this.cacheFilename = cacheFilename;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public void setResultOnError(String resultOnError) {
		this.resultOnError = resultOnError;
	}

}
