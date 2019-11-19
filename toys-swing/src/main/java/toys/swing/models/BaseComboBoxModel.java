/*
 * Departamento de Desenvolvimento - ISIC Brasil 
 * Todos os direitos reservados
 * Criado em 01/11/2005
 */

package toys.swing.models;

import javax.swing.*;
import java.util.List;

/**
 * Implementação básica para criação de ComboBoxModels com listas imutáveis.
 * @author Iran Marcius
 */
@SuppressWarnings("rawtypes")
public abstract class BaseComboBoxModel extends AbstractListModel implements ComboBoxModel {
	private static final long serialVersionUID = 6857378103790917221L;
	
	protected Object selected;
	protected Object[] items;
	
	/**
	 * Construtor default.
	 */
	protected BaseComboBoxModel() {
		super();
	}

	/**
	 * Construtor.
	 * @param items Lista de valores que serão apresentados no combo.
	 */
	protected BaseComboBoxModel(Object[] items) {
		super();
		this.items = items;
	}
	
	/**
	 * Construtor.
	 * @param items Lista de itens com os valores que serão apresentados no combo.
	 */
	protected BaseComboBoxModel(List<?> items) {
		this(items.toArray());
	}
	
	public int getSize() {
		return items != null ? items.length : 0;
	}

	public Object getElementAt(int index) {
		return items != null ? items[index] : null;
	}

	public void setSelectedItem(Object anItem) {
		if (((selected == null) && (anItem != null)) ||
			((selected != null) && !anItem.equals(selected))) {
			selected = anItem;
			fireContentsChanged(this, -1, -1);
		}
		
	}

	public Object getSelectedItem() {
		return selected;
	}
	
}
