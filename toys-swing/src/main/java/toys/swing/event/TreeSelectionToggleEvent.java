package toys.swing.event;

import toys.swing.ModelBean;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.EventObject;


/**
 * Evento para alternação de seleção da chaixa de seleção.
 *
 * @author Iran
 * @since 07/2007
 */
public class TreeSelectionToggleEvent extends EventObject {
    private static final long serialVersionUID = -8054423352910564296L;

    public int modifiers;
    public DefaultMutableTreeNode node;
    public ModelBean modelBean;

    /**
     * Construtor default.
     *
     * @param source Componente que originou o evento.
     */
    public TreeSelectionToggleEvent(Object source, int modifiers, DefaultMutableTreeNode node, ModelBean modelBean) {
        super(source);
        this.modifiers = modifiers;
        this.node = node;
        this.modelBean = modelBean;
    }

}
