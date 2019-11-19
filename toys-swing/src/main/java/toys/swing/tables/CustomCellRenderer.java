package toys.swing.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * Esta classe exibe uma célula de tabela renderizada com um JLabel, permitindo especificar
 * cor de texto, fundo e tooltips para a célula.
 * @author Iran Marcius
 */
public class CustomCellRenderer extends DefaultTableCellRenderer {
	private static final long serialVersionUID = 3978989877264332342L;
	
	/**
	 * Esta flag define se o valor o próprio texto deverá ser utilizado como
	 * tooltip da label.
	 */
	protected boolean useTextAsTooltip;
	
	/**
	 * Fonte a ser utilizada no texto.
	 */
	protected Font textFont;
	
	/**
	 * Configura a visualização do componente e retorna-o.
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
		boolean hasFocus, int row, int col) {
		
		// chama o procedimento padr�o
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
			row, col);
		
		// seta a fonte se esta foi definida
		if (textFont != null) {
			setFont(textFont);
		}
		
		// seta o texto da dica se estiver definido
		String s = getTooltipText(table, value, isSelected, hasFocus, row, col);
		if (s != null) {
			setToolTipText(s);
		}
		
		return this;
	}
	
	/**
	 * Retorna o tooltip para o valor
	 */
	protected String getTooltipText(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int col) {
		
		if (useTextAsTooltip) {
			String s = getText();
			if (s.length() > 0) return s;
		}
		
		return null;
	}
	
	/*
	 * Acessors
	 */

	public boolean isUseTextAsTooltip() {
		return useTextAsTooltip;
	}

	public void setUseTextAsTooltip(boolean b) {
		useTextAsTooltip = b;
	}

	public void setTextFont(Font textFont) {
		this.textFont = textFont;
	}
	
}
