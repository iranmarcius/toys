/*
 * Criado em 04/12/2009 10:05:36
 */

package toys.swt.listeners;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import toys.swt.SWTUtils;

/**
 * Este listener é especializado no controle seleção de itens com checkbox em árvores.
 * @author Iran
 */
public class CheckTreeListener extends SelectionAdapter {
	private boolean recursivo;

	/**
	 * Cria uma instância deste listener ligando a propriedade de recursividade.
	 */
	public CheckTreeListener() {
		super();
		recursivo = true;
	}

	/**
	 * Construtor com inicialização das propriedades.
	 * @param recursivo Flag indicando se os itens filhos devem ser selecionados recursivamente.
	 */
	public CheckTreeListener(boolean recursivo) {
		super();
		this.recursivo = recursivo;
	}

	@Override
	public void widgetSelected(SelectionEvent event) {

		// verifica se o controle que originou o evento é uma Tree
		if (!(event.getSource() instanceof Tree))
			return;

		// verifica se a Tree possui checkboxes nos itens
		Tree tree = (Tree)event.getSource();
		if ((tree.getStyle() & SWT.CHECK) == 0)
			return;

		// verifica se o detalhe do evento é um check
		if (event.detail != SWT.CHECK)
			return;

		// se o item em questão possuir subitens e a flag de recursividade estiver ativada,
		// processa os subitens
		TreeItem item = (TreeItem)event.item;
		if (item.getItemCount() > 0 && recursivo) {
			SWTUtils.setItemsChecked(item.getItems(), item.getChecked(), recursivo);
			item.setGrayed(false);
		}

		// seta o estado de seleção dos itens pai
		if (item.getParentItem() != null)
			selecionarPai(item.getParentItem());

	}

	/**
	 * Sera o estado de seleção do item pai com base na quantidade de itens filhos selecionados.
	 * @param item Referência para o item.
	 */
	private void selecionarPai(TreeItem item) {
		int checkCount = SWTUtils.getCheckCount(item.getItems());
		int itemCount = item.getItemCount();
		item.setChecked(itemCount > 0 && checkCount > 0);
		item.setGrayed(item.getChecked() && checkCount < itemCount);
		if (item.getParentItem() != null)
			selecionarPai(item.getParentItem());
	}

}
