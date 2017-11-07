/*
 * Criado em 21/03/2011 12:17:26
 */

package toys.backend.metadata;

import java.util.List;


/**
 * Classe com os metadados de uma tabela.
 * @author Iran
 */
public class TableMetaData {
	public String catalog;
	public String schema;
	public String name;
	public String type;
	public List<ColumnMetaData> columns;
}
