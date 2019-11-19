package toys.swing.utils;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Esta classe oferece alguns métodos utilitários para operações com componentes.
 *
 * @author Iran Marcius
 */
public class ComponentUtils {

    /**
     * Centraliza um componente baseado na área total do monitor.
     */
    public static void centerOnScreen(Component c) {
        center(c, Toolkit.getDefaultToolkit().getScreenSize());
    }

    /**
     * Centraliza um componente com relação a outro.
     *
     * @param c         Componente a ser centralizado.
     * @param reference Componente de referência.
     */
    public static void centerOnComponent(Component c, Component reference) {
        Dimension d = reference.getSize();
        center(c, d);
        c.setLocation(c.getX() + reference.getX(), c.getY() + reference.getY());
    }

    /**
     * Centraliza um componente baseado nas dimensões informadas.
     */
    public static void center(Component c, Dimension d) {
        Dimension s = c.getSize();
        c.setLocation(
            (int) ((d.getWidth() - s.getWidth()) / 2),
            (int) ((d.getHeight() - s.getHeight()) / 2)
        );
    }

    /**
     * Percorre o painel fornecido configurando todos os mnemônicos de labels,
     * buttons, radiobuttons e checkboxes de acordo com o texto informado em
     * cada um.
     *
     * @param container Container a ser verificado
     */
    public static void configureMnemonics(Container container) {
        Component[] components = container instanceof JMenu ?
            ((JMenu) container).getMenuComponents() : container.getComponents();
        StringBuilder text = new StringBuilder();
        for (Component component : components) {

            text.setLength(0);
            JLabel label = null;
            AbstractButton button = null;
            if (component instanceof JLabel) {
                label = (JLabel) component;
                text.append(label.getText());
            } else if (component instanceof AbstractButton) {
                button = (AbstractButton) component;
                text.append(button.getText());
            } else if (component instanceof Container) {
                configureMnemonics((Container) component);
            }

            if (text.length() > 0) {
                int mnemonicIndex = text.indexOf("&");
                if (mnemonicIndex > -1) {
                    text.delete(mnemonicIndex, mnemonicIndex + 1);
                    char mnemonic = text.charAt(mnemonicIndex);
                    if (label != null) {
                        label.setText(text.toString());
                        label.setDisplayedMnemonic(mnemonic);
                        label.setDisplayedMnemonicIndex(mnemonicIndex);
                    } else if (button != null) {
                        button.setText(text.toString());
                        button.setMnemonic(mnemonic);
                        button.setDisplayedMnemonicIndex(mnemonicIndex);
                    }
                }
            }

            if (component instanceof JMenu)
                configureMnemonics((Container) component);
        }
    }

    /**
     * Configura a fonte de um componente e todos os seus filhos. Serão modificados apenas os componentes
     * de labels, campos de texto, checkboxes, radiobuttons, listas e combos. No momento da execução deste
     * método, os componentes já deverão ter sido previamente inseridos no container, ou nenhuma modificação
     * será feita.
     *
     * @param component Componente cuja fonte será atualizada.
     * @param font      Fonte a ser configurada.
     */
    public static void setFont(Component component, Font font) {
        if (component instanceof JLabel ||
            component instanceof JTextComponent ||
            component instanceof JButton ||
            component instanceof JToggleButton ||
            component instanceof JList ||
            component instanceof JComboBox ||
            component instanceof JTabbedPane
        )
            component.setFont(font);

        if (component instanceof Container) {
            Component[] components = ((Container) component).getComponents();
            if ((components != null) && (components.length > 0)) {
                for (Component c : components) {
                    setFont(c, font);
                }
            }
        }
    }

    /**
     * Encontra um componente de um tipo específico dentro de um container.
     *
     * @param container Componente inicial
     * @param clazz     Tipo pesquisado
     * @return Retorna o componente da classe requisitada ou nulo caso não seja encontrado.
     */
    public static Component findComponentByType(Container container, Class<?> clazz) {
        Component[] components = container.getComponents();
        for (Component c : components) {

            // Se o componente encontrado for da classe especificada, retorna a sua referência.
            if (c.getClass().isAssignableFrom(clazz))
                return c;

            // Caso o componente não seja da classe especificada mas seja um container,
            // realiza busca no container.
            if (c instanceof Container)
                return findComponentByType((Container) c, clazz);
        }
        return null;
    }

}
