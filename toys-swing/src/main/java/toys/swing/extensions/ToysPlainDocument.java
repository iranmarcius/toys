package toys.swing.extensions;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * Este modelo de documento implementa algumas funcionalidades extras para os componentes
 * {@link javax.swing.JTextField} e {@link javax.swing.JTextArea}.
 *
 * @author Iran Marcius
 */
public class ToysPlainDocument extends PlainDocument {
    private static final long serialVersionUID = 3258132466169296176L;
    private int limit;
    private boolean uppercase;

    /**
     * Construtor default.
     */
    public ToysPlainDocument() {
        super();
    }

    /**
     * Cria uma instância com o limite e a flag de caixa informados.
     *
     * @param limit     Limite máximo de caracteres do documento.
     * @param uppercase flag indicando de os caracteres do documento devem ser
     *                  convertidos para caixa alta.
     */
    public ToysPlainDocument(int limit, boolean uppercase) {
        this();
        setLimit(limit);
        setUppercase(uppercase);
    }

    /**
     * Cria uma instância com o limite informado.
     */
    public ToysPlainDocument(int limit) {
        super();
        setLimit(limit);
    }

    /**
     * Intercepta a inserção de uma string verificando as configurações do componente
     * pai antes executar a operação.
     */
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if (str == null) return;
        if (uppercase) str = str.toUpperCase();
        boolean insert = true;
        if (limit > 0) {
            insert = getLength() + str.length() <= limit;
        }
        if (insert) {
            super.insertString(offs, str, a);
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public boolean isUppercase() {
        return uppercase;
    }

    public void setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
    }

}
