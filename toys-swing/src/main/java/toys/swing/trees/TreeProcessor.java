/*
 * Criado em 13/06/2007 17:11:59
 */

package toys.swing.trees;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import java.util.Enumeration;

/**
 * Esta classe percorre todos os nós de uma árvore
 *
 * @author Iran
 */
public class TreeProcessor {

    private TreeNodeProcessor processor;

    /**
     * Cria uma instência do processador de árvores.
     *
     * @param processor Referência para o processador de nós
     */
    public TreeProcessor(TreeNodeProcessor processor) {
        super();
        this.processor = processor;
    }

    /**
     * Processa a árvore informada.
     *
     * @param tree Referência para a árvore.
     */
    public void process(JTree tree) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        Enumeration<TreeNode> enumeration = root.children();
        processNodes(enumeration);
    }

    /**
     * Processa os nós passados
     *
     * @param enumeration Nós a serem processados
     */
    private void processNodes(Enumeration<TreeNode> enumeration) {
        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) enumeration.nextElement();
            boolean processChildren = processor.process(node);
            if (processChildren) {
                Enumeration<TreeNode> children = node.children();
                processNodes(children);
            }
        }
    }

}
