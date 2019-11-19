package toys.swing.models;

import java.text.DateFormatSymbols;
import java.util.Locale;

/**
 * Implementação de um <code>ComboBoxModel</code> que contém uma lista com os nomes
 * dos dias da semana.
 * @author Iran Marcius
 */
public class WeekdaysComboBoxModel extends BaseComboBoxModel {
    private static final long serialVersionUID = 7600777369497909736L;

    /**
     * Construtor.
     * @param shortNames Flag indicando se devem ser utilizados os nomes curtos dos dias da semana.
     * @param locale Definição da localidade
     */
    public WeekdaysComboBoxModel(boolean shortNames, Locale locale) {
        super();
        DateFormatSymbols dfs = new DateFormatSymbols(locale);
        String[] s = !shortNames ? dfs.getWeekdays() : dfs.getShortWeekdays();
        items = new Object[7];
        System.arraycopy(s, 1, items, 0, 7);
    }

    /**
     * Cria uma instância desta classe utilizando o locale default.
     * @param shortNames Flag indicando se devem ser utilizados os nomes curtos dos dias da semana.
     */
    public WeekdaysComboBoxModel(boolean shortNames) {
        this(shortNames, Locale.getDefault());
    }

    /**
     * Cria uma instância desta classe utilizando nomes longos dos dias da semana.
     * @param locale Localidade
     */
    public WeekdaysComboBoxModel(Locale locale) {
        this(false, locale);
    }

    /**
     * Cria uma instância desta classe utilizando nomes longos de dia da semana para a localidade atual.
     */
    public WeekdaysComboBoxModel() {
        this(false, Locale.getDefault());
    }

}
