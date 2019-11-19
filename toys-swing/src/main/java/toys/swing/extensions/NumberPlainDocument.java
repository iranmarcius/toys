package toys.swing.extensions;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.text.DecimalFormatSymbols;

/**
 * Classe auxiliar para controlar a edição do campo.
 */
public class NumberPlainDocument extends PlainDocument {
    private static final long serialVersionUID = 4122537700043076663L;

    private int length;
    private int decimalPlaces;
    private char decimalSeparator;
    private String pattern;

    /**
     * Construtor default.
     */
    public NumberPlainDocument() {
        super();
        decimalSeparator = new DecimalFormatSymbols().getDecimalSeparator();
    }

    /**
     * Construtor default com inicialização das propriedades.
     *
     * @param length        Número máximo de dígitos
     * @param decimalPlaces Número de casas decimais.
     */
    public NumberPlainDocument(int length, int decimalPlaces) {
        this();
        setSizes(length, decimalPlaces);
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a)
        throws BadLocationException {

        // obtém o conteúdo atual do documento e insere a string enviada.
        StringBuilder text = new StringBuilder(getText(0, getLength()));
        text.insert(offs, str);

        // verifica se a string resultante está no formato permitido
        if (!text.toString().matches(pattern)) {
            return;
        }

        // da string resultante, obtém a parte inteira e a parte decimal.
        // caso uma das duas ultrapasse as especificações de tamanho, não
        // permite a inserção
        int dsp = text.indexOf(String.valueOf(decimalSeparator));
        String integerPart;
        String decimalPart;
        if (dsp == -1) {
            integerPart = text.toString();
            decimalPart = "";
        } else {
            integerPart = text.substring(0, dsp);
            decimalPart = text.substring(dsp + 1);
        }
        int maxLeftLength = length - decimalPlaces;
        if ((integerPart.length() > maxLeftLength) ||
            (decimalPart.length() > decimalPlaces)) {
            return;
        }

        // se todas as validações passaram, insere a string
        super.insertString(offs, str, a);
    }

    /**
     * Seta o tamanho do campo.
     *
     * @param length        Tamanho do documento.
     * @param decimalPlaces Casas decimais.
     */
    public void setSizes(int length, int decimalPlaces) {
        this.length = length;
        this.decimalPlaces = decimalPlaces;
        if (decimalPlaces == 0) {
            pattern = "^\\d{0," + length + "}$";
        } else {
            pattern = "^\\d{0," + (length - decimalPlaces) + "}\\" +
                decimalSeparator + "?\\d{0," + decimalPlaces + "}$";
        }
    }

}