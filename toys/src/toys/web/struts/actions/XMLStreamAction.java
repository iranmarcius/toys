/*
 * Criado em 22/01/2008 16:48:11
 */

package toys.web.struts.actions;

import java.io.ByteArrayInputStream;

import com.thoughtworks.xstream.XStream;

/**
 * Esta é uma implementação básica para ações que devem gerar um stream de dados
 * cujo conteúdo seja um documento XML. Esta ação extende a {@link StreamAction}, setando
 * o <code>contentType</code> para <code>text/xml</code> como padrão.
 * @author Iran
 */
public abstract class XMLStreamAction extends StreamAction {
	private static final long serialVersionUID = -2600858326010409538L;

	protected XStream xs;

	/**
	 * Charset a ser utilizado no documento xml. O padrão é <code>UTF-8</code>.
	 */
	protected String charset;

	public XMLStreamAction() {
		super();
		xs = new XStream();
		xs.setMode(XStream.NO_REFERENCES);
		charset = "utf-8";
		contentType = "text/xml";
	}

	/**
	 * Retorna o bean que deverá ser serializado.
	 * @return <code>Object</code>
	 * @throws Exception
	 */
	protected abstract Object createData() throws Exception;

	/**
	 * Configura o serializador.
	 */
	protected abstract void configure();

	@Override
	protected void createStream() throws Exception {
		Object o = createData();
		if (!hasErrors()) {
			String s = null;
			if (o != null) {
				configure();
				s = xs.toXML(o);
			} else {
				s = "<null/>";
			}
			inputStream = new ByteArrayInputStream(s.getBytes(charset));
		}
	}

	/*
	 * Acessors
	 */

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getCharset() {
		return charset;
	}

}
