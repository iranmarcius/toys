/*
 * Criado em 06/09/2004
 */

package toys.swing.forms;

import toys.swing.utils.ComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Janela genérica para exibição de progresso de operações.
 * @author Iran Marcius
 */
public class ProgressWindow extends JDialog  {
	private static final long serialVersionUID = 3546647594391844921L;
	
	protected boolean cancelled;
	protected JPanel centerPanel;
	protected JProgressBar progress;
	protected JButton cancelButton;

	public ProgressWindow(Dialog owner) throws HeadlessException {
		super(owner);
		initialize();
	}
	
	public ProgressWindow(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
		initialize();
	}
	
	public ProgressWindow(Frame owner) throws HeadlessException {
		super(owner);
		initialize();
	}
	
	public ProgressWindow(Frame owner, String title) throws HeadlessException {
		super(owner, title);
		initialize();
	}
	
	/**
	 * Inicializa os componentes visuais.
	 */
	protected void initialize() {
		cancelled = false;
		this.setSize(385, 125);
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setLayout(new BorderLayout());
		
		centerPanel = new JPanel();
		centerPanel.setLayout(null);
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(null);

		progress = new JProgressBar(SwingConstants.HORIZONTAL);
		centerPanel.add(progress);
		progress.setBounds(15, 15, 345, 25);
		progress.setStringPainted(true);
		
		cancelButton = new JButton("Cancelar", new ImageIcon(getClass().getClassLoader()
			.getResource("toys/swing/images/cancel.gif")));
		cancelButton.setMnemonic(KeyEvent.VK_C);
		centerPanel.add(cancelButton);
		cancelButton.setBounds(135, 55, 115, 30);
		
		ComponentUtils.centerOnScreen(this);
		
		// configura os listeners
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (cancelled) dispose();
			}
			
		});
		
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelled = true;
			}
		});
		
	}
	
	/**
	 * Configura e inicializa a barra de progresso. 
	 */
	public void configure(int min, int max) {
		progress.setMinimum(min);
		progress.setMaximum(max);
		progress.setValue(min);
	}
	
	/**
	 * Seta o valor da posição atual da barra.
	 */
	public void setValue(int value) {
		progress.setValue(value);
	}
	
	/**
	 * Incrementa a barra de progresso com o valor informado.
	 * @param ammount Valor de incremento
	 */
	public void increment(int ammount) {
		progress.setValue(progress.getValue() + ammount);
	}
	
	/**
	 * Incrementa a barra de progresso em um.
	 */
	public void increment() {
		increment(1);
	}
	
	/**
	 * Retorna se o botão de cancelamento foi pressionado.
	 */
	public boolean isCancelled() {
		return cancelled;
	}
	
	/**
	 * Seta a flag de cancelamento da janela.
	 */
	public void cancel() {
		cancelled = true;
	}
	
	/**
	 * Retorna se a operação está terminada baseado no tamanho da tarefa e
	 * no progresso atual.
	 */
	public boolean isFinished() {
		return progress.getValue() >= progress.getMaximum();
	}
	
}
