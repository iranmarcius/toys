/*
 * Criado em 06/05/2009 10:47:58 
 */

package toys.swing.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import toys.swing.listeners.AuthListener;
import toys.swing.utils.ComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta é uma dialog modal para operações de troca de senha.
 * @author Iran
 */
public class PasswordDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = -4693728949671455572L;
	
	private JPasswordField senhaAtualText;
	private JPasswordField novaSenhaText;
	private JPasswordField confirmacaoSenhaText;
	private JButton trocarButton;
	private JButton cancelarButton;
	
	private AuthListener authListener;

	/**
	 * Construtor com inicialização de parâmetros.
	 * @param authListener Referência para o listener de autenticação
	 * @param requererSenhaAtual Flag indicando se a senha atual deve ser requerida.
	 */
	public PasswordDialog(AuthListener authListener, boolean requererSenhaAtual) {
		super();
		inicializar(authListener, requererSenhaAtual);
	}

	/**
	 * Construtor com inicialização de parâmetros.
	 * @param owner Proprierário da janela
	 * @param authListener Referência para o listener de autenticação
	 * @param requererSenhaAtual Flag indicando se a senha atual deve ser requerida.
	 */
	public PasswordDialog(Window owner, AuthListener authListener, boolean requererSenhaAtual) {
		super(owner);
		inicializar(authListener, requererSenhaAtual);
	}

	/**
	 * Inicialização da janela.
	 * @param authListener Referência para o listener de autenticação.
	 * @param requererSenhaAtual Esta flag indica se o campo de senha atual deve ser exibido.
	 */
	private void inicializar(AuthListener authListener, boolean requererSenhaAtual) {
		this.authListener = authListener;
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Troca de senha");
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getFormulario(requererSenhaAtual), BorderLayout.CENTER);
		getContentPane().add(getBotoes(), BorderLayout.SOUTH);
		pack();
		ComponentUtils.configureMnemonics(this);
		ComponentUtils.centerOnScreen(this);
	}
	
	private JPanel getFormulario(boolean requererSenhaAtual) {
		JPanel panel = new JPanel(new FormLayout(
				"right:100dlu, 3dlu, 80dlu, 3dlu",
				requererSenhaAtual ? "21dlu, 21dlu, 21dlu" : "21dlu, 21dlu"));
		
		int row = 1;
		CellConstraints cc = new CellConstraints();
		
		// este campo é condicionado à flag de pedido da senha atual
		if (requererSenhaAtual) {
			senhaAtualText = new JPasswordField();
			JLabel senhaAtualLabel = new JLabel("&Senha atual:");
			senhaAtualLabel.setLabelFor(senhaAtualText);
			panel.add(senhaAtualLabel, cc.xy(1, row));
			panel.add(senhaAtualText, cc.xy(3, row++));
		}
		
		novaSenhaText = new JPasswordField(); 
		JLabel novaSenhaLabel = new JLabel("&Nova senha:");
		novaSenhaLabel.setLabelFor(novaSenhaText);
		panel.add(novaSenhaLabel, cc.xy(1, row));
		panel.add(novaSenhaText, cc.xy(3, row++));
		
		confirmacaoSenhaText = new JPasswordField();
		JLabel confirmacaoSenhaLabel = new JLabel("C&onfirmação da nova senha:");
		confirmacaoSenhaLabel.setLabelFor(confirmacaoSenhaText);
		panel.add(confirmacaoSenhaLabel, cc.xy(1, row));
		panel.add(confirmacaoSenhaText, cc.xy(3, row));
		
		return panel;
	}
	
	private JPanel getBotoes() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		trocarButton = new JButton("&Trocar senha");
		trocarButton.addActionListener(this);
		panel.add(trocarButton);
		
		cancelarButton = new JButton("&Cancelar");
		cancelarButton.addActionListener(this);
		panel.add(cancelarButton);
		
		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource().equals(trocarButton)) {
			String senhaAtual = senhaAtualText != null ? new String(senhaAtualText.getPassword()) : null;
			String novaSenha = new String(novaSenhaText.getPassword());
			String confirmacaoSenha = new String(confirmacaoSenhaText.getPassword());
			if (authListener.trocarSenha(this, senhaAtual, novaSenha, confirmacaoSenha))
				dispose();
		}
		
		if (event.getSource().equals(cancelarButton))
			dispose();
	}

}
