/*
 * Criado em 22/07/2004
 */
package toys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Conjunto o de métodos utilitários para operações em coleções.
 * @author Iran Marcius
 */
public class CollectionToys {

    private CollectionToys() {
        super();
    }

    /**
     * Fragmenta uma lista original em várias sublistas obedecendo o tamanho máximo especificado.
     * @param l Liwsta original.
     * @param tamanho Tamanho máximo das sublistas.
     * @return <code>List&lt;List&lt;?&gt;&gt;</code>
     */
    public static <T> List<List<T>> fragmentar(List<T> l, int tamanho) {
        int i = 0;
        int f;
        List<List<T>> listas = new ArrayList<>();
        while (i < l.size()) {
            f = i + tamanho;
            if (f > l.size())
                f = l.size();
            listas.add(l.subList(i, f));
            i += tamanho;
        }
        return listas;
    }

    /**
     * Cria um mapa de chave/valor a partir da lista informada. A conversão será executada
     * apenas em objetos do tipo {@link KeyValue}, {@link StringPair} e <code>Object[]</code>,
     * neste último considerando apenas os dois primeiros elementos do array.
     * @param l Lista a ser convertida
     * @return <code>Map</code>
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map asMap(List l) {
        Map m = new HashMap();
        for (Object o: l) {
            if (o instanceof KeyValue) {
                KeyValue p = (KeyValue)o;
                m.put(p.getKey(), p.getValue());
            } else if (o instanceof StringPair) {
                StringPair p = (StringPair)o;
                m.put(p.getKey(), p.getValue());
            } else if (o instanceof Object[]) {
                Object[] a = (Object[])o;
                if (a.length >= 2)
                    m.put(a[0], a[1]);
            }
        }
        return m;
    }

    /**
     * Retorna uma lista convertida em string utilizando os separadores informados e
     * envolvendo cada item com o quote informado.
     * @param l Lista de itens.
     * @param sep Separator de itens.
     * @param quote Sinal para ser utilizado como quote.
     * @return <code>String</code>
     */
    public static String asString(List<?> l, String sep, String quote) {
        if (l == null || l.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (Object o: l) {
            if (quote != null)
                sb.append(quote);
            sb.append(o);
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
     * Retorna uma lista convertida em string utilizando o separador informado.
     * @param l Lista de itens.
     * @param separator Separador.
     * @return <code>String</code>
     */
    public static String asString(List<?> l, String separator) {
        return asString(l, separator, null);
    }

    /**
     * Retorna uma lista convertida em string com seus itens separados por vírgula.
     * @param l Lista com os valores.
     * @return <code>String</code>
     */
    public static String asString(List<?> l) {
        return asString(l, ", ", null);
    }

}
