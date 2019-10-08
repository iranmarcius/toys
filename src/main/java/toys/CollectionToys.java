/*
 * Criado em 22/07/2004
 */
package toys;

import toys.pojos.Identity;

import java.util.*;

/**
 * Conjunto o de métodos utilitários para operações em coleções.
 *
 * @author Iran Marcius
 */
public class CollectionToys {

    private CollectionToys() {
        super();
    }

    /**
     * Fragmenta uma lista original em várias sublistas obedecendo o tamanho máximo especificado.
     *
     * @param l       Lista original.
     * @param tamanho Tamanho máximo das sublistas.
     * @return <code>List&lt;List&lt;?&gt;&gt;</code>
     */
    public static <T> List<List<T>> subLists(List<T> l, int tamanho) {
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
     * Retorna uma lista convertida em string utilizando os separadores informados e
     * envolvendo cada item com o quote informado.
     *
     * @param c     Lista de itens.
     * @param sep   Separator de itens.
     * @param quote Sinal para ser utilizado como quote.
     * @return <code>String</code>
     */
    public static String asString(Collection<?> c, String sep, String quote) {
        if (c == null || c.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder();
        for (Object o : c) {
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
     *
     * @param l         Lista de itens.
     * @param separator Separador.
     * @return <code>String</code>
     */
    public static String asString(Collection<?> l, String separator) {
        return asString(l, separator, null);
    }

    /**
     * Retorna uma lista convertida em string com seus itens separados por vírgula.
     *
     * @param l Lista com os valores.
     * @return <code>String</code>
     */
    public static String asString(Collection<?> l) {
        return asString(l, ", ", null);
    }

    /**
     * Converte uma lista com elementos duplicados para uma lista com elementos distintos.
     *
     * @param l Lista com elementos dupicados.
     * @return Lista com elementos distintos.
     */
    public static List distinctList(List<?> l) {
        List<Object> result = new ArrayList<>(l.size());
        Set<Identity> distinct = new HashSet<>();
        for (Object entity : l) {
            if (distinct.add(new Identity(entity)))
                result.add(entity);
        }
        return result;
    }

    /**
     * Insere um item em uma lista somente se ele não existir. Os itens serão inseridos ordenadamente de acordo com
     * o valor retornado por {@link Collections#binarySearch(List, Object)}.
     *
     * @param l Lista onde o item será inserido.
     * @param o Objeto que será inserido na lista.
     * @return O valor retornado segue a definição do método {@link Collections#binarySearch(List, Object)}, o que significa que
     * um valor negativo indica que o elemento não voi encontrado na lista e portando foi adicionado.
     */
    @SuppressWarnings("unchecked")
    public static int uniqueAdd(List l, Comparable o) {
        int i = Collections.binarySearch(l, o);
        if (i < 0)
            l.add((i * -1) - 1, o);
        return i;
    }


    /**
     * Método de conveniência que utiliza o {@link Collections#binarySearch(List, Object, Comparator)} para localizar
     * e retornar o elemento correspondente à chave informada dentro de uma lista. A lista deve estar previamente ordenada
     * utilizando o mesmo comparator informado.
     *
     * @param l    Lista a ser pesquisada. Deve estar classificada pelo mesmo comparator informado no parâmetro.
     * @param key  Chave do objeto pesquisado.
     * @param comp comparator a ser utilizado na pesquisa.
     * @return Retorna o objeto correspondente à chave ou null caso ele não seja encontrado.
     */
    public static <T> T find(List<T> l, T key, Comparator<T> comp) {
        int i = Collections.binarySearch(l, key, comp);
        return i >= 0 ? l.get(i) : null;
    }

    /**
     * Método de conveniência que utiliza o {@link Collections#binarySearch(List, Object)} para localizar e retornar o elemento
     * correspondente à chave informada dentro de uma lista. A lista deve estar previamente ordenada.
     *
     * @param l   Lista a ser pesquisada. Deve estar classificada pelo mesmo comparator informado no parâmetro.
     * @param key Chave do objeto pesquisado.
     * @return Retorna o objeto correspondente à chave ou null caso ele não seja encontrado.
     */
    @SuppressWarnings("unchecked")
    public static <T> T find(List<T> l, T key) {
        int i = Collections.binarySearch((List<Comparable<T>>) l, key);
        return i >= 0 ? l.get(i) : null;
    }

}
