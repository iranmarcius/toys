/*
 * Criado em 26/07/2007 16:57:41 
 */

package toys.swing.event;

import java.util.EventListener;

/**
 * Listener para alternalção de seleção do valor do CheckBox.
 * @author Iran
 */
public interface TreeSelectionToogleListener extends EventListener {

	void checkBoxToggled(TreeSelectionToggleEvent event);
	
}
