package toys.swing.actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Esta ação faz a implementação básica para fechar uma janela.
 * @author Iran Marcius
 */
public class CloseAction extends BaseAction {
	private static final long serialVersionUID = 3257288024110020660L;

	/**
	 * @see BaseAction#BaseAction(Object)
	 */
	public CloseAction(Object target) {
		super(target);
	}

	/**
	 * @see BaseAction#BaseAction(Object, AbstractButton)
	 */
	public CloseAction(Object target, AbstractButton button) {
		super(target, button);
	}

	/**
	 * Executa a ação de fechar a janela.
	 */
	public void actionPerformed(ActionEvent e) {
		((Window)target).dispose();
	}

}
