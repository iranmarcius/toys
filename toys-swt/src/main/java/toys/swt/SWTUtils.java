/*
 * Criado em 30/09/2009 15:33:34
 */

package toys.swt;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.CellLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.*;
import toys.SystemToys;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Métodos utilitários.
 * @author Iran
 */
public class SWTUtils {

    private SWTUtils() {
    }

	/**
	 * Centraliza o shell dentro do display informado.
	 */
	public static void center(Shell shell, Display display) {
		Rectangle r = shell.getBounds();
		Rectangle d = display.getBounds();
		shell.setLocation((d.width - r.width) / 2, (d.height - r.height) / 2);
	}

	/**
	 * Retorna quantos itens de uma tabela com check box estão ligados.
	 * @param table Referência para a tabela.
	 * @return <code>int</code>
	 */
	public static int getCheckCount(Table table) {
		if ((table.getStyle() & SWT.CHECK) == 0)
			return 0;
		TableItem[] itens = table.getItems();
		int c = 0;
		for (TableItem item: itens)
			if (item.getChecked())
				c++;
		return c;
	}

	/**
	 * Retorna o número de itens checados.
	 * @param itens Array de {@link TreeItem}.
	 * @return Retorna a quantidade de itens checados verificando a flag <code>checked</code>.
	 */
	public static int getCheckCount(TreeItem[] itens) {
		int c = 0;
		for (TreeItem item: itens)
			if (item.getChecked() || item.getGrayed())
				c++;
		return c;
	}

	/**
	 * Seta o estado de checagem de todos os itens de uma tabela que possua checkboxes.
	 * @param table Referência para a tabela
	 * @param check Flag checagem
	 */
	public static void setItemsChecked(Table table, boolean check) {
		if ((table.getStyle() & SWT.CHECK) == 0)
			return;
		TableItem[] itens = table.getItems();
		for (TableItem item: itens)
			item.setChecked(check);
	}

	/**
	 * Seta o estado de checagem dos itens de uma árvore.
	 * @param tree Referência para a árvore.
	 * @param check Flag indicando o novo estado de checagem do item.
	 * @param recursive Indica se o processamento deve ser recursivo, ou seja, se os sub-itens de
	 * um item também serão processados.
	 */
	public static void setItemsChecked(Tree tree, boolean check, boolean recursive) {
		if ((tree.getStyle() & SWT.CHECK) == 0)
			return;
		setItemsChecked(tree.getItems(), check, recursive);
	}

	/**
	 * Seta o estado de checagem dos itens passados.
	 * @param itens Referência para a árvore.
	 * @param check Flag indicando o novo estado de checagem do item.
	 * @param recursive Indica se o processamento deve ser recursivo, ou seja, se os sub-itens de
	 * um item também serão processados.
	 */
	public static void setItemsChecked(TreeItem[] itens, boolean check, boolean recursive) {
		for (TreeItem item: itens) {
			item.setChecked(check);
			if (recursive && item.getItemCount() > 0)
				setItemsChecked(item.getItems(), check, true);
		}
	}

	/**
	 * Cria as colunas de um TableViewer a partir da lista de descritores informada.
	 * @param viewer TableViewer que será atualizado.
	 * @param descriptors Lista com objetos do tipo {@link ColumnDescriptor} contendo a descrição de cada coluna.
	 * @param labelProvider Objeto que será utilizado para fornecer o conteúdo das células.
	 */
	public static void configureColumns(TableViewer viewer, List<ColumnDescriptor> descriptors, CellLabelProvider labelProvider) {
		for (ColumnDescriptor cd: descriptors) {
			TableViewerColumn c = new TableViewerColumn(viewer, cd.getStyle());
			c.getColumn().setData("fieldId", cd.getId());
			c.getColumn().setData("sorteable", cd.isSorteable());
			c.getColumn().setText(cd.getHeader());
			c.getColumn().setWidth(cd.getWidth());
			c.setLabelProvider(labelProvider);
		}
	}
	
	/**
	 * Exibe uma dialog de erro utilizando a classe {@link ErrorDialog} do JFace para exibir todos os detalhes
	 * da exceção gerada.
	 * @param parent Shell pai.
	 * @param msg Mensagem.
	 * @param t Exceção.
	 */
	public static void errorDialog(Shell parent, String msg, Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		String[] linhas = sw.toString().split(SystemToys.getLineSeparator());
		IStatus[] sts = new Status[linhas.length];
		for (int i = 0; i < linhas.length; i++)
			sts[i] = new Status(IStatus.ERROR, "pluginid", linhas[i]);
		MultiStatus ms = new MultiStatus("pluginid", IStatus.ERROR, sts, t.getLocalizedMessage(), t);
		ErrorDialog.openError(parent, "Erro", msg, ms);
	}
	
}
