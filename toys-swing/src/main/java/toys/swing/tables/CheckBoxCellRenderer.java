package toys.swing.tables;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Esta classe é um TableCellRenderer para exibir uma célula de uma tabela como um checkbox.
 * <b>IMPORTANTE:</b> este renderer deve ser utilizado apenas para células cujo valor do
 * conteúdo seja do tipo <b>Boolean</b>, caso contrário ocorrerão erros.
 * @author Iran Marcius
 */
public class CheckBoxCellRenderer extends JCheckBox implements TableCellRenderer {
    private static final long serialVersionUID = 3617297834903351864L;

    /**
     * Construtor default.
     */
    public CheckBoxCellRenderer() {
        super();
        setBackground(Color.WHITE);
    }

    /**
     * Configura a exibição e retorna o componente.
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
        setSelected(((Boolean)value).booleanValue());
        if (isSelected) {
            setBackground(table.getSelectionBackground());
        } else {
            setBackground(table.getBackground());
        }
        return this;
    }

}
