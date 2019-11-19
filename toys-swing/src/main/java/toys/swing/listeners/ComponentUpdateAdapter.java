/*
 * Criado em 21/05/2009 17:06:09
 */

package toys.swing.listeners;

import toys.swing.threads.ComponentUpdater;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Esta classe contém a implementação padrão para inicialização e finalização de um
 * {@link ComponentUpdater}.
 *
 * @author Iran
 */
public class ComponentUpdateAdapter extends WindowAdapter {
    private ComponentUpdater updater;

    public ComponentUpdateAdapter(ComponentUpdater updater) {
        super();
        this.updater = updater;
    }

    @Override
    public void windowOpened(WindowEvent event) {
        new Thread(updater).start();
    }

    @Override
    public void windowClosed(WindowEvent e) {
        updater.setExecutando(false);
    }

}
