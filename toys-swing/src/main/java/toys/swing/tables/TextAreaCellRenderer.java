package toys.swing.tables;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * Esta classe é um renderer de células que necessitem exibir seu conteúdo
 * em múltiplas linhas.
 *
 * @author Iran Marcius
 */
public class TextAreaCellRenderer extends JTextArea implements TableCellRenderer {
    private static final long serialVersionUID = 6002035949303896698L;

    private DefaultTableCellRenderer df;

    /**
     * Cria uma instância da classe com os parâmetros informados.
     *
     * @param lineWrap      Flag indicando se haverá quebra de linha.
     * @param wrapStyleWord Flag indicando se as quebras devem ocorrer somente por palavras inteiras.
     * @param font          Fonte.
     */
    public TextAreaCellRenderer(boolean lineWrap, boolean wrapStyleWord, Font font) {
        super();
        setLineWrap(lineWrap);
        setWrapStyleWord(wrapStyleWord);
        if (font != null) setFont(font);
        df = new DefaultTableCellRenderer();
    }

    /**
     * Cria uma instância da classe com os parâmetros informados.
     *
     * @param lineWrap      Flag indicando se haverá quebra de linha.
     * @param wrapStyleWord Flag indicando se as quebras devem ocorrer somente por palavras inteiras.
     */
    public TextAreaCellRenderer(boolean lineWrap, boolean wrapStyleWord) {
        this(lineWrap, wrapStyleWord, null);
    }

    /**
     * Construtor default, setando a exibição com quebras de linhas.
     */
    public TextAreaCellRenderer() {
        this(true, true, null);
    }

    /**
     * Retorna o componente que será utilizado para renderização.
     *
     * @param table      Referência para a tabela.
     * @param value      Valor.
     * @param isSelected Flag indicando se a célula está selecionada.
     * @param hasFocus   Flah indicando se a célula detém o foco de entrada.
     * @param row        Número da linha.
     * @param column     Número da coluna.
     * @return Componente para renderização
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        df.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setForeground(df.getForeground());
        setBackground(df.getBackground());
        setBorder(df.getBorder());
        setFont(df.getFont());
        setText(df.getText());
        return this;
    }

}
