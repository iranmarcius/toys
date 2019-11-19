package toys.swing.listeners;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta é uma ação genérica para fechar uma janela.
 * @author Iran Marcius
 */
public class CloseActionListener implements ActionListener {
    protected Window target;

    /**
     * Construtor padrão.
     */
    public CloseActionListener() {
        super();
    }

    /**
     * Construtor como inicialização de propriedades.
     */
    public CloseActionListener(Window target) {
        this();
        this.target = target;
    }

    /**
     * Execução da ação
     */
    public void actionPerformed(ActionEvent e) {
        if (target != null) {
            target.dispose();
        }
    }

}
