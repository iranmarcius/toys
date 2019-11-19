package toys.swing.utils;

import toys.KeyValue;

import javax.swing.*;

/**
 * Métodos utilitários para operações com comboboxes.
 * @author Iran Marcius
 */
public class ComboBoxUtils {

	/**
	 * Retorna o objeto <code>{@link KeyValue KeyValue}</code> do combo.
	 * @param combo Combo cujos itens deverão ser objetos do tipo <code>{@link KeyValue KeyValue}</code>.
	 * @return Retorna o <code>{@link KeyValue KeyValue}</code> correspondente ao item
	 * atualmente selecionado no combo ou <code>null</code> caso não haja nenhum item selecionado.
	 * @see toys.KeyValue
	 */
	public static KeyValue<?, ?> getKeyValueItem(JComboBox<?> combo) {
		if (combo.getSelectedIndex() < 0) return null;
		return (KeyValue<?, ?>)combo.getSelectedItem();
	}
	
}
