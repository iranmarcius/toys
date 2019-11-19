package toys.swing.listeners;

import toys.swing.ModelBean;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Esta classe implementa a funcionalidade necessária para buscas incrementais
 * em comboboxes.
 *
 * @author Iran Marcius
 */
public class IncrementalSearchComboListener extends IncrementalSearchKeyListener {

    /**
     * Cria uma instância da classe setando a referência para a lista de dados.
     *
     * @param data Lista de dados
     */
    public IncrementalSearchComboListener(List<?> data) {
        super(data);
    }

    /**
     * Faz uma pesquisa sequencial na lista de dados para retornar o item correspondente.
     *
     * @param expr Expressão a ser pesquisada
     */
    @Override
    protected int findIndex(String expr) {
        String s = expr.toUpperCase();
        for (int i = 0; i < data.size(); i++) {
            if (matches(data.get(i), s)) return i;
        }
        return -1;
    }

    /**
     * Retorna se os valores são equivalentes.
     *
     * @param listItem Item da lista de dados
     * @param s        String que deve ser comparada
     * @return <code>boolean</code>
     */
    protected boolean matches(Object listItem, String s) {
        if (listItem instanceof ModelBean) {
            ModelBean bean = (ModelBean) listItem;
            return bean.name.toUpperCase().startsWith(s);
        } else {
            return listItem.toString().toUpperCase().startsWith(s);
        }
    }

    /**
     * Atualiza o item selecionado no combo com o índice fornecido.
     *
     * @param event Evento de teclado
     * @param index Índice
     */
    @Override
    protected void doUpdateComponent(KeyEvent event, int index) {
        Object o = event.getSource();
        if (!(o instanceof JComboBox))
            return;
        JComboBox<?> combo = (JComboBox<?>) o;
        combo.setSelectedIndex(index);
    }

}
