/*
 * Criado em 30/08/2013 17:25:03
 */

package toys.swt;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import toys.BeanToys;

import java.lang.reflect.InvocationTargetException;

/**
 * Esta classe gerencia cliques em cabeçalhos de tabelas para aplicar classificação.
 *
 * @author Iran
 */
public class TableHeaderManager extends ViewerComparator implements SelectionListener {
    protected TableViewer viewer;
    private TableColumn lastSortedColumn;

    /**
     * Construtor.
     *
     * @param viewer Referência para o viewer. Todas as colunas da tabela referenciada terão uma instância
     *               desta classe adicionada como SelectionListener para interceptar os cliques de mouse.
     */
    public TableHeaderManager(TableViewer viewer) {
        super();
        this.viewer = viewer;
        for (TableColumn column : viewer.getTable().getColumns()) {
            Object o = column.getData("sorteable");
            if (o instanceof Boolean && (boolean) o)
                column.addSelectionListener(this);
        }
    }

    /**
     * Intercepta o clique de mouse verificando se a coluna é classificável ou não para aplicar a classificação.
     */
    @Override
    public void widgetSelected(SelectionEvent e) {
        TableColumn c = (TableColumn) e.getSource();
        Table t = viewer.getTable();

        int direction = lastSortedColumn == null || !lastSortedColumn.equals(c) ? SWT.NONE : t.getSortDirection();
        switch (direction) {
            case SWT.NONE:
                direction = SWT.DOWN;
                break;
            case SWT.DOWN:
                direction = SWT.UP;
                break;
            case SWT.UP:
                direction = SWT.NONE;
                break;
            default:
                // Nada ocorre
        }

        t.setSortColumn(c);
        t.setSortDirection(direction);

        lastSortedColumn = c;

        viewer.refresh();
    }

    @Override
    public void widgetDefaultSelected(SelectionEvent e) {
        // Nenhuma implementação
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {

        Table t = ((TableViewer) viewer).getTable();
        TableColumn c = t.getSortColumn();
        if (c == null)
            return 0;

        String id = (String) c.getData("fieldId");
        if (id == null)
            return 0;

        int direction = t.getSortDirection();
        if (direction == SWT.NONE)
            return 0;

        try {
            Object o1 = BeanToys.getValue(e1, id);
            Object o2 = BeanToys.getValue(e2, id);
            if (!(o1 instanceof Comparable) || !(o2 instanceof Comparable))
                return 0;

            Comparable k1 = (Comparable) o1;
            Comparable k2 = (Comparable) o2;

            return direction == SWT.DOWN ? k1.compareTo(k2) : k2.compareTo(k1);
        } catch (IllegalAccessException | InvocationTargetException e) {
            return 0;
        }
    }

}
