/*
 * Criado em 03/07/2007 14:19:30
 */

package toys.swing.extensions;

import toys.swing.ModelBean;
import toys.swing.event.TreeSelectionToggleEvent;
import toys.swing.event.TreeSelectionToogleListener;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * Implementação do <code>JTree</code> com checkboxes em cada nó da árvore.
 *
 * @author Iran
 */
public class JCheckBoxTree extends JTree {
    private static final long serialVersionUID = -8975938590601801142L;

    private boolean propagateCheck;

    public JCheckBoxTree() {
        super();
        init();
    }

    public JCheckBoxTree(Hashtable<?, ?> value) {
        super(value);
    }

    public JCheckBoxTree(Object[] value) {
        super(value);
        init();
    }

    public JCheckBoxTree(TreeNode root, boolean asksAllowsChildren) {
        super(root, asksAllowsChildren);
    }

    public JCheckBoxTree(TreeNode root) {
        super(root);
    }

    public JCheckBoxTree(Vector<?> value) {
        super(value);
    }

    /**
     * Instancia o componente setando o model a ser utilizado.
     */
    public JCheckBoxTree(TreeModel newModel) {
        super(newModel);
        init();
    }

    /**
     * Inicializa o componente.
     */
    protected void init() {
        setCellRenderer(new CheckBoxTreeCellRenderer());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    treeClicked(event);
                }
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent event) {
                treeKeyTyped(event);
            }
        });

    }

    /**
     * Adiciona um evento de seleção do checkbox.
     */
    public void addCheckBoxToggledListeter(TreeSelectionToogleListener listener) {
        listenerList.add(TreeSelectionToogleListener.class, listener);
    }

    /**
     * Verifica se o clique do mouse foi sobre um nó da árvore e se foi sobre a caixa de seleção
     * do checkbox para alternar a seleção.
     *
     * @param event Evento de mouse
     */
    private void treeClicked(MouseEvent event) {
        TreePath path = getPathForLocation(event.getX(), event.getY());
        Rectangle r = getPathBounds(path);
        if (r != null) {
            r.setBounds(r.x + 2, r.y + 2, 30, 30);
            if (r.contains(event.getPoint())) {
                toggleNodeCheck(path, event);
            }
        }
    }

    /**
     * Intercepta o pressionamento de teclas na árvore.
     *
     * @param event Evento de teclado
     */
    private void treeKeyTyped(KeyEvent event) {
        if (event.getKeyChar() == KeyEvent.VK_SPACE) {
            toggleNodeCheck(getSelectionPath(), event);
        }
    }

    /**
     * Alterna o estado de checagem de um nó.
     *
     * @param path  Item cuja seleção será alternada
     * @param event Evento de entrada utilizado para seleção do item da árvore
     */
    protected void toggleNodeCheck(TreePath path, InputEvent event) {

        // se nenhum nó estiver selecionado, não realiza nenhum processamento
        if (path == null)
            return;

        // obtém a referência para o nó selecionado
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();

        // caso o user object do nó não seja uma instância de um TreeNodeBean, não
        // realiza nenhum processamento
        if (!(node.getUserObject() instanceof ModelBean))
            return;

        // obtém a referência para o TreeNodeBean e alterna o estado de checagem
        ModelBean modelBean = (ModelBean) node.getUserObject();
        modelBean.checked = !modelBean.checked;

        // verifica se a seleção deve ser propagada
        if (propagateCheck) {
            propagateChildren(node, modelBean.checked);
            propagateParent(node);
        }

        // redesenha a árvore
        repaint();

        // processa os listeners de alternação de seleção
        TreeSelectionToogleListener[] listeners = listenerList.getListeners(TreeSelectionToogleListener.class);
        for (TreeSelectionToogleListener l : listeners) {
            TreeSelectionToggleEvent e = new TreeSelectionToggleEvent(this, event.getModifiersEx(), node, modelBean);
            l.checkBoxToggled(e);
        }
    }

    /**
     * Propaga a seleção do nó informado para os nós filhos.
     *
     * @param reference Nó referência
     * @param checked   Flag indicando se o check está ligado ou desligado
     */
    private void propagateChildren(DefaultMutableTreeNode reference, boolean checked) {
        Enumeration<TreeNode> children = reference.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
            Object o = node.getUserObject();
            if (o instanceof ModelBean) {
                ModelBean bean = (ModelBean) o;
                bean.checked = checked;
            }
            propagateChildren(node, checked);
        }
    }

    /**
     * Propaga a seleção do nó informado para os nós pai. O nó pai será selecionado
     * caso haja um ou mais filhos selecionados.
     *
     * @param reference Nó referência
     */
    private void propagateParent(DefaultMutableTreeNode reference) {
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) reference.getParent();
        if (parent != null) {
            Object o = parent.getUserObject();
            if (o instanceof ModelBean) {
                int t = 0;
                Enumeration<TreeNode> children = parent.children();
                while (children.hasMoreElements()) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) children.nextElement();
                    o = node.getUserObject();
                    if (o instanceof ModelBean) {
                        ModelBean bean = (ModelBean) o;
                        if (bean.checked) t++;
                    }
                }
                ((ModelBean) parent.getUserObject()).checked = t > 0;
            }
            propagateParent(parent);
        }
    }

    /*
     * Acessors
     */

    public boolean isPropagateCheck() {
        return propagateCheck;
    }

    public void setPropagateCheck(boolean propagateCheck) {
        this.propagateCheck = propagateCheck;
    }

    /*
     * ==================
     * Classes auxiliares
     * ==================
     */

    /**
     * Renderizador dos nós da árvore
     */
    private static class CheckBoxTreeCellRenderer extends DefaultTreeCellRenderer {
        private static final long serialVersionUID = -3529235325746117203L;

        @Override
        public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel,
            boolean expanded, boolean leaf, int row, boolean hasFocus) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            if (treeNode.getUserObject() instanceof ModelBean) {
                ModelBean node = (ModelBean) treeNode.getUserObject();
                JCheckBox checkBox = new JCheckBox(node.name);
                checkBox.setSelected(node.checked);
                if (!sel) {
                    checkBox.setForeground(textNonSelectionColor);
                    checkBox.setBackground(backgroundNonSelectionColor);
                } else {
                    checkBox.setForeground(textSelectionColor);
                    checkBox.setBackground(backgroundSelectionColor);
                }
                return checkBox;
            } else {
                return super.getTreeCellRendererComponent(tree, value, selected, expanded,
                    leaf, row, hasFocus);
            }
        }
    }

}
