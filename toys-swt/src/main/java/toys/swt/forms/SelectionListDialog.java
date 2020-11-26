package toys.swt.forms;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.*;
import toys.BeanToys;
import toys.swt.SWTUtils;

import java.util.ArrayList;

public class SelectionListDialog<E> extends Dialog implements SelectionListener {
	private final Shell dialogShell;
	private final List list;
	private final Button cancelarButton;
	private Button selecionarButton;
	private final Composite composite1;
	private java.util.List<E> selecao;
	private java.util.List<E> items;
	private boolean confirmado;

	/**
	 * Cria uma instância da dialog.
	 * @param parent Referência para o shell pai.
	 * @param style Estilo que será utilizado na lista.
	 */
	public SelectionListDialog(Shell parent, int style) {
		super(parent, SWT.NONE);
		confirmado = false;
		dialogShell = new Shell(parent, SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);

		GridLayout thisLayout = new GridLayout();
		thisLayout.makeColumnsEqualWidth = true;
		dialogShell.setDefaultButton(selecionarButton);
		dialogShell.setLayout(thisLayout);

		{
			GridData listLData = new GridData();
			listLData.grabExcessHorizontalSpace = true;
			listLData.grabExcessVerticalSpace = true;
			listLData.horizontalAlignment = GridData.FILL;
			listLData.verticalAlignment = GridData.FILL;
			list = new List(dialogShell, SWT.BORDER | SWT.V_SCROLL |style);
			list.setLayoutData(listLData);
		}
		{
			composite1 = new Composite(dialogShell, SWT.NONE);
			RowLayout composite1Layout = new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
			GridData composite1LData = new GridData();
			composite1LData.horizontalAlignment = GridData.END;
			composite1LData.verticalAlignment = GridData.END;
			composite1.setLayoutData(composite1LData);
			composite1.setLayout(composite1Layout);
			{
				selecionarButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
				RowData okButtonLData = new RowData();
				selecionarButton.setLayoutData(okButtonLData);
				selecionarButton.setText("Selecionar");
			}
			{
				cancelarButton = new Button(composite1, SWT.PUSH | SWT.CENTER);
				RowData cancelarButtonLData = new RowData();
				cancelarButton.setLayoutData(cancelarButtonLData);
				cancelarButton.setText("Cancelar");
			}
		}
		dialogShell.pack();

		// adiciona os listeners
		selecionarButton.addSelectionListener(this);
		cancelarButton.addSelectionListener(this);
	}

	public boolean open() {
		Display display = dialogShell.getDisplay();
		dialogShell.setSize(400, 270);
		SWTUtils.center(dialogShell, display);
		dialogShell.open();
		while (!dialogShell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();
		return confirmado;
	}

	/**
	 * Seta os itens que serão disponibilizados para seleção.
	 * @param items Lista de itens
	 * @param field No caso dos itens serem beans, especifica o nome da propriedade que será
	 * utilizada para exibição.
	 */
	public void setItems(java.util.List<E> items, String field) {
		this.items = items;
		list.removeAll();
		for (Object o: items) {
			if (field != null) {
				String valor;
				try {
					valor = (String)BeanToys.getValue(o, field);
				} catch (Exception e) {
					valor = "<erro>";
				}
				list.add(valor);
			} else {
				list.add(o.toString());
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent event) {
		widgetSelected(event);
	}

	@Override
	public void widgetSelected(SelectionEvent event) {
		if (event.getSource().equals(selecionarButton))
			confirmarSelecao();
		else if (event.getSource().equals(cancelarButton))
			dialogShell.close();
	}

	/**
	 * Cria uma lista com todos os itens selecionados.
	 */
	private void confirmarSelecao() {
		 selecao = new ArrayList<>();
		 for (int i: list.getSelectionIndices())
			 selecao.add(items.get(i));
		 confirmado = true;
		 dialogShell.close();
	}

	/**
	 * Retorna uma lista com os itens selecionados.
	 */
	public java.util.List<E> getSelecao() {
		return selecao;
	}

	/**
	 * Retorna um único item selecionado.
	 */
	public E getSelecaoUnica() {
		return !selecao.isEmpty() ? selecao.get(0) : null;
	}

	/**
	 * Seta o título da janela.
	 * @param title Título
	 */
	public void setTitle(String title) {
		dialogShell.setText(title);
	}

}
