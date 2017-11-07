/*
 * Criado em 31/10/2008
 */
package toys.backend.metadata;


/**
 * Classe com os dados da coluna de uma tabela.
 * @author Iran
 */
public class ColumnMetaData {
	public String catalog;
	public String schema;
	public String tableName;
	public String name;
	public int dataType;
	public String typeName;
	public int columnSize;
	public int decimalDigits;
	public int nullable;
	public int ordinalPosition;

	@Override
	public String toString() {
		return String.format("%s:%d %s", name, columnSize, typeName);
	}

}
