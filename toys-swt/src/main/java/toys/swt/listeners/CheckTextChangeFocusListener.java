/*
 * Criado em 29/12/2009 08:44:57
 */

package toys.swt.listeners;

import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;

import java.lang.reflect.Method;

/**
 * <p>Este listener provê uma forma de disparar um evento quando o texto de um campo é modificado.
 * Ele armazena o texto original quando o controle ganha o foco e verifica se o texto é o
 * mesmo quando o controle perde o foco.</p>
 * @author Iran
 */
public abstract class CheckTextChangeFocusListener extends FocusAdapter {
	private String oldText;

	@Override
	public void focusGained(FocusEvent event) {
		oldText = getText(event.getSource());
	}

	@Override
	public void focusLost(FocusEvent event) {
		String newText = getText(event.getSource());
		if (!newText.equals(oldText))
			textChanged(event.getSource(), oldText, newText);
	}

	/**
	 * Este método é invocado quando uma mudança no texto é detectada.
	 * @param source Controle que originou o evento.
	 * @param oldText Texto anterior à modificação.
	 * @param newText Novo texto.
	 */
	public abstract void textChanged(Object source, String oldText, String newText);

	/**
	 * Verifica o tipo de controle  retorna o texto.
	 * @param widget Referência para o controle.
	 * @return <code>String</code>
	 */
	private String getText(Object widget) {
		try {
			Method m = widget.getClass().getMethod("getText");
			Object o = m.invoke(widget);
			if (o != null && o instanceof String)
				return o.toString();
		} catch (Exception e) {
		}
		return "";
	}

}
