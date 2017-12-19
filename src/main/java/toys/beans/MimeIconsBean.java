/*
 * Departamento de Desenvolvimento - ISIC Brasil
 * Todos os direitos reservados
 * Criado em 17/03/2005
 */

package toys.beans;

import java.util.ResourceBundle;

/**
 * Bean para acesso a imagens correspondentes a tipos MIME.
 * @author Iran Marcius
 */
public class MimeIconsBean {
	private ResourceBundle bundle;

	/**
	 * Construtor default.
	 */
	public MimeIconsBean() {
		super();
		bundle = ResourceBundle.getBundle(getClass().getName());
	}

	/**
	 * Retorna o nome do arquivo de imagem correspondente ao tipo MIME informado.
	 * Caso o tipo não conste, retorna o nome da imagem default.
	 */
	public String getName(String mimeType) {
		try {
			return bundle.getString(mimeType);
		} catch (Exception e) {
			return "others.png";
		}
	}

}
