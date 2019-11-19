/*
 * Criado em 08/02/2010 11:08:21
 */

package toys.swt.listeners;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;

/**
 * Este listener possui uma referência para um composite cujo layout deve ser
 * do tipo {@link StackLayout}. Ele deve ser associado a componentes cujo método
 * {@link Widget#getData()} deve conter a referência para um componentes que
 * será exibido no layout quando o botão for pressionado.
 * @author Iran
 */
public class StackLayoutComponentSelectionListener extends SelectionAdapter {
	private Composite stack;
	private StackLayout layout;

	public StackLayoutComponentSelectionListener(Composite stack) {
		super();
		this.stack = stack;
		if (stack.getLayout() instanceof StackLayout)
			layout = (StackLayout)stack.getLayout();
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		if (!(event.getSource() instanceof Widget) && layout != null)
			return;

		Widget w = (Widget)event.getSource();
		if (!(w.getData("topControl") instanceof Control))
				return;

		Control c = (Control)w.getData("topControl");
		layout.topControl = c;
		stack.layout();
	}

	/**
	 * Adiciona este listener ao controle informado.
	 */
	public void associate(ToolItem item, Control c) {
		item.setData("topControl", c);
		item.addSelectionListener(this);
	}

	/**
	 * Adiciona este listener ao controle informado.
	 */
	public void associate(MenuItem item, Control c) {
		item.setData("topControl", c);
		item.addSelectionListener(this);
	}

}
