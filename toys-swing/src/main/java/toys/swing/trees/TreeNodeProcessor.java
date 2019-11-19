/*
 * Criado em 13/06/2007 17:09:02 
 */

package toys.swing.trees;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Interface com a definição dos métodos do processador de nós de árvores.
 * @author Iran
 */
public interface TreeNodeProcessor {
	
	/**
	 * Processa o nó da árvore.
	 * @param node Referência para o nó da árvore
	 * @return se o valor retornado for <code>TRUE</code>, serão processados os filhos
	 * do nó atual, caso contrário eles serão ignorados.
	 */
	boolean process(DefaultMutableTreeNode node);
	
}
