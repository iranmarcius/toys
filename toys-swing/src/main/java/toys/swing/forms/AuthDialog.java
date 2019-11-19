/*
 * Criado em 16/04/2007 17:32:54
 */

package toys.swing.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import toys.Crypt;
import toys.swing.listeners.AuthListener;
import toys.swing.utils.ComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;


/**
 * Janela genérica para pedido de login de usuário.
 *
 * @author Iran
 */
public class AuthDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 2660808985720690826L;

    public enum Estado {AUTENTICADO, CANCELADO}

    private JTextField username;
    private JPasswordField password;
    private JButton loginButton;
    private JButton cancelarButton;
    private AuthListener authListener;
    private Estado estado;

    /**
     * Instancia a janela modal de autenticação especificando um frame como pai.
     *
     * @param owner        proprietário da janela
     * @param authListener Referência para o listener de autenticação
     */
    public AuthDialog(Window owner, AuthListener authListener) {
        super(owner);
        inicializar(authListener);
    }

    /**
     * Cria uma instância da janela de autenticação.
     *
     * @param authListener Referência para o listener de autenticação
     */
    public AuthDialog(AuthListener authListener) {
        super();
        inicializar(authListener);
    }

    /**
     * Criação dos componentes da dialog.
     *
     * @param authListener Referência para o autenticador.
     */
    protected void inicializar(AuthListener authListener) {
        this.authListener = authListener;
        setTitle("Autenticação");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        setResizable(false);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(getFormularioPanel(), BorderLayout.CENTER);
        getContentPane().add(getBotoesPanel(), BorderLayout.SOUTH);
        pack();
        ComponentUtils.configureMnemonics(this);
        ComponentUtils.centerOnScreen(this);
    }

    protected JPanel getFormularioPanel() {
        JPanel panel = new JPanel();
        FormLayout layout = new FormLayout("3dlu, right:p, 3dlu, p, 3dlu", "21dlu, 21dlu");
        panel.setLayout(layout);

        username = new JTextField(12);
        JLabel usernameLabel = new JLabel("&Nome de usuário:");
        usernameLabel.setLabelFor(username);
        panel.add(usernameLabel, new CellConstraints("2, 1, 1, 1, default, default"));
        panel.add(username, new CellConstraints("4, 1, 1, 1, default, default"));

        password = new JPasswordField(12);
        JLabel passwordLabel = new JLabel("&Senha:");
        passwordLabel.setLabelFor(password);
        panel.add(passwordLabel, new CellConstraints("2, 2, 1, 1, default, default"));
        panel.add(password, new CellConstraints("4, 2, 1, 1, default, default"));

        return panel;
    }

    protected JPanel getBotoesPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        loginButton = new JButton("&Entrar");
        loginButton.addActionListener(this);
        panel.add(loginButton);

        cancelarButton = new JButton("&Cancelar");
        cancelarButton.addActionListener(this);
        panel.add(cancelarButton);

        return panel;
    }

    /**
     * Retorna o nome de usuário informado.
     *
     * @return <code>String</code>
     */
    public String getUsername() {
        return username.getText();
    }

    /**
     * Retorna a senha informada.
     *
     * @return <code>String</code>
     */
    public String getPassword() {
        return new String(password.getPassword());
    }

    /**
     * Retorna a senha informada criptografada com o algorítmo informado.
     *
     * @param algoritmo Algorítmo que deve ser utilizado para criptografar a senha (MD5, SHA, etc).
     * @return <code>String</code>
     */
    public String getSenhaCriptografada(String algoritmo) throws NoSuchAlgorithmException {
        return Crypt.digest(new String(password.getPassword()), algoritmo);
    }

    /**
     * Retorna o estado da autenticação.
     *
     * @return {@link Estado}
     */
    public Estado getEstado() {
        return estado;
    }

    /**
     * Processa as ações disparadas pelos botões.
     */
    @Override
    public void actionPerformed(ActionEvent event) {

        // o botão de login invoca o listener de autenticação
        if (event.getSource().equals(loginButton)) {
            boolean autenticado = authListener.autenticar(this, getUsername(), getPassword());
            if (autenticado) {
                estado = Estado.AUTENTICADO;
                dispose();
            }
        }

        // o botão de cancelamento simplesmente fecha a janela
        if (event.getSource().equals(cancelarButton)) {
            estado = Estado.CANCELADO;
            dispose();
        }

    }

}
