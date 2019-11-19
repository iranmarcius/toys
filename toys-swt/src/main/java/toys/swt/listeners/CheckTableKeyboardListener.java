/*
 * Criado em 15/02/2012 11:23:02
 */

package toys.swt.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Table;
import toys.swt.SWTUtils;

/**
 * Este listener deve ser utilizado com Tables para associar as teclas Ctrl+T e Ctrl+N
 * para selecionar todos e nenhum elemento respectivamente.
 * @author Iran
 */
public class CheckTableKeyboardListener extends KeyAdapter {

	@Override
	public void keyPressed(KeyEvent event) {
		if (event.widget instanceof Table) {
			Table t = (Table)event.widget;
			if ((t.getStyle() & SWT.CHECK )> 0) {
				if (event.stateMask == SWT.CTRL) {

					// Ctrl+T - seleciona tudo
					if (event.keyCode == 116)
						SWTUtils.setItemsChecked(t, true);

					// Ctrl+N - deseleciona tudo
					else if (event.keyCode == 110)
						SWTUtils.setItemsChecked(t, false);

				}
			}
		}
	}

}
