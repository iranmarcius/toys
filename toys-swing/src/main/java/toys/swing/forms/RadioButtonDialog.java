/*
 * Criado em 29/06/2007 09:57:18
 */

package toys.swing.forms;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import toys.swing.threads.ComponentUpdate;
import toys.swing.threads.ComponentUpdater;
import toys.swing.utils.ComponentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

/**
 * Esta dialog é apresentada com um conjunto de checkboxes, um botão de &quot;ok&quot; e um botão
 * de &quot;cancelar&quot; para seleção do usuário.
 *
 * @author Iran
 */
public class RadioButtonDialog extends JDialog implements ActionListener, ComponentUpdate {
    private static final long serialVersionUID = -7302287253810653699L;

    private ButtonGroup buttonGroup;
    private JButton okButton;
    private JButton cancelButton;
    private JPanel radioPanel;
    private boolean cancelled;
    private int selectedIndex;
    private ComponentUpdater updater;

    public RadioButtonDialog(Frame owner, String title) {
        super(owner, title);
        this.cancelled = false;
        this.selectedIndex = -1;
        initialize();
    }

    /**
     * Atualiza o estado do botão ok.
     */
    public void updateComponents() {
        okButton.setEnabled(buttonGroup.getSelection() != null);
    }

    /**
     * Seta os textos dos checkboxes que aparecerão.
     *
     * @param options Textos com os radio buttons que serão criados
     */
    public void setOptions(String... options) {
        buttonGroup = new ButtonGroup();
        for (String option : options) {
            JRadioButton rb = new JRadioButton(option);
            buttonGroup.add(rb);
            radioPanel.add(rb);
        }
        ComponentUtils.configureMnemonics(radioPanel);
        pack();
    }

    /**
     * Retorna o índice do botão selecionado, considerando que a primeira opção é
     * o índice 0 (zero). Caso nenhum botão tenha sido selecionado, retorna o valor -1.
     *
     * @return <code>int</code>
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Intercepta o pressionamento dos botões
     *
     * @param event Evento
     */
    public void actionPerformed(ActionEvent event) {
        Object o = event.getSource();
        if (o instanceof JButton) {
            JButton b = (JButton) o;
            if (b.getName().equals("ok")) {
                ButtonModel bm = buttonGroup.getSelection();
                Enumeration<AbstractButton> e = buttonGroup.getElements();
                int i = 0;
                while (e.hasMoreElements()) {
                    if (e.nextElement().getModel().equals(bm)) break;
                    i++;
                }
                if (i < buttonGroup.getButtonCount()) {
                    selectedIndex = i;
                } else {
                    selectedIndex = -1;
                }
                cancelled = false;
            } else if (b.getName().equals("cancel")) {
                cancelled = true;
            }
            dispose();
        }
    }

    /**
     * Retorna o estado da flag de cancelamento.
     *
     * @return <code>boolean</code>
     */
    public boolean isCancelled() {
        return cancelled;
    }

    protected void initialize() {
        this.setModal(true);
        this.setResizable(false);
        this.setContentPane(getContentPanel());

        updater = new ComponentUpdater(this);
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowOpened(WindowEvent e) {
                new Thread(updater).start();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                updater.setExecutando(false);
            }

        });

        ComponentUtils.configureMnemonics(this);
    }

    protected JPanel getContentPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(getCenterPanel(), BorderLayout.CENTER);
        panel.add(getBottomPanel(), BorderLayout.SOUTH);
        return panel;
    }

    protected JPanel getCenterPanel() {
        JPanel panel = new JPanel(new FormLayout("3dlu, p, 3dlu", "3dlu, p, 3dlu"));

        radioPanel = new JPanel();
        BoxLayout layout = new BoxLayout(radioPanel, BoxLayout.Y_AXIS);
        radioPanel.setLayout(layout);

        panel.add(radioPanel, new CellConstraints("2, 2"));

        return panel;
    }

    protected JPanel getBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        okButton = new JButton("O&k");
        okButton.setName("ok");
        okButton.addActionListener(this);
        panel.add(okButton);

        cancelButton = new JButton("&Cancelar");
        cancelButton.setName("cancel");
        cancelButton.addActionListener(this);
        panel.add(cancelButton);

        return panel;
    }

}
