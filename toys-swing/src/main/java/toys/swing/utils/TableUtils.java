package toys.swing.utils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;

/**
 * Métodos utilitários para operações com tabelas.
 * @author Iran Marcius
 */
public class TableUtils {

	/**
	 * Configura as larguras de colunas para uma tabela.
	 * @param table Tabela cujas larguras das colunas serão ajustadas
	 * @param cw Larguras das colunas
	 */
	public static void setColumnWidths(JTable table, int... cw) {
		TableColumnModel cm = table.getColumnModel();
		int cc = cm.getColumnCount();
		for (int i = 0; (i < cc) && (i < cw.length); i++) {
			cm.getColumn(i).setPreferredWidth(cw[i]);
		}
	}

	/**
	 * Método de conveniência para disparar o evento <code>fireTableDataChanged</code> do model da
	 * tabela informada.
	 * @param table Referência para a tabela
	 */
	public static void fireTableDataChanged(JTable table) {
		((AbstractTableModel)table.getModel()).fireTableDataChanged();
	}

}
