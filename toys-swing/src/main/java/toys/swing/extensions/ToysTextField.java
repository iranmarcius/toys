package toys.swing.extensions;

import javax.swing.*;
import javax.swing.text.Document;

/**
 * Esta classe é uma estensão do {@link JTextField} que acrescenta propriedades como
 * limitação de texto e conversão do texto para caixa alta. 
 * @author Iran Marcius
 */
public class ToysTextField extends JTextField {
	private static final long serialVersionUID = 3546362850978050614L;

	protected int limit;
	protected boolean uppercase;
	
	/**
	 * Construtor default.
	 */
	public ToysTextField() {
		super();
	}
	
	/**
	 * Construtor com inicialização de propriedades.
	 * @param text Texto inicial
	 */
	public ToysTextField(String text) {
		super(text);
	}
	
	/**
	 * Construtor com inicialização de propriedades.
	 * @param limit Número máximo de caracteres 
	 */
	public ToysTextField(int limit) {
		this();
		setLimit(limit);
	}
	
	/**
	 * Construtor com inicialização de propriedades.
	 * @param limit Número míximo de caracteres no campo
	 * @param uppercase Flag indicando se as letras devem ser em caixa alta
	 */
	public ToysTextField(int limit, boolean uppercase) {
		this();
		setLimit(limit);
		setUppercase(uppercase);
	}
	
	/**
	 * Construtor com inicialização de propriedades. 
	 * @param text Texto do campo
	 * @param limit Número máximo de caracteres do campo
	 * @param uppercase Flag indicando se o texto deve ser forçado para caixa alta
	 */
	public ToysTextField(String text, int limit, boolean uppercase) {
		this(text);
		setLimit(limit);
		setUppercase(uppercase);
	}
	
	/**
	 * Retorna uma instância do {@link ToysPlainDocument} para ser utilizado no componente.
	 */
	@Override
	protected Document createDefaultModel() {
		return new ToysPlainDocument();
	}

	/**
	 * Seta o limite de caracteres atualizando o valor no model.
	 * @param limit Número máximo de caracteres
	 */
	public void setLimit(int limit) {
		this.limit = limit;
		if (getDocument() instanceof ToysPlainDocument) {
			((ToysPlainDocument)getDocument()).setLimit(limit);
		}
	}

	/**
	 * Retorna o limite máximo de caracteres.
	 * @return <code>int</code>
	 */
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

	/**
	 * Retorna o valor da flag {@link #uppercase}.
	 * @return <code>boolean</code>
	 */
	public boolean isUppercase() {
		return uppercase;
	}

}
