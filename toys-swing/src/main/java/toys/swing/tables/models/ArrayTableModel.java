/*
 * Criado em 04/08/2004
 *
 * Hist�rico de revis�es:
 */

package toys.swing.tables.models;

import java.util.List;

/**
 * Esta classe é uma extensão da <code>{@link BaseTableModel BaseTableModel}</code> que
 * implementa o método <code>{@link #getValueAt(int, int) getValueAt}</code> assumindo que
 * cada elemento da lista de dados é um array de objetos.
 * @author Iran Marcius
 */
public class ArrayTableModel extends BaseTableModel<Object[]> {
	private static final long serialVersionUID = 4049638975465336880L;

	/**
	 * Retorna uma isntância da classe setando a lista de dados e os nomes das colunas.
	 * @param data Lista de dados
	 * @param columnNames Nomes das colunas
	 */
	public ArrayTableModel(List<Object[]> data, String... columnNames) {
		super(data, columnNames);
	}
	
	/**
	 * Retorna o valor especificado pela linha e coluna considerando que o item especificado
	 * da lista seja um array de objetos.
	 * @param rowIndex Índice da linha
	 * @param columnIndex Índice da coluna
	 * @return <code>Object</code>
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object[] o = data.get(rowIndex);
		return o[columnIndex];
	}

}
