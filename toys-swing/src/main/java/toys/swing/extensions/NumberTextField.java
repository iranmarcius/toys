package toys.swing.extensions;


import javax.swing.*;
import javax.swing.text.Document;
import java.text.DecimalFormatSymbols;

/**
 * Este componente é uma especialização do <code>{@link JTextField}</code> para edição
 * de números.
 * @author Iran Marcius
 */
public class NumberTextField extends JTextField {
	private static final long serialVersionUID = 3257852077853323312L;
	
	private char decimalSeparator;
	
	/**
	 * Número de dígitos do campo incluindo as casas decimais e excluindo o separador decimal.
	 */
	protected int length;
	
	/**
	 * Número de casas decimais
	 */
	protected int decimalPlaces;
	
	/**
	 * Construtor com inicialização de propriedades.
	 * @param length Quantidade de dígitos do campo, incluindo as casas decimais.
	 * @param decimalPlaces Número de casas decimais.
	 */
	public NumberTextField(int length, int decimalPlaces) {
		super();
		setHorizontalAlignment(SwingConstants.RIGHT);
		setLength(length);
		setDecimalPlaces(decimalPlaces);
		decimalSeparator = new DecimalFormatSymbols().getDecimalSeparator();
	}
	
	/**
	 * Instancia um campo no tamanho especificado e com duas casas decimais.
	 * @param length Quantidade de dígitos do campo.
	 */
	public NumberTextField(int length) {
		this(length, 0);
	}
	
	/**
	 * Instancia um campo padrão de cindo dígitos com duas casas decimais.
	 */
	public NumberTextField() {
		this(5, 2);
	}
	
	/**
	 * Retorna uma instência do documento para edição numérica.
	 */
	@Override
	protected Document createDefaultModel() {
		return new NumberPlainDocument();
	}
	
	/*
	 * Retorna uma string formatada para que o valor possa ser convertido em número.
	 */
	private String convertionText() {
		return getText().replaceAll("\\" + decimalSeparator, ".");
	}
	
	/**
	 * Retorna o valor do campo convertido para um inteiro.
	 * @return int
	 */
	public int integerValue() {
		return Integer.valueOf(convertionText().replaceAll("\\.\\d+$", ""));
	}
	
	/**
	 * Retorna o valor do campo convertido para um inteiro.
	 * @param defaultValue Valor default para o caso de ocorrer algum erro
	 * na conversão
	 * @return int  
	 */
	public int defaultInteger(int defaultValue) {
		try {
			return integerValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * Seta o valor do campo.
	 * @param value Valor que será setado
	 */
	public void setInteger(int value) {
		 setText(Integer.toString(value));
	}
	
	/**
	 * Retorna o valor do campo convertido para um double.
	 * @return double
	 */
	public double doubleValue() {
		return Double.parseDouble(convertionText());
	}
	
	/**
	 * Retorna o valor do campo convertido para um double.
	 * @param defaultValue Valor default que será retornado caso ocorra algum erro
	 * na conversão
	 * @return double
	 */
	public double defaultDouble(double defaultValue) {
		try {
			return doubleValue();
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * Seta o valor do campo.
	 * @param value Novo valor para o campo
	 */
	public void setDouble(double value) {
		setText(Double.toString(value).replaceAll("\\.",
			String.valueOf(decimalSeparator)));
	}
	
	public void setLength(int length) {
		if (length < 0) length = 0;
		this.length = length;
		if (getDocument() instanceof NumberPlainDocument) {
			((NumberPlainDocument)getDocument()).setSizes(length, decimalPlaces);
		}
	}

	public int getLength() {
		return length;
	}

	public void setDecimalPlaces(int decimalPlaces) {
		if (decimalPlaces < 0) decimalPlaces = 0;
		this.decimalPlaces = decimalPlaces;
		if (getDocument() instanceof NumberPlainDocument) {
			((NumberPlainDocument)getDocument()).setSizes(length, decimalPlaces);
		}
	}

	public int getDecimalPlaces() {
		return decimalPlaces;
	}

}
