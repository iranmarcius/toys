package toys.swing.extensions;

import javax.swing.*;
import javax.swing.text.Document;

/**
 * Esta classe é uma estensão do {@link JTextArea} que acrescenta propriedades como
 * limitação de texto e flag de caixa alta. 
 * @author Iran Marcius
 */
public class ToysTextArea extends JTextArea {
	private static final long serialVersionUID = 3257566200417628720L;
	
	protected int limit;
	protected boolean uppercase;
	
	public ToysTextArea() {
		super();
	}
	
	/**
	 * Construtor com inicialização de propriedades.
	 * @param limit Limite máximo de caracteres do campo
	 */
	public ToysTextArea(int limit) {
		this();
		setLimit(limit);
	}

	/**
	 * Retorna uma instância de {@link ToysPlainDocument} como modelo default para o componente.
	 */
	@Override
	protected Document createDefaultModel() {
		return new ToysPlainDocument();
	}

	/**
	 * Seta o limite de caracteres atualizando o valor no modelo.
	 * @param limit Número máximo de caracteres
	 */
	public void setLimit(int limit) {
		this.limit = limit;
		if (getDocument() instanceof ToysPlainDocument) {
			((ToysPlainDocument)getDocument()).setLimit(limit);
		}
	}

	public int getLimit() {
		return limit;
	}

	/**
	 * Seta a flag de caixa alta e atualiza o modelo.
	 * @param uppercase Flag indicando se o texto deve estar em caixa alta.
	 */
	public void setUppercase(boolean uppercase) {
		this.uppercase = uppercase;
		if (getDocument() instanceof ToysPlainDocument) {
			((ToysPlainDocument)getDocument()).setUppercase(uppercase);
		}
	}

	public boolean isUppercase() {
		return uppercase;
	}

}
