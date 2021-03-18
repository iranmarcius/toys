package toys;

/**
 * Classe utilitária para operações com arrays.
 * @author Iran
 */
public class ArrayToys {

    private ArrayToys() {
    }

    /**
     * Retorna uma array convertido em string utilizando os separadores informados e envolvendo cada item com o quote informado.
     * @param a Array com os itens.
     * @param sep Separator de itens.
     * @param quote Separador de expressão, ou seja será utilizado antes e após a cada item do array.
     * @return <code>String</code>
     */
    public static String toString(Object[] a, String sep, String quote) {
        if (a == null || a.length == 0)
            return "";
        StringBuilder sb = new StringBuilder();
        for (Object o: a) {
            if (quote != null)
                sb.append(quote);
            sb.append(o.toString());
            if (quote != null)
                sb.append(quote);
            if (sep != null)
                sb.append(sep);
        }
        if (sep != null && sb.length() > sep.length())
            sb.setLength(sb.length() - sep.length());
        return sb.toString();
    }

    /**
     * Retorna um array convertido em string utilizando o separador informado.
     * @param a Array de itens com os itens.
     * @param separator Separador.
     * @return <code>String</code>
     */
    public static String toString(Object[] a, String separator) {
        return toString(a, separator, null);
    }

    /**
     * Retorna um array convertido em string com seus itens separados por vírgula.
     * @param a Array com is itens.
     * @return <code>String</code>
     */
    public static String toString(Object[] a) {
        return toString(a, ", ", null);
    }

    /**
     * Cria um array de inteiros a partir de um array de strings.
     *
     * @param strings Array de strings onde cada strings deve representar um número.
     * @return <code>int[]</code>
     */
    public static int[] toIntArray(String[] strings) {
        if (strings == null)
            return null;
        var ints = new int[strings.length];
        for (int i = 0; i < strings.length; i++)
            ints[i] = Integer.parseInt(strings[i]);
        return ints;
    }

}
