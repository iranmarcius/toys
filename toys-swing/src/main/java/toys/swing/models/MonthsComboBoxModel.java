/*
 * Departamento de Desenvolvimento - ISIC Brasil 
 * Todos os direitos reservados
 * Criado em 01/11/2005
 */

package toys.swing.models;

import java.text.DateFormatSymbols;
import java.util.Locale;

/**
 * Implementa��o de um <code>ComboBoxModel</code> que cont�m uma lista com os nomes
 * dos meses do ano.
 * @author Iran Marcius
 */
public class MonthsComboBoxModel extends BaseComboBoxModel {
	private static final long serialVersionUID = -4211438098939577093L;
	
	/**
	 * Construtor.
	 * @param shortNames Flag indicando se devem ser utilizados os nomes curtos dos
	 * meses.
	 * @param locale Definição da localidade
	 */
	public MonthsComboBoxModel(boolean shortNames, Locale locale) {
		super();
		DateFormatSymbols dfs = new DateFormatSymbols(locale);
		String[] s = !shortNames ? dfs.getMonths() : dfs.getShortMonths();
		items = new Object[12];
		System.arraycopy(s, 0, items, 0, 12);
	}
	
	/**
	 * Cria uma instância desta classe utilizando o locale default.
	 * @param shortNames Flag indicando se devem ser utilizados os nomes curtos dos
	 * meses.
	 */
	public MonthsComboBoxModel(boolean shortNames) {
		this(shortNames, Locale.getDefault());
	}
	
	/**
	 * Cria uma instância desta classe utilizando nomes de meses longos.
	 * @param locale Localidade
	 */
	public MonthsComboBoxModel(Locale locale) {
		this(false, locale);
	}
	
	/**
	 * Cria uma instância desta classe utilizando nomes de meses longos e a
	 * localidade default.
	 */
	public MonthsComboBoxModel() {
		this(false, Locale.getDefault());
	}
	
}
