/*
 * Criado em 06/06/2007 16:56:51 
 */

package toys.swing.forms;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Janela simples para exibição de uma mensagem.
 * @author Iran
 */
public class MessageWindow extends JWindow {
	private static final long serialVersionUID = -1787613063734903966L;
	
	private JLabel message;

	public MessageWindow(Window parent, String message) {
		super(parent);
		setSize(350, 200);
		setAlwaysOnTop(true);
		
		JPanel panel = new JPanel(null);
		panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		
		Rectangle r = getBounds();
		r.grow(-20, -20);
		this.message = new JLabel(message, SwingConstants.CENTER);
		this.message.setBounds(r);
		this.message.setFont(this.message.getFont().deriveFont(Font.BOLD));
		panel.add(this.message);

		setContentPane(panel);
	}
	
}
