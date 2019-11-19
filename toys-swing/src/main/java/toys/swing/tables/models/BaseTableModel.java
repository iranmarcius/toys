package toys.swing.tables.models;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * Esta classe contém implementações básicas úteis para implementação de TableModels.
 * @author Iran Marcius
 */
public abstract class BaseTableModel<E> extends AbstractTableModel {
	private static final long serialVersionUID = 3258706537132357320L;
	
	protected List<E> data;
	protected String[] columnNames;
	protected boolean modified;
	
	/**
	 * Construtor default.
	 */
	public BaseTableModel() {
		super();
	}
	
	/**
	 * Construtor como inicialização das propriedades.
	 * @param data Lista de dados da tabela
	 * @param columnNames Nomes das colunas
	 */
	public BaseTableModel(List<E> data, String... columnNames) {
		this();
		setColumnNames(columnNames);
		setData(data);
	}
	
	/**
	 * Retorna o número de colunas baseado no array de nomes de colunas.
	 * @return <code>int</code>
	 */
	public int getColumnCount() {
		return columnNames != null ? columnNames.length : 0;
	}

	/**
	 * Retorna o número de linhas baseado no tamanho da lista de dados.
	 * @return <code>int</code>
	 */
	public int getRowCount() {
		return data != null ? data.size() : 0;
	}

	/**
	 * Retorna o nome da coluna correspondente ao índice.
	 * @param column Índice da coluna
	 * @return <code>String</code>
	 */
	@Override
	public String getColumnName(int column) {
		return columnNames != null ? columnNames[column] : "<semnome>";
	}

	/**
	 * Retorna a referência para o array de nomes de colunas.
	 * @return <code>String[]</code>
	 */
	public String[] getColumnNames() {
		return columnNames;
	}

	/**
	 * Retorna a referência para a lista de dados.
	 * @return <code>List&lt;?&gt;</code>
	 */
	public List<E> getData() {
		return data;
	}

	/**
	 * Seta a referência para o array de nomes de colunas
	 * @param columnNames Array com os nomes das colunas
	 */
	public void setColumnNames(String... columnNames) {
		this.columnNames = columnNames;
	}

	/**
	 * Seta a referência para a lista de dados.
	 * @param data Lista de dados
	 */
	public void setData(List<E> data) {
		this.data = data;
	}
	
	/**
	 * Seta a flag de modificação.
	 * @param modified Flag de modificação
	 */
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	/**
	 * Retorna o valor da flag de modificação
	 * @return <code>boolean</code>
	 */
	public boolean isModified() {
		return modified;
	}
	
}
