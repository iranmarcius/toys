/*
 * Criado em 30/08/2013 12:01:01 
 */

package toys.swt;

import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import toys.BeanToys;

import java.util.List;

/**
 * Esta classe serve de base para LabelProviders que utilizem {@link ColumnDescriptor}.
 * @author Iran
 */
public class ColumnCellLabelProvider<T> extends CellLabelProvider {
	protected List<ColumnDescriptor> descriptors;
	
	public ColumnCellLabelProvider(List<ColumnDescriptor> descriptors) {
		super();
		this.descriptors = descriptors;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(ViewerCell cell) {
		int index = cell.getColumnIndex();
		String fieldId = descriptors.get(index).getId();
		T element = (T)cell.getElement();
		cell.setText(getText(element, fieldId, index));
		cell.setFont(getFont(element, fieldId, index));
		cell.setForeground(getForeground(element, fieldId, index));
		cell.setBackground(getBackground(element, fieldId, index));
		cell.setImage(getImage(element, fieldId, index));
	}
	
	/**
	 * Retorna o texto que será utilizado na célula.
	 * @param element Elemento.
	 * @param fieldId Identificador da coluna.
	 * @param index Índice da coluna.
	 * @return <code>String</code>
	 */
	protected String getText(T element, String fieldId, int index) {
		try {
			if (element != null) {
				Object o = BeanToys.getValue(element, fieldId);
				return o != null ? o.toString() : "";
			} else {
				return "";
			}
		} catch (Exception e) {
			return "***";
		}
	}
	
	/**
	 * Retorna a fonte que será utilizada na célula.
	 * @param element Elemento.
	 * @param fieldId Identificador da coluna.
	 * @param index Índice da coluna.
	 * @return {@link Font}
	 */
	protected Font getFont(T element, String fieldId, int index) {
		return null;
	}

	/**
	 * Retorna a cor de frente que será utilizada na célula.
	 * @param element Elemento.
	 * @param fieldId Identificador da coluna.
	 * @param index Índice da coluna.
	 * @return {@link Color}
	 */
	protected Color getForeground(T element, String fieldId, int index) {
		return null;
	}

	/**
	 * Retorna a cor de fundo que será utilizada na célula.
	 * @param element Elemento.
	 * @param fieldId Identificador da coluna.
	 * @param index Índice da coluna.
	 * @return {@link Color}
	 */
	protected Color getBackground(T element, String fieldId, int index) {
		return null;
	}
	
	/**
	 * Retorna a imagem que será utilizada na célula.
	 * @param element Elemento.
	 * @param fieldId Identificador da coluna.
	 * @param index Índice da coluna.
	 * @return {@link Image}
	 */
	protected Image getImage(T element, String fieldId, int index) {
		return null;
	}

}
